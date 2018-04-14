package com.mihryundel.ecwidandroid.mvp.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.mihryundel.ecwidandroid.EcwidAndroidApplication
import com.mihryundel.ecwidandroid.bus.ProductDeleteAction
import com.mihryundel.ecwidandroid.bus.ProductEditAction
import com.mihryundel.ecwidandroid.mvp.model.Product
import com.mihryundel.ecwidandroid.mvp.model.ProductDao
import com.mihryundel.ecwidandroid.mvp.views.MainView
import com.mihryundel.ecwidandroid.utils.getProductsSortMethodName
import com.mihryundel.ecwidandroid.utils.setProductsSortMethod
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    enum class SortProductsBy : Comparator<Product> {
        DATE {
            override fun compare(product1: Product, product2: Product) = product1.changedAt!!.compareTo(product2.changedAt)
        },
        NAME {
            override fun compare(product1: Product, product2: Product) = product1.title!!.compareTo(product2.title!!)
        },
    }

    @Inject
    lateinit var productDao: ProductDao
    private lateinit var productsList: MutableList<Product>

    init {
        EcwidAndroidApplication.graph.inject(this)
        EventBus.getDefault().register(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadAllProducts()
    }

    fun deleteAllProducts() {
        productDao.deleteAllProducts()
        productsList.removeAll(productsList)
        viewState.onAllProductsDeleted()
    }

    fun deleteProductByPosition(position: Int) {
        val product = productsList[position]
        productDao.deleteProduct(product)
        productsList.remove(product)
        viewState.onProductDeleted()
    }

    fun openNewProduct() {
        val newProduct = productDao.createProduct()
        productsList.add(newProduct)
        sortProductsBy(getCurrentSortMethod())
        viewState.openProductScreen(newProduct.id)
    }

    fun openProduct(position: Int) {
        viewState.openProductScreen(productsList[position].id)
    }

    fun search(query: String) {
        if (query == "") {
            viewState.onSearchResult(productsList)
        } else {
            val searchResults = productsList.filter { it.title!!.startsWith(query, ignoreCase = true) }
            viewState.onSearchResult(searchResults)
        }
    }

    fun sortProductsBy(sortMethod: SortProductsBy) {
        productsList.sortWith(sortMethod)
        setProductsSortMethod(sortMethod.toString())
        viewState.updateView()
    }

    @Subscribe
    fun onProductEdit(action: ProductEditAction) {
        val productPosition = getProductPositionById(action.productId)
        productsList[productPosition] = productDao.getProductById(action.productId)!!
        sortProductsBy(getCurrentSortMethod())
    }

    @Subscribe
    fun onProductDelete(action: ProductDeleteAction) {
        Log.d("EcwidAndroid", "onDeleted" + action.productId);
        val productPosition = getProductPositionById(action.productId)
        productsList.removeAt(productPosition)
        viewState.updateView()
    }

    fun showProductContextDialog(position: Int) {
        viewState.showProductContextDialog(position)
    }

    fun hideProductContextDialog() {
        viewState.hideProductContextDialog()
    }

    fun showProductDeleteDialog(position: Int) {
        viewState.showProductDeleteDialog(position)
    }

    fun hideProductDeleteDialog() {
        viewState.hideProductDeleteDialog()
    }

    fun showProductInfo(position: Int) {
        viewState.showProductInfoDialog(productsList[position].getInfo())
    }

    fun hideProductInfoDialog() {
        viewState.hideProductInfoDialog()
    }

    private fun loadAllProducts() {
        productsList = productDao.loadAllProducts()
        Collections.sort(productsList, getCurrentSortMethod())
        viewState.onProductsLoaded(productsList)
    }

    private fun getCurrentSortMethod(): SortProductsBy {
        val defaultSortMethodName = SortProductsBy.DATE.toString()
        val currentSortMethodName = getProductsSortMethodName(defaultSortMethodName)
        return SortProductsBy.valueOf(currentSortMethodName)
    }

    private fun getProductPositionById(productId: Long) = productsList.indexOfFirst { it.id == productId }

}

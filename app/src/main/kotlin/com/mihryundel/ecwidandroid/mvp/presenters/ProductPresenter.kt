package com.mihryundel.ecwidandroid.mvp.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.mihryundel.ecwidandroid.EcwidAndroidApplication
import com.mihryundel.ecwidandroid.bus.ProductDeleteAction
import com.mihryundel.ecwidandroid.bus.ProductEditAction
import com.mihryundel.ecwidandroid.mvp.model.Product
import com.mihryundel.ecwidandroid.mvp.model.ProductDao
import com.mihryundel.ecwidandroid.mvp.views.ProductView
import org.greenrobot.eventbus.EventBus
import java.util.*
import javax.inject.Inject

@InjectViewState
class ProductPresenter(val productId: Long) : MvpPresenter<ProductView>() {

    @Inject
    lateinit var productDao: ProductDao
    private lateinit var product: Product

    init {
        EcwidAndroidApplication.graph.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        product = productDao.getProductById(productId)!!
        viewState.showProduct(product)
    }

    fun saveProduct(title: String, text: String, price: Double, balance: Int) {
        product.title = title
        product.text = text
        product.price = price
        product.balance = balance
        productDao.saveProduct(product)
        EventBus.getDefault().post(ProductEditAction(product.id))
        viewState.onProductSaved()
    }

    fun deleteProduct() {
        //after deletion product id will be 0,
        // so we should save one before delete operation
        val productId = product.id
        productDao.deleteProduct(product)
        EventBus.getDefault().post(ProductDeleteAction(productId))
        viewState.onProductDeleted()
    }

    fun showProductDeleteDialog() {
        viewState.showProductDeleteDialog()
    }

    fun hideProductDeleteDialog() {
        viewState.hideProductDeleteDialog()
    }

}

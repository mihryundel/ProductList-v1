package com.mihryundel.ecwidandroid.ui.activities

import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.pawegio.kandroid.onQueryChange
import com.mihryundel.ecwidandroid.R
import com.mihryundel.ecwidandroid.mvp.model.Product
import com.mihryundel.ecwidandroid.mvp.presenters.MainPresenter
import com.mihryundel.ecwidandroid.mvp.views.MainView
import com.mihryundel.ecwidandroid.ui.adapters.ProductsAdapter
import com.mihryundel.ecwidandroid.ui.commons.ItemClickSupport
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MvpAppCompatActivity(), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter
    private var productContextDialog: MaterialDialog? = null
    private var productDeleteDialog: MaterialDialog? = null
    private var productInfoDialog: MaterialDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        with(ItemClickSupport.addTo(productsList)) {
            setOnItemClickListener { _, position, _ -> presenter.showProductContextDialog(position);}
        }

        newProductFab.setOnClickListener { presenter.openNewProduct() }
    }

    override fun onProductsLoaded(products: List<Product>) {
        productsList.adapter = ProductsAdapter(products)
        updateView()
    }

    override fun updateView() {
        productsList.adapter.notifyDataSetChanged()
        if (productsList.adapter.itemCount == 0) {
            productsList.visibility = View.GONE
            tvProductsIsEmpty.visibility = View.VISIBLE
        } else {
            productsList.visibility = View.VISIBLE
            tvProductsIsEmpty.visibility = View.GONE
        }
    }

    override fun onProductDeleted() {
        updateView()
        Toast.makeText(this, R.string.product_deleted, Toast.LENGTH_SHORT).show()
    }

    override fun onAllProductsDeleted() {
        updateView()
        Toast.makeText(this, R.string.all_products_deleted, Toast.LENGTH_SHORT).show()
    }

    override fun onSearchResult(products: List<Product>) {
        productsList.adapter = ProductsAdapter(products)
    }

    override fun showProductContextDialog(productPosition: Int) {
        productContextDialog = MaterialDialog.Builder(this)
                .items(R.array.main_product_context)
                .itemsCallback { _, _, position, _ ->
                    onContextDialogItemClick(position, productPosition)
                    presenter.hideProductContextDialog()
                }
                .cancelListener { presenter.hideProductContextDialog() }
                .show()
    }

    override fun hideProductContextDialog() {
        productContextDialog?.dismiss()
    }

    override fun showProductDeleteDialog(productPosition: Int) {
        productDeleteDialog = MaterialDialog.Builder(this)
                .title(getString(R.string.product_deletion_title))
                .content(getString(R.string.product_deletion_message))
                .positiveText(getString(R.string.yes))
                .negativeText(getString(R.string.no))
                .onPositive { _, _ ->
                    presenter.deleteProductByPosition(productPosition)
                    productInfoDialog?.dismiss()
                }
                .onNegative { _, _ -> presenter.hideProductDeleteDialog() }
                .cancelListener { presenter.hideProductDeleteDialog() }
                .show()
    }

    override fun hideProductDeleteDialog() {
        productDeleteDialog?.dismiss()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun openProductScreen(productId: Long) {
        startActivity(ProductActivity.buildIntent(this, productId))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuDeleteAllProducts -> presenter.deleteAllProducts()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onContextDialogItemClick(contextItemPosition: Int, productPosition: Int) {
        when (contextItemPosition) {
            0 -> presenter.openProduct(productPosition)
            1 -> presenter.showProductDeleteDialog(productPosition)
        }
    }

}

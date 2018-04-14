package com.mihryundel.ecwidandroid.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.mihryundel.ecwidandroid.R
import com.mihryundel.ecwidandroid.mvp.model.Product
import com.mihryundel.ecwidandroid.mvp.presenters.ProductPresenter
import com.mihryundel.ecwidandroid.mvp.views.ProductView
import com.mihryundel.ecwidandroid.utils.formatDate
import kotlinx.android.synthetic.main.activity_product.*

class ProductActivity : MvpAppCompatActivity(), ProductView {

    companion object {
        const val NOTE_DELETE_ARG = "product_id"

        fun buildIntent(activity: Activity, productId: Long) : Intent{
            val intent = Intent(activity, ProductActivity::class.java)
            intent.putExtra(NOTE_DELETE_ARG, productId)
            return intent
        }
    }

    @InjectPresenter
    lateinit var presenter: ProductPresenter
    private var productDeleteDialog: MaterialDialog? = null
    private var productInfoDialog: MaterialDialog? = null

    @ProvidePresenter
    fun provideHelloPresenter(): ProductPresenter {
        val productId = intent.extras.getLong(NOTE_DELETE_ARG, -1)
        return ProductPresenter(productId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        etTitle.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                val editText = view as EditText
                editText.setSelection((editText.text.length))
            }
        }
    }

    override fun showProduct(product: Product) {
        tvProductDate.text = formatDate(product.changedAt)
        etTitle.setText(product.title)
        etText.setText(product.text)
    }

    override fun showProductInfoDialog(productInfo: String) {
        productInfoDialog = MaterialDialog.Builder(this)
                .title(R.string.product_info)
                .positiveText(R.string.ok)
                .content(productInfo)
                .onPositive { materialDialog, dialogAction -> presenter.hideProductInfoDialog() }
                .cancelListener { presenter.hideProductInfoDialog() }
                .show()
    }

    override fun hideProductInfoDialog() {
        productInfoDialog?.dismiss()
    }

    override fun showProductDeleteDialog() {
        productDeleteDialog = MaterialDialog.Builder(this)
                .title(getString(R.string.product_deletion_title))
                .content(getString(R.string.product_deletion_message))
                .positiveText(getString(R.string.yes))
                .negativeText(getString(R.string.no))
                .onPositive { _, _ ->
                    presenter.hideProductDeleteDialog()
                    presenter.deleteProduct()
                }
                .onNegative { _, _ -> presenter.hideProductDeleteDialog() }
                .cancelListener { presenter.hideProductDeleteDialog() }
                .show()
    }


    override fun hideProductDeleteDialog() {
        productDeleteDialog?.dismiss()
    }

    override fun onProductSaved() {
        Toast.makeText(this, "Product saved", Toast.LENGTH_SHORT).show()
    }

    override fun onProductDeleted() {
        Toast.makeText(this, R.string.product_deleted, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.product, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuSaveProduct -> presenter.saveProduct(etTitle.text.toString(), etText.text.toString())

            R.id.menuDeleteProduct -> presenter.showProductDeleteDialog()

            R.id.menuProductInfo -> presenter.showProductInfoDialog()
        }
        return super.onOptionsItemSelected(item)
    }

}
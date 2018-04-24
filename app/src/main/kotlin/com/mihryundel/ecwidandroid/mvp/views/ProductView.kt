package com.mihryundel.ecwidandroid.mvp.views

import com.arellomobile.mvp.MvpView
import com.mihryundel.ecwidandroid.mvp.model.Product

interface ProductView : MvpView {

    fun showProduct(product: Product)

    fun onProductSaved()

    fun onProductDeleted()

    fun showProductDeleteDialog()

    fun hideProductDeleteDialog()

}

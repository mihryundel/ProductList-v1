package com.mihryundel.ecwidandroid.mvp.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.mihryundel.ecwidandroid.mvp.model.Product

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface MainView : MvpView {

    fun onProductsLoaded(products: List<Product>)

    fun updateView()

    fun onSearchResult(products: List<Product>)

    fun onAllProductsDeleted()

    fun onProductDeleted()

    fun showProductDeleteDialog(productPosition: Int)

    fun hideProductDeleteDialog()

    fun showProductContextDialog(productPosition: Int)

    fun hideProductContextDialog()

    fun openProductScreen(productId: Long)

}
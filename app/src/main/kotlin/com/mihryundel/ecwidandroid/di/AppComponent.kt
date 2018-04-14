package com.mihryundel.ecwidandroid.di

import dagger.Component
import com.mihryundel.ecwidandroid.mvp.presenters.MainPresenter
import com.mihryundel.ecwidandroid.mvp.presenters.ProductPresenter
import javax.inject.Singleton

@Singleton
@Component(modules = [ProductDaoModule::class])
interface AppComponent {
    fun inject(mainPresenter: MainPresenter)

    fun inject(productPresenter: ProductPresenter)
}
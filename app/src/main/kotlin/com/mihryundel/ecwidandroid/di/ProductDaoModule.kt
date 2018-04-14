package com.mihryundel.ecwidandroid.di

import dagger.Module
import dagger.Provides
import com.mihryundel.ecwidandroid.mvp.model.ProductDao
import javax.inject.Singleton

@Module
class ProductDaoModule {

    @Provides
    @Singleton
    fun provideProductDao(): ProductDao = ProductDao()

}
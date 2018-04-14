package com.mihryundel.ecwidandroid

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.mihryundel.ecwidandroid.di.AppComponent
import com.mihryundel.ecwidandroid.di.DaggerAppComponent
import com.mihryundel.ecwidandroid.di.ProductDaoModule
import com.mihryundel.ecwidandroid.mvp.model.AppDatabase
import com.mihryundel.ecwidandroid.mvp.model.Product
import com.reactiveandroid.ReActiveAndroid
import com.reactiveandroid.ReActiveConfig
import com.reactiveandroid.internal.database.DatabaseConfig

class EcwidAndroidApplication : Application() {

    companion object {
        lateinit var graph: AppComponent
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        context = this
        graph = DaggerAppComponent.builder().productDaoModule(ProductDaoModule()).build()

        val appDatabaseConfig = DatabaseConfig.Builder(AppDatabase::class.java)
                .addModelClasses(Product::class.java)
                .build()

        ReActiveAndroid.init(ReActiveConfig.Builder(this)
                .addDatabaseConfigs(appDatabaseConfig)
                .build())
    }

}

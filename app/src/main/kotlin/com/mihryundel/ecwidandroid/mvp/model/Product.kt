package com.mihryundel.ecwidandroid.mvp.model

import com.mihryundel.ecwidandroid.utils.formatDate
import com.reactiveandroid.Model
import com.reactiveandroid.annotation.Column
import com.reactiveandroid.annotation.PrimaryKey
import com.reactiveandroid.annotation.Table
import java.util.*

@Table(name = "Products", database = AppDatabase::class)
class Product : Model {

    @PrimaryKey
    var id: Long = 0
    @Column(name = "title")
    var title: String? = null
    @Column(name = "text")
    var text: String? = null
    @Column(name = "price")
    var price: Double? = null
    @Column(name = "balance")
    var balance: Int? = null

    constructor(title: String) {
        this.title = title
        this.price = 0.0
        this.balance = 0
    }

    constructor()

    fun getInfo(): String = "Название:\n$title\n" +
            "Цена:\n${price}\n" +
            "Остаток:\n${balance}"

}
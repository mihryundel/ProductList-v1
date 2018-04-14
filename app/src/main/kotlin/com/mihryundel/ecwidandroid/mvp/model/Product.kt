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
    @Column(name = "created_at")
    var createAt: Date? = null
    @Column(name = "change_at")
    var changedAt: Date? = null

    constructor(title: String, createDate: Date) {
        this.title = title
        this.createAt = createDate
        this.changedAt = createDate
    }

    constructor()

    fun getInfo(): String = "Title:\n$title\n" +
            "Created at:\n${formatDate(createAt)}\n" +
            "Changed at:\n${formatDate(changedAt)}"

}
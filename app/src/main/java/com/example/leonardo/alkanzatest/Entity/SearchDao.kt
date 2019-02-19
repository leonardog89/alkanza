package com.example.leonardo.alkanzatest.Entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class SearchDao(
    @PrimaryKey open var id: Int? = null,
    open var radio: String? = null,
    open var lat: Double? = null,
    open var lng: Double? = null,
    open var minimum: Double? = null
)
    : RealmObject() {
}
package com.example.leonardo.alkanzatest.Entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class PlaceDao(
    @PrimaryKey open var id: String? = null,
    open var name: String? = null,
    open var lat: Double? = null,
    open var lng: Double? = null
)
    : RealmObject() {
}

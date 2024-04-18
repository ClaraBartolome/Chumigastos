package com.example.myapplication.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trifle_table")
data class TrifleModel(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "name", defaultValue = "") var name: String = "",
    @ColumnInfo(name = "storeName", defaultValue = "") var storeName: String = "",
    @ColumnInfo(name = "dateOfCreation", defaultValue = "") var dateOfCreation: String = "",
    @ColumnInfo(name = "dateOfModification", defaultValue = "") var dateOfModification: String = "",
    @ColumnInfo(name = "category", defaultValue = "") var category: String = "",
    @ColumnInfo(name = "yenPrice", defaultValue = "") var yenPrice: String = "",
    @ColumnInfo(name = "eurPrice", defaultValue = "") var eurPrice: String = "",
) {
    override fun toString(): String {
        return "$name $storeName $dateOfCreation $category $yenPrice $eurPrice"
    }
}


package com.example.myapplication.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trifle_table")
data class TrifleModel(
    @PrimaryKey(autoGenerate = true)var id: Int = 0,
    @ColumnInfo(name = "name", defaultValue = "")var name: String = "",
    @ColumnInfo(name = "storeName", defaultValue = "")var storeName: String = "",
    @ColumnInfo(name = "dateOfCreation", defaultValue = "")var dateOfCreation: String = "",
    @ColumnInfo(name = "category", defaultValue = "")var folder: String = "",
    @ColumnInfo(name = "yenPrice", defaultValue = "")var yenPrice: String = "",
    @ColumnInfo(name = "eurPrice", defaultValue = "")var eurPrice: String = "",
) {
}
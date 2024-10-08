package com.example.myapplication.db


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.db.models.TrifleModel

@Dao
interface TrifleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrifle(trifle: TrifleModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllTrifles(trifle: List<TrifleModel>)

    @Query("SELECT * FROM trifle_table ORDER BY dateOfCreation DESC")
    suspend fun getAllDateOfCreationDesc(): List<TrifleModel>

    @Query("SELECT * FROM trifle_table ORDER BY dateOfCreation ASC")
    suspend fun getAllDateOfCreationAsc(): List<TrifleModel>

    @Query("SELECT * FROM trifle_table ORDER BY name ASC")
    suspend fun getAllOrderedByNameAsc(): List<TrifleModel>

    @Query("SELECT * FROM trifle_table ORDER BY name DESC")
    suspend fun getAllOrderedByNameDesc(): List<TrifleModel>

    @Query("SELECT * FROM trifle_table ORDER BY storeName ASC")
    suspend fun getAllOrderedByStoreNameAsc(): List<TrifleModel>

    @Query("SELECT * FROM trifle_table ORDER BY storeName DESC")
    suspend fun getAllOrderedByStoreNameDesc(): List<TrifleModel>

    @Query("SELECT * FROM trifle_table ORDER BY dateOfModification DESC")
    suspend fun getAllDateOfModDesc(): List<TrifleModel>

    @Query("SELECT * FROM trifle_table ORDER BY dateOfModification ASC")
    suspend fun getAllDateOfModAsc(): List<TrifleModel>

    @Delete
    suspend fun deleteTrifle(trifle: TrifleModel)

    @Update
    suspend fun updateTrifle(trifle: TrifleModel)

    /*@Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLink(link: LinkModel)

    @Update
    suspend fun updateLink(link: LinkModel)

    @Delete
    suspend fun deleteLink(link: LinkModel)

    @Delete
    suspend fun deleteListLink(linkList: List<LinkModel>)

    @Query("DELETE FROM link_table")
    suspend fun deleteAll()

    @Query("DELETE FROM link_table WHERE folder = :folderName")
    suspend fun deleteAllinFolder(folderName: String)

    @Query("SELECT * FROM link_table ")
    fun getAll(): List<LinkModel>

    @Query("SELECT * FROM link_table ORDER BY name ASC")
    suspend fun getAllOrderedByNameAsc(): List<LinkModel>

    @Query("SELECT * FROM link_table ORDER BY name DESC")
    suspend fun getAllOrderedByNameDesc(): List<LinkModel>

    @Query("SELECT * FROM link_table ORDER BY dateOfCreation ASC")
    suspend fun getAllOrderedByDateOfCreationAsc(): List<LinkModel>

    @Query("SELECT * FROM link_table ORDER BY dateOfCreation DESC")
    suspend fun getAllOrderedByDateOfCreationDesc(): List<LinkModel>

    @Query("SELECT * FROM link_table ORDER BY dateOfModified ASC")
    suspend fun getAllOrderedByDateOfModifiedAsc(): List<LinkModel>

    @Query("SELECT * FROM link_table ORDER BY dateOfModified DESC")
    suspend fun getAllOrderedByDateOfModifiedDesc(): List<LinkModel>*/
}
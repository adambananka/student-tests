package cz.bald.student_tests.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Delete

interface AbstractDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg items: T): List<Long>

    @Update
    suspend fun update(item: T)

    @Delete
    suspend fun delete(item: T)
}
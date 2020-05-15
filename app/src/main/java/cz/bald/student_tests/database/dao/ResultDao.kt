package cz.bald.student_tests.database.dao

import androidx.room.Dao
import androidx.room.Query
import cz.bald.student_tests.model.Result

@Dao
interface ResultDao : AbstractDao<Result> {

    @Query("SELECT * FROM result WHERE id == :id")
    suspend fun getById(id: Long): Result

    @Query("SELECT * FROM result")
    suspend fun getAll(): List<Result>
}
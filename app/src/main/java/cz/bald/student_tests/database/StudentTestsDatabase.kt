package cz.bald.student_tests.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cz.bald.student_tests.database.dao.ResultDao
import cz.bald.student_tests.model.Result


@Database(entities = [Result::class], version = 1)
@TypeConverters(Converters::class)
abstract class StudentTestsDatabase : RoomDatabase() {

    abstract fun resultDao(): ResultDao

    companion object {
        private var INSTANCE: StudentTestsDatabase? = null

        fun getInstance(context: Context): StudentTestsDatabase {
            if (INSTANCE == null) {
                synchronized(StudentTestsDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        StudentTestsDatabase::class.java, "student_tests-database")
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}
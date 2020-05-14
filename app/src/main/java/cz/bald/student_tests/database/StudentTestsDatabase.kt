package cz.bald.student_tests.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [/*Entity::class*/], version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class StudentTestsDatabase : RoomDatabase() {

    //abstract fun entityDao(): EntityDao

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
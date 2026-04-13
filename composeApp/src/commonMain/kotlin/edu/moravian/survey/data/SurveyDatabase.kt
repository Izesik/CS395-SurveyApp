package edu.moravian.survey.data

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

/**
 * The Room database for this app. It contains two tables: one for surveys and one for question results.
 * The survey table is the parent of the question result table, so deleting a survey will also delete
 * all of its question results.
 */

@Database(
    entities = [SurveyEntity::class, QuestionResultEntity::class],
    version = 1,
    exportSchema = false,
)
// Tell Room to use our custom type converters for handling complex data types like Set<Int>.
@TypeConverters(Converters::class)
@ConstructedBy(SurveyDatabaseConstructor::class)
abstract class SurveyDatabase : androidx.room.RoomDatabase() {
    abstract fun surveyDao(): SurveyDao
}

// This function is used by Room to create an instance of the SurveyDatabase. It configures the database
// to use the BundledSQLiteDriver, which is a cross-platform SQLite driver that works on
// Android, iOS, and desktop platforms.,

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object SurveyDatabaseConstructor : RoomDatabaseConstructor<SurveyDatabase> {
    override fun initialize(): SurveyDatabase
}

fun getSurveyDatabase(
    builder: androidx.room.RoomDatabase.Builder<SurveyDatabase>,
): SurveyDatabase = builder
    .setDriver(BundledSQLiteDriver())
    .setQueryCoroutineContext(Dispatchers.IO)
    .build()

package edu.moravian.survey.data

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import androidx.sqlite.driver.bundled.BundledSQLiteDriver


/**
 * The Room database for this app. It contains two tables: one for surveys and one for question results.
 * The survey table is the parent of the question result table, so deleting a survey will also delete
 * all of its question results.
 */

@Database(
    entities = [SurveyEntity::class, QuestionResultEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class SurveyDatabase : androidx.room.RoomDatabase() {
    abstract fun surveyDao(): SurveyDao
}
fun getSurveyDatabase(
    builder: androidx.room.RoomDatabase.Builder<SurveyDatabase>,
): SurveyDatabase = builder
    .setDriver(BundledSQLiteDriver())
    .setQueryCoroutineContext(Dispatchers.IO)
    .build(
)

package edu.moravian.survey.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface SurveyDao {
    /**
     * Inserts a survey and its associated questions into the database.
     * Returns the ID of the inserted survey.
     */
    @Insert
    suspend fun insertSurvey(survey: SurveyEntity): Long

    /**
     * Inserts a list of questions into the database. Each question should have its surveyId property set
     * to the ID of the survey it belongs to.
     */
    @Insert
    suspend fun insertQuestions(questions: List<QuestionResultEntity>)

    /**
     * Inserts a survey and its associated questions into the database in a single transaction.
     * The survey will be inserted first to obtain its ID, which will then be set on each question before
     * they are inserted.
     */
    @Transaction
    suspend fun insertSurveyWithQuestions(survey: SurveyEntity, questions: List<QuestionResultEntity>) {
        val surveyId = insertSurvey(survey)
        val questionsWithSurveyId = questions.map { it.copy(surveyId = surveyId) }
        insertQuestions(questionsWithSurveyId)
    }

    /**
     * Retrieves all surveys from the database, along with their associated questions, ordered by dateTime
     * in descending order (newest first).
     */
    @Transaction
    @Query("SELECT * FROM SurveyEntity ORDER BY dateTime DESC")
    suspend fun getAllSurveys(): List<SurveyWithQuestions>

    /**
     * Retrieves the most recent survey from the database, along with its associated questions, or null if
     * there are no surveys.
     */
    @Transaction
    @Query("SELECT * FROM SurveyEntity ORDER BY dateTime DESC LIMIT 1")
    suspend fun getRecentSurvey(): SurveyWithQuestions?

    /**
     * Retrieves a specific survey by its ID from the database, along with its associated questions, or null
     * if no survey with the given ID exists.
     */
    @Transaction
    @Query("SELECT * FROM SurveyEntity WHERE id = :id")
    suspend fun getSurveyById(id: Long): SurveyWithQuestions?
}

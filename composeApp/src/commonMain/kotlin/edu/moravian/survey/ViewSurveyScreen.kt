package edu.moravian.survey

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.moravian.survey.data.SurveyDao
import edu.moravian.survey.data.SurveyWithQuestions
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import surveytaker.composeapp.generated.resources.*

/**
 * Navigation destination for the screen that shows a survey that has already been taken. The survey
 * id must be provided.
 */
@Serializable
data class ViewSurveyScreenDest(
    val surveyId: Long,
)

/**
 * Displays a survey that has already been taken. The survey is not editable, but it shows the
 * answers that were given.
 */
@Composable
fun ViewSurveyScreen(
    surveyId: Long,
    dao: SurveyDao,
) {
    var loading by remember { mutableStateOf(true) }
    var surveyResult by remember { mutableStateOf<SurveyWithQuestions?>(null) }
    var survey by remember { mutableStateOf(AMISOS_R_SURVEY) }
    LaunchedEffect(surveyId) {
        surveyResult = dao.getSurveyById(surveyId)
        survey = surveyResult?.let { AMISOS_R_SURVEY.load(it) } ?: AMISOS_R_SURVEY
        loading = false
    }

    if (loading) {
        Row {
            CircularProgressIndicator()
            Text(stringResource(Res.string.loading))
        }
        return
    }

    Column(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        surveyResult?.let { result ->
            Text(stringResource(Res.string.survey_results_from, formatEpochMillis(result.survey.dateTime)))
            Text(stringResource(Res.string.score, result.survey.score))
        }
        SurveyView(survey, false, null)
    }
}

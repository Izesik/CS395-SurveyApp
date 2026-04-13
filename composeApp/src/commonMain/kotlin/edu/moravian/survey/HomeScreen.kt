package edu.moravian.survey

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.moravian.survey.data.SurveyWithQuestions
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import surveytaker.composeapp.generated.resources.*

/**
 * The home screen destination, which shows the current status and allows the user to take a survey
 * or view their history.
 */
@Serializable
data object HomeScreen

/**
 * The home screen, which shows the current status and allows the user to take a survey or view
 * their history.
 */
@Composable
fun HomeScreen(
    recentSurvey: SurveyWithQuestions?,
    onTakeSurvey: () -> Unit,
    onOpenHistory: () -> Unit,
) {
    Column(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        StatusText(recentSurvey)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onTakeSurvey) { Text(stringResource(Res.string.take_survey)) }
        TextButton(onClick = onOpenHistory) { Text(stringResource(Res.string.view_history)) }
    }
}

@Composable
private fun StatusText(recentSurvey: SurveyWithQuestions?) {
    val now = currentTimeMillis()

    if (recentSurvey == null) {
        Text(stringResource(Res.string.no_survey_results_yet))
        return
    }

    Text(stringResource(Res.string.last_completed, formatEpochMillis(recentSurvey.survey.dateTime)))
    Text(stringResource(Res.string.last_score, recentSurvey.survey.score))

    reminderMessage(now, recentSurvey.survey.dateTime)?.let { message ->
        Text(stringResource(message))
    }
}

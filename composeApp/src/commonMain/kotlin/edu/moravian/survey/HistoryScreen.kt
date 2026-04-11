package edu.moravian.survey

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.moravian.survey.data.SurveyWithQuestions
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import surveytaker.composeapp.generated.resources.*

/**
 * History screen destination. Shows a list of surveys the user has taken, with the most recent at
 * the top.
 */
@Serializable
data object HistoryScreen

/**
 * Shows a list of surveys the user has taken, with the most recent at the top. Tapping on one
 * opens the [ViewSurveyScreen] for that survey.
 */
@Composable
fun HistoryScreen(
    entries: List<SurveyWithQuestions>,
    onOpenSurvey: (Long) -> Unit,
) {
    Column(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(stringResource(Res.string.history), style = MaterialTheme.typography.headlineSmall)

        if (entries.isEmpty()) {
            Text(stringResource(Res.string.no_history))
            return@Column
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(entries, key = { it.survey.id }) { result ->
                Card(modifier = Modifier.fillMaxWidth().clickable { onOpenSurvey(result.survey.id) }) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(stringResource(Res.string.date) + formatEpochMillis(result.survey.dateTime))
                        Text(stringResource(Res.string.score) + result.survey.score)
                    }
                }
            }
        }
    }
}

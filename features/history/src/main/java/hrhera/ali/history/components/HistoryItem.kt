package hrhera.ali.history.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import hrhera.ali.core.Constants
import hrhera.ali.domain.models.Weather


@Composable
fun HistoryItem(
    weatherEntry: Weather,
    onNavToWeather: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp)
            .clickable { onNavToWeather() },
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .clickable { onNavToWeather() }
                .fillMaxWidth()
                .padding(all = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            val painter =
                rememberAsyncImagePainter(
                    model = Constants.ICON_URL.replace(
                        "icon",
                        weatherEntry.icon
                    )
                )
            Image(
                modifier = Modifier
                    .size(60.dp)
                    .padding(end = 16.dp),
                painter = painter,
                contentDescription = "Weather icon for ${weatherEntry.description}",
                contentScale = ContentScale.Fit
            )

            Column {
                Time(weatherEntry)
                Text(
                    text = "${weatherEntry.description}, ${weatherEntry.tempCelsius}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

        }
    }
}

@Composable
private fun Time(weatherEntry: Weather) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = weatherEntry.date,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = weatherEntry.time,
            fontSize = 18.sp,
        )
    }
}

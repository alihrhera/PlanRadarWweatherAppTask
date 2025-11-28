package hrhera.ali.wether_details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import hrhera.ali.core.Constants
import hrhera.ali.core.utils.capitalizeWords
import hrhera.ali.wether_details.DetailsUiState
import hrhera.ali.wether_details.R

@Composable
fun WeatherScreen(uiState: DetailsUiState) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(32.dp))
        if (uiState.errorEntity.isNullOrBlank().not())
            Text(
                text = uiState.errorEntity,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
            )
        Spacer(modifier = Modifier.height(64.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = uiState.name.capitalizeWords(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                val imageUrl = Constants.ICON_URL.replace("icon", uiState.icon)
                val painter = rememberAsyncImagePainter(model = imageUrl)

                Image(
                    modifier = Modifier
                        .size(200.dp)
                        .padding(bottom = 24.dp),
                    painter = painter,
                    contentDescription = stringResource(R.string.weather_icon),
                    contentScale = ContentScale.Fit
                )

                WeatherDetailRow(
                    label = stringResource(R.string.description),
                    value = uiState.description,
                )
                WeatherDetailRow(
                    label = stringResource(R.string.temperature),
                    value = uiState.temp,
                )
                WeatherDetailRow(
                    label = stringResource(R.string.humidity),
                    value = uiState.humidity,
                )
                WeatherDetailRow(
                    label = stringResource(R.string.windspeed),
                    value = uiState.windSpeed,
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            text = stringResource(
                R.string.weather_information_received_on,
                uiState.name,
                uiState.time
            ),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 14.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWeatherScreen() {
    WeatherScreen(DetailsUiState())
}
package hrhera.ali.planradarweatherapp.ui.home.components
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun CityItem(city: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

      /*  Icon(
            imageVector = Icons.Default.Home,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(22.dp)
        )*/

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = city,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

       /* Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Info",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(18.dp)
        )*/
    }
}

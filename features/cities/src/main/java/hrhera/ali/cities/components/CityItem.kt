package hrhera.ali.cities.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun CityItem(city: String,onClick:(String)->Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable{
                onClick(city)
            }
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Default.LocationCity,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = city,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Info",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(18.dp)
        )
    }
}

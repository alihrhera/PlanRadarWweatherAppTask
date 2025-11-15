package hrhera.ali.cities.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CitiesHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(99.dp)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = "Cities",
            style = MaterialTheme.typography.headlineSmall.copy(
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 24.sp
            ),
            modifier = Modifier
                .padding(start = 22.dp, top = 72.dp)
        )
    }
}
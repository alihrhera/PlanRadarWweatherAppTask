package hrhera.ali.cities.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoMode
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CityTitle(isObserved: Boolean, onMakeObserver: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Cities", color = Color.Black, style = TextStyle(fontSize = 24.sp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Auto Observe",color = Color.Black, style = TextStyle(fontSize = 12.sp))
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                tint = if (isObserved) Color.Green else Color.Red,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onMakeObserver()
                    },
                imageVector = Icons.Default.AutoMode,
                contentDescription = "Auto Observe"
            )
        }

    }

}


package hrhera.ali.cities.components

import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hrhera.ali.core.utils.capitalizeWords

const val threshold = 64f

@Composable
fun CityItem(
    city: String,
    isLoading: Boolean = false,
    onMoveToHistory: () -> Unit = {},
    onMoveToDetails: () -> Unit = {},
    onDeleteCity: () -> Unit = {}
) {
    var dragOffset by remember { mutableStateOf(Offset(0f, 0f)) }
    var cardWeight by remember { mutableStateOf(1f) }

    val swipeModifier = Modifier
        .offset(x = -dragOffset.x.dp)
        .pointerInput(Unit) {
            detectHorizontalDragGestures { _, dr ->
                dragOffset = Offset(
                    x = if (dr < 0) -2f else 0f,
                    y = dragOffset.y
                )
            }
        }

    val animatedOffset = animateOffsetAsState(
        targetValue = Offset(if (dragOffset.x < 0) -48f else 0f, 0f),
        animationSpec = tween(durationMillis = 150)
    )

    Row {

        Card(
            modifier = Modifier
                .offset(x = animatedOffset.value.x.dp)
                .weight(cardWeight)
                .padding(vertical = 8.dp)
                .clickable { onMoveToHistory() },
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Row(
                modifier =
                    swipeModifier.padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationCity,
                    contentDescription = "show $city history",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(42.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = city.capitalizeWords(),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "get $city current weather",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(42.dp)
                        .clickable {
                            onMoveToDetails()
                        }
                )
            }
        }
        if (dragOffset.x < 0) {
            cardWeight=.95f
            Box(
                Modifier
                    .width(48.dp)
                    .height(72.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading)
                    CircularProgressIndicator(modifier = Modifier.size(32.dp))
                else Icon(
                    imageVector = Icons.Default.DeleteForever,
                    contentDescription = "delete $city",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onDeleteCity() })
            }
        }else
            cardWeight=1f
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CityItemPreview() {
    CityItem(city = "Home")
}

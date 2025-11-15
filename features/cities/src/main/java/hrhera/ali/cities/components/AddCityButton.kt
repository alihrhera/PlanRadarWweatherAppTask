package hrhera.ali.cities.components
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddCityButton(modifier: Modifier = Modifier,onClick: () -> Unit) {
    Button(
        onClick =onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(32.dp),
        modifier = modifier
            .height(56.dp)
            .width(180.dp)
    ) {
        Icon(Icons.Default.Add, contentDescription = "Add city")
        Spacer(modifier = Modifier.width(8.dp))
        Text("Add city", style = MaterialTheme.typography.labelLarge)
    }
}

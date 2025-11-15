package hrhera.ali.cities.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddCityBottomSheetContent(
    onAddClick: (String) -> Unit,
    onCancelClick: () -> Unit,
    name: String = "",
    isLoaderVisible: Boolean = false
) {
    var cityName by remember { mutableStateOf(name) }
    var showError by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(
            text = "Add New City",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = cityName,
            onValueChange = {
                cityName = it
                if (showError) showError = false
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("City Name") },
            placeholder = { Text("Enter city name...") },
            singleLine = true,
            isError = showError,
            supportingText = {
                if (cityName.isBlank()) {
                    Text("Please enter a city name")
                }
            })

        Spacer(modifier = Modifier.height(32.dp))
        if (isLoaderVisible) Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = onCancelClick, colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Text("Cancel")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    if (cityName.isBlank()) {
                        showError = true
                        return@Button
                    }
                    showError = false
                    onAddClick(cityName)
                },
                enabled = cityName.isNotBlank() && !isLoaderVisible,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.12f
                    )
                )
            ) {
                Text("Add City")
            }
        }
    }
}
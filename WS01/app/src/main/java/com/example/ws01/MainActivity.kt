package com.example.ws01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ws01.ui.theme.WS01Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WS01Theme {
                TemperatureConverterUI()
            }
        }
    }
}

@Composable
fun TemperatureConverterUI() {
    var fahrenheitInput by remember { mutableStateOf("") }
    var celsiusInput by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "My Temperature Converter - Portrait Mode",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Fahrenheit Input
        Text(
            text = "Enter Fahrenheit Temp Below",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        TextField(
            value = fahrenheitInput,
            onValueChange = { input ->
                fahrenheitInput = input
                celsiusInput = if (input.isNotBlank()) {
                    errorText = ""
                    fahrenheitToCelsius(input)
                } else ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Celsius Input
        Text(
            text = "Enter Celsius Temp Below",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        TextField(
            value = celsiusInput,
            onValueChange = { input ->
                celsiusInput = input
                fahrenheitInput = if (input.isNotBlank()) {
                    errorText = ""
                    celsiusToFahrenheit(input)
                } else ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Button for manual conversion (optional)
        Button(
            onClick = {
                if (fahrenheitInput.isBlank() && celsiusInput.isBlank()) {
                    errorText = "Please enter a value to convert"
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Convert Temp")
        }

        // Display Error (if any)
        if (errorText.isNotEmpty()) {
            Text(
                text = errorText,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

fun fahrenheitToCelsius(fahrenheit: String): String {
    return try {
        val f = fahrenheit.toDouble()
        val celsius = (f - 32) * 5 / 9
        "%.2f".format(celsius)
    } catch (e: Exception) {
        ""
    }
}

fun celsiusToFahrenheit(celsius: String): String {
    return try {
        val c = celsius.toDouble()
        val fahrenheit = (c * 9 / 5) + 32
        "%.2f".format(fahrenheit)
    } catch (e: Exception) {
        ""
    }
}

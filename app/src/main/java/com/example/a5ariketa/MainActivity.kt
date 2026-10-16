package com.example.a5ariketa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a5ariketa.ui.theme._5AriketaTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _5AriketaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GGIKalkulua(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun GGIKalkulua(modifier: Modifier = Modifier) {
    var pisuaTestua by remember { mutableStateOf("") }
    var altueraTestua by remember { mutableStateOf("") }
    var adinaTestua by remember { mutableStateOf("") }
    var sexuaTestua by remember { mutableStateOf("") }

    val pisuaKg = pisuaTestua.replace(",", ".").toDoubleOrNull() ?: 0.0
    val altueraCm = altueraTestua.replace(",", ".").toDoubleOrNull() ?: 0.0
    val adinaUrteak = adinaTestua.toIntOrNull() ?: 0
    val sexuaGarbia = sexuaTestua.trim().lowercase()

    val sexuaBalioa = when (sexuaGarbia) {
        "g", "h" -> 1
        "e", "m" -> 0
        else -> null
    }

    val datuakZuzenakDira = pisuaKg > 0.0 && altueraCm > 0.0 && adinaUrteak > 0 && sexuaBalioa != null
    val gmiEmaitza = if (datuakZuzenakDira) {
        val altueraMetrotan = altueraCm / 100.0
        pisuaKg / (altueraMetrotan * altueraMetrotan)
    } else {
        0.0
    }

    val ggiEmaitza = if (datuakZuzenakDira) {
        (1.20 * gmiEmaitza) + (0.23 * adinaUrteak) - (10.8 * sexuaBalioa) - 5.4
    } else {
        0.0
    }

    val gmiFormatua = String.format(Locale.US, "%.2f", gmiEmaitza)
    val ggiFormatua = String.format(Locale.US, "%.2f", ggiEmaitza)

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondonotak),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Gorputz Gantzaren Indizearen Kalkulagailua",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.Green
            )

            Text(text = "Pisua kilogramotan (adib. 65):", fontWeight = FontWeight.SemiBold)
            TextField(
                value = pisuaTestua,
                onValueChange = { pisuaTestua = it },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Text(text = "Altuera zentimetrotan (adib. 170):", fontWeight = FontWeight.SemiBold)
            TextField(
                value = altueraTestua,
                onValueChange = { altueraTestua = it },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Text(text = "Adina urtetan (adib. 22):", fontWeight = FontWeight.SemiBold)
            TextField(
                value = adinaTestua,
                onValueChange = { adinaTestua = it },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Text(text = "Sexua (g/h edo e/m):", fontWeight = FontWeight.SemiBold)
            TextField(
                value = sexuaTestua,
                onValueChange = { sexuaTestua = it },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Kalkulatutako GMI : $gmiFormatua",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray
            )

            Text(
                text = "Kalkulatutako GGI %: $ggiFormatua%",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GGIKalkuluaPreview() {
    _5AriketaTheme {
        GGIKalkulua()
    }
}
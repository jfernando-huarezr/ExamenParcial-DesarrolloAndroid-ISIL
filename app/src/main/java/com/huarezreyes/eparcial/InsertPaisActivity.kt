package com.huarezreyes.eparcial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.huarezreyes.eparcial.ui.theme.EParcialTheme
import com.huarezreyes.eparcial.utils.Total

@OptIn(ExperimentalMaterial3Api::class)
class InsertPaisActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EParcialTheme {
                var codpais by remember { mutableStateOf("") }
                var pais by remember { mutableStateOf("") }
                var capital by remember { mutableStateOf("") }
                var area by remember { mutableStateOf("") }
                var poblacion by remember { mutableStateOf("") }
                var continente by remember { mutableStateOf("") }

                Column(modifier = Modifier.padding(all = 32.dp)) {
                    TextField(value = codpais,
                        label = { Text(text = "Codigo del pais") },
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = {
                            codpais = it
                        })
                    Spacer(modifier = Modifier.size(16.dp))
                    TextField(value = pais,
                        label = { Text(text = "Nombre del pais") },
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = {
                            pais = it
                        })
                    Spacer(modifier = Modifier.size(16.dp))
                    TextField(value = capital,
                        label = { Text(text = "Capital del pais") },
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = {
                            capital = it
                        })
                    Spacer(modifier = Modifier.size(16.dp))
                    TextField(value = area,
                        label = { Text(text = "Area del pais (Km2)") },
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = {
                            area = it
                        })
                    Spacer(modifier = Modifier.size(16.dp))
                    TextField(value = poblacion,
                        label = { Text(text = "Poblacion del pais") },
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = {
                            poblacion = it
                        })
                    Spacer(modifier = Modifier.size(16.dp))
                    TextField(value = continente,
                        label = { Text(text = "Codigo del continente") },
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = {
                            continente = it
                        })
                    Spacer(modifier = Modifier.size(16.dp))
                    Button(onClick = {
                        leerServicioGuardar(codpais, pais, capital, area, poblacion, continente)
                    }) {
                        Text(text = "Registrar Pais")
                    }
                }
            }
        }
    }

    private fun leerServicioGuardar(codpais: String, pais: String, capital: String, area: String, poblacion: String, continente: String) {
        val queue = Volley.newRequestQueue(this)
        val url = Total.rutaServicio + "paisesinsert.php"
        val stringRequest = object: StringRequest(
            Request.Method.POST, url,
            { response ->
                Log.d("DATOS", response)
                Toast.makeText(this, "Nuevo Pais " + response, Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, PaisesActivity::class.java))
                finish()
            },
            {
                Log.d("DATOSERROR", it.message.toString())
            }) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params.put("codigopais", codpais)
                params.put("pais", pais)
                params.put("capital", capital)
                params.put("area", area)
                params.put("poblacion", poblacion)
                params.put("continente", continente)
                return params
            }
        }
        queue.add(stringRequest)
    }
}


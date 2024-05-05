package com.huarezreyes.eparcial

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.huarezreyes.eparcial.datos.DatosAlumnos
import com.huarezreyes.eparcial.ui.theme.EParcialTheme


class AlumnosInsertActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EParcialTheme {
                var nombres by remember { mutableStateOf("") }
                var apellidos by remember { mutableStateOf("") }
                var edad by remember { mutableStateOf("") }
                var correo by remember { mutableStateOf("") }
                var carrera by remember { mutableStateOf("") }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Añadir Alumnos",
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier
                                        .padding(30.dp),
                                    color = Color.White
                                )
                            },

                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            ),

                            navigationIcon = {
                                IconButton(onClick = {
                                    finish()
                                }) {
                                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                                }
                            },
                        )
                    }
                ) {

                    Box (
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                    ){
                        Column(modifier = Modifier.padding(all = 32.dp)) {

                            TextField(value = nombres,
                                label = { Text(text = "Nombres alumno",
                                    color = Color.Black) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Black),
                                textStyle = TextStyle(color = Color.Black),
                                onValueChange = {
                                    nombres= it
                                },

                                )
                            Spacer(modifier = Modifier.size(16.dp))
                            TextField(value = apellidos,
                                label = { Text(text = "Apellidos alumno",
                                    color = Color.Black) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Black),
                                textStyle = TextStyle(color = Color.Black),
                                onValueChange = {
                                    apellidos = it
                                })
                            Spacer(modifier = Modifier.size(16.dp))
                            TextField(value = edad,
                                label = { Text(text = "Edad",
                                    color = Color.Black) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Black),
                                textStyle = TextStyle(color = Color.Black),
                                onValueChange = {
                                    edad = it
                                })

                            Spacer(modifier = Modifier.size(16.dp))
                            TextField(value = correo,
                                label = { Text(text = "Correo Electrónico",
                                    color = Color.Black) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Black),
                                textStyle = TextStyle(color = Color.Black),
                                onValueChange = {
                                    correo = it
                                })

                            Spacer(modifier = Modifier.size(16.dp))
                            TextField(value = carrera,
                                label = { Text(text = "Que carrera estas cursando",
                                    color = Color.Black) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Black),
                                textStyle = TextStyle(color = Color.Black),
                                onValueChange = {
                                    carrera = it
                                })



                            Spacer(modifier = Modifier.size(16.dp))

                            Row (
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Button(onClick = {
                                    guardarDatos(nombres, apellidos, edad, correo, carrera)
                                }) {
                                    Text(text = "Registrar")
                                }

                                Button(onClick = {
                                    startActivity(Intent(this@AlumnosInsertActivity, AlumnosActivity::class.java))
                                }) {
                                    Text(text = "Ver lista de alumnos")
                                }
                            }

                        }
                    }

                }


            }
        }
    }

    private fun guardarDatos(
        nombres: String,
        apellidos: String,
        edad: String,
        correo: String,
        carrera: String
    ) {
        val datosAlumnos = DatosAlumnos(this)
        val autonumerico = datosAlumnos.registrarAlumno(datosAlumnos, nombres, apellidos, edad.toInt(), correo, carrera)
        Toast.makeText(this, "Registrado con id: "+ autonumerico, Toast.LENGTH_SHORT).show()

        startActivity(Intent(this@AlumnosInsertActivity, AlumnosActivity::class.java))
    }
}


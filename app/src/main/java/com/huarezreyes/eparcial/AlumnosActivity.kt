package com.huarezreyes.eparcial

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.huarezreyes.eparcial.datos.DatosAlumnos
import com.huarezreyes.eparcial.ui.theme.EParcialTheme

class AlumnosActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EParcialTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Lista de Alumnos",
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
                        modifier = Modifier.padding(it)
                    )
                    {
                        leerDatos()
                    }
                }
            }
        }
    }

    @Composable private fun leerDatos() {
        val arrayList = ArrayList<HashMap<String, String>>()
        val datosAlumnos = DatosAlumnos(this)
        val cursor: Cursor = datosAlumnos.alumnosSelect(datosAlumnos)

        if(cursor.moveToFirst()) {
            do {
                val idalumno = cursor.getString(cursor.getColumnIndexOrThrow("idalumno"))
                val fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"))
                val nombres = cursor.getString(cursor.getColumnIndexOrThrow("nombres"))
                val apellidos = cursor.getString(cursor.getColumnIndexOrThrow("apellidos"))
                val edad = cursor.getString(cursor.getColumnIndexOrThrow("edad"))
                val correo = cursor.getString(cursor.getColumnIndexOrThrow("correo"))
                val carrera = cursor.getString(cursor.getColumnIndexOrThrow("carrera"))


                val map = HashMap<String, String>();
                map.put("idalumno", idalumno)
                map.put("fecha", fecha)
                map.put("nombres", nombres)
                map.put("apellidos", apellidos)
                map.put("edad", edad)
                map.put("correo", correo)
                map.put("carrera",carrera)
                arrayList.add(map)

            } while (cursor.moveToNext())

            dibujar(arrayList)
        }
    }

    @Composable private fun dibujar(arrayList: ArrayList<HashMap<String, String>>) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.size(20.dp))
            LazyColumn(
                content = {
                    items(
                        items = arrayList,
                        itemContent = {


                            Box (
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {

                                Column(
                                    modifier = Modifier
                                        .padding(all = dimensionResource(id = R.dimen.espacio2))
                                        .border(
                                            width = 1.dp,
                                            color = Color.Gray,
                                            shape = RectangleShape
                                        )
                                        .background(MaterialTheme.colorScheme.primary)
                                        .padding(all = dimensionResource(id = R.dimen.espacio))
                                        .height(200.dp)
                                        .width(220.dp)


                                ) {
                                    Text(text = it.get("idalumno").toString(),
                                        color = MaterialTheme.colorScheme.background,
                                        textAlign = TextAlign.Left,
                                        fontSize = 14.sp)
                                    Text(text = it.get("fecha").toString(),
                                        color = MaterialTheme.colorScheme.background,
                                        textAlign = TextAlign.Left,
                                        fontSize = 14.sp)
                                    Text(text = it.get("nombres").toString(),
                                        color = MaterialTheme.colorScheme.background,
                                        textAlign = TextAlign.Left,
                                        fontSize = 14.sp)
                                    Text(text = it.get("apellidos").toString(),
                                        color = MaterialTheme.colorScheme.background,
                                        textAlign = TextAlign.Left,
                                        fontSize = 14.sp)
                                    Text(text = it.get("edad").toString() + " años",
                                        color = MaterialTheme.colorScheme.background,
                                        textAlign = TextAlign.Left,
                                        fontSize = 14.sp)
                                    Text(text = it.get("correo").toString(),
                                        color = MaterialTheme.colorScheme.background,
                                        textAlign = TextAlign.Left,
                                        fontSize = 14.sp)
                                    Text(text = it.get("carrera").toString(),
                                        color = MaterialTheme.colorScheme.background,
                                        textAlign = TextAlign.Left,
                                        fontSize = 14.sp)
                                }
                            }

                        }
                    )//items
                }
            )//LazyColumn
        }

    }
}


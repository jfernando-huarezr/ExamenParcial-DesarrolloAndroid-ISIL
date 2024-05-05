package com.huarezreyes.eparcial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.huarezreyes.eparcial.ui.theme.EParcialTheme
import com.huarezreyes.eparcial.ui.theme.PrimaryColor2
import com.huarezreyes.eparcial.utils.Total
import org.json.JSONArray

@OptIn(ExperimentalMaterial3Api::class)
class ColaboradoresActivity : ComponentActivity() {
    val campos =
        arrayOf(
            "idempleado",
            "apellidos",
            "nombres",
            "cargo",
            "tratamiento",
            "fechanacimiento",
            "direccion",
            "ciudad",
            "usuario",
            "codigopostal",
            "pais",
            "telefono",
            "clave",
            "foto",
            "jefe"
        )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        leerServicio()

    }

    private fun leerServicio() {
        val queue = Volley.newRequestQueue(this)
        val url =  Total.rutaServicio + "empleados.php"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("EMP", response)
                llenarLista(response)
            },
            {
                Log.d("EMPERROR", it.message.toString())
            })
        queue.add(stringRequest)
    }

    private fun llenarLista(response: String?) {
        val jsonArray = JSONArray(response)
        val arrayList = ArrayList<HashMap<String, String>>()

        val auxArrayValues = ArrayList<String>()

        for (i in 0 until jsonArray.length()) {


            for (j in 0 until campos.size) {
                val valor = jsonArray.getJSONObject(i).getString(campos[j])

                if (valor == "null") {
                    auxArrayValues.add("No hay datos")
                } else {
                    auxArrayValues.add(valor)
                }
            }

            Log.d("PRUEBA", auxArrayValues.toString())

            val map = HashMap<String, String>()


            for (i in 0 until campos.size) {
                map.put(campos[i], auxArrayValues.get(i))
            }

            arrayList.add(map)
            auxArrayValues.clear()
        }

        dibujar(arrayList)
    }

    private fun dibujar(arrayList: ArrayList<HashMap<String, String>>) {
        setContent {
            EParcialTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = stringResource(id = R.string.title_activity_colaboradores),
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier
                                        .padding(30.dp),
                                    color = PrimaryColor2
                                )
                            },

                            colors = TopAppBarDefaults.smallTopAppBarColors(
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
                            .background(MaterialTheme.colorScheme.background)
                            .padding(it)
                            .padding(vertical = 10.dp, horizontal = 15.dp)
                    ) {
                        Column {
                            LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
                                items(arrayList.size) { posicion ->
                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.tertiary
                                        ),
                                        modifier = Modifier
                                            .padding(all = dimensionResource(id = R.dimen.espacio2))
                                            .height(250.dp)
                                            .shadow(
                                                elevation = dimensionResource(id = R.dimen.espacio2)
                                            )
                                            .clickable {
                                                seleccionarColaborador(arrayList[posicion])
                                            }
                                    ) {
                                        Column (
                                            modifier = Modifier
                                                .padding(all = dimensionResource(id = R.dimen.espacio1))
                                                .fillMaxWidth(),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ){

                                            Box {
                                                if(arrayList[posicion].get("foto").toString() == "null"){
                                                    Image(painter = painterResource(id = R.drawable.nofoto), contentDescription = null,
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .height(150.dp))
                                                }
                                                else {
                                                    AsyncImage(
                                                        model = Total.rutaServicio + "fotos/"+ arrayList[posicion].get(
                                                            "foto"
                                                        ).toString(),
                                                        contentDescription = null,
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .height(150.dp)
                                                    )
                                                }
                                            }

                                            Text(
                                                text = arrayList[posicion].get("nombres").toString() + " "+ arrayList[posicion].get("apellidos").toString(),
                                                textAlign = TextAlign.Center,
                                                fontSize = 14.sp
                                            )

                                            Text(
                                                text = arrayList[posicion].get("cargo").toString(),
                                                textAlign = TextAlign.Center,
                                                fontSize = 12.sp,
                                                color =
                                                if (arrayList[posicion].get("jefe").toString() == "2") {
                                                     Color.Red
                                                }

                                                else {
                                                    Color.White
                                                }
                                            )


                                        }
                                    }
                                }
                            })//LazyVerticalGrid
                        }
                    }

                    }

            }
        }
    }

    private fun seleccionarColaborador(colaborador: HashMap<String, String>) {

        val intent = Intent(this, DatosActivity::class.java  )
        intent.putExtra("colaborador", colaborador)
        startActivity(intent)
    }
}


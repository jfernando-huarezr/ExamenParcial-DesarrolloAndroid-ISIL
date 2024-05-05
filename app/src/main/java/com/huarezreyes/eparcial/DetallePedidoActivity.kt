package com.huarezreyes.eparcial

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.huarezreyes.eparcial.ui.theme.EParcialTheme
import com.huarezreyes.eparcial.utils.Total
import org.json.JSONArray

class DetallePedidoActivity : ComponentActivity() {

    val campos =
        arrayOf(
            "idpedido",
            "idproducto",
            "precio",
            "cantidad",
            "nombre",
            "detalle",
            "imagenchica"
        )
    var idpedido = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        Log.v("Pruebas", "${intent.getStringExtra("idpedido")}")
        idpedido = intent.getStringExtra("idpedido").toString()

        leerServicio(idpedido)
    }

    private fun leerServicio(idpedido: String?) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/pedidosdetalle.php?idpedido=" + idpedido

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("DATOS", response)
                llenarLista(response)
            },
            {
                Log.d("DATOSERROR", it.message.toString())
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

    @OptIn(ExperimentalMaterial3Api::class)
    private fun dibujar(arrayList: ArrayList<HashMap<String, String>>) {
        setContent {
            EParcialTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Detalle Pedido ${idpedido}",
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier
                                        .padding(30.dp),
                                    color = Color.White
                                )
                            },

                            colors = TopAppBarDefaults.smallTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            ),

                            navigationIcon = {
                                IconButton(onClick = {
                                    finish()
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = null
                                    )
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
                            LazyVerticalGrid(columns = GridCells.Fixed(3), content = {
                                items(arrayList.size) { posicion ->
                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.tertiary
                                        ),
                                        modifier = Modifier
                                            .padding(all = dimensionResource(id = R.dimen.espacio2))
                                            .height(270.dp)
                                            .shadow(
                                                elevation = dimensionResource(id = R.dimen.espacio2)
                                            )
                                    ) {
                                        Column (
                                            modifier = Modifier
                                                .padding(all = dimensionResource(id = R.dimen.espacio1))
                                                .fillMaxWidth(),
                                            horizontalAlignment = Alignment.Start
                                        ){

                                            Box {

                                                if (arrayList[posicion].get(campos[6]).toString() == "null") {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.nofoto),
                                                        contentDescription = null,
                                                    )
                                                } else {
                                                    AsyncImage(
                                                        model = "https://servicios.campus.pe/" + arrayList[posicion].get(campos[6]).toString(),
                                                        contentDescription = null,
                                                        modifier = Modifier.height(80.dp)
                                                    )
                                                }
                                            }
                                            Spacer(modifier = Modifier.height(8.dp))

                                            for (i in 2 until campos.size-1) {
                                                Text(
                                                    text =

                                                    if (i==2) {
                                                        val precio = arrayList[posicion].get(campos[i])?.toFloat()
                                                        val precioFormateado =  String.format("%.2f", precio)
                                                        "${campos[i]}: S/${precioFormateado}"
                                                    }

                                                    else {
                                                        "${campos[i]}: ${arrayList[posicion].get(campos[i]).toString()}"
                                                    },

                                                    modifier = Modifier.padding(vertical = 1.dp),
                                                    color = Color.White,
                                                    fontSize = 8.sp
                                                )
                                            }


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
}

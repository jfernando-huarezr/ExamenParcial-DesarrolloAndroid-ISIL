package com.huarezreyes.eparcial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.huarezreyes.eparcial.ui.theme.EParcialTheme
import org.json.JSONArray

class EnviosActivity : ComponentActivity() {

    val campos =
        arrayOf(
            "idempresaenvio",
            "nombre",
            "telefono",
            "latitud",
            "longitud"
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        leerServicio()

    }

    private fun leerServicio() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/envios.php"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("ENVIOS", response)
                llenarLista(response)
            },
            {
                Log.d("ENVIOSERROR", it.message.toString())
            })

        queue.add(stringRequest)
    }

    private fun llenarLista(response: String?) {
        val jsonArray = JSONArray(response)
        val arrayList = ArrayList<HashMap<String,String>>()
        val auxArrayValues = ArrayList<String>()

        for(i in 0 until jsonArray.length()) {


            for(j in 0 until campos.size) {
                val valor = jsonArray.getJSONObject(i).getString(campos[j])

                if(valor == "null") {
                    auxArrayValues.add("No hay datos")
                }
                else {
                    auxArrayValues.add(valor)
                }
            }

            Log.d("ENVIOSPRUEBA", auxArrayValues.toString())

            val map =  HashMap<String,String>()


            for(i in 0 until campos.size) {
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
                                    text = "Envios",
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
                    Column (
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .padding(it)
                            .padding(vertical = 10.dp, horizontal = 15.dp)
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            content ={
                                items(
                                    items = arrayList,
                                    itemContent = {envio ->

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

                                                    .height(130.dp)
                                                    .width(200.dp)
                                                    .clickable {
                                                        Log.i("Pruebas", "${envio}")
                                                        val intent = Intent(this@EnviosActivity, DetalleEnvioActivity::class.java)
                                                        intent.putExtra("latitud", envio["latitud"].toString())
                                                        intent.putExtra("longitud", envio["longitud"].toString())
                                                        intent.putExtra("nombre", envio["nombre"].toString())
                                                        intent.putExtra("telefono", envio["telefono"].toString())
                                                        intent.putExtra("idempresaenvio", envio["idempresaenvio"].toString())

                                                        startActivity(intent)
                                                    },


                                                ) {
                                                for (i in 0 until campos.size) {
                                                    Text(text = "${campos[i]}: ${envio[campos[i]].toString()}",
                                                        modifier = Modifier.padding(vertical = 5.dp),
                                                        color = Color.White,
                                                        fontSize =
                                                        if (i==0) {
                                                            16.sp
                                                        }
                                                        else {
                                                            12.sp
                                                        },
                                                        fontWeight =

                                                        if (i==0) {
                                                            FontWeight.Bold
                                                        }
                                                        else {
                                                            FontWeight.Normal
                                                        }

                                                    )
                                                }
                                            }
                                        }
                                    }
                                )
                            }
                        )
                    }
                }

            }

        }
    }

}


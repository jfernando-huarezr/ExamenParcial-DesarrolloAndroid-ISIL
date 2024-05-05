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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.huarezreyes.eparcial.ui.theme.EParcialTheme
import org.json.JSONArray

class DetalleEnvioActivity : ComponentActivity() {

    val campos =
        arrayOf(
            "idpedido",
            "idempleado",
            "fechapedido",
            "fechaentrega",
            "fechaenvio",
            "idempresaenvio",
            "cargo",
            "destinatario",
            "direcciondestinatario",
            "ciudaddestinatario",
            "regiondestinatario",
            "codigopostaldestinatario",
            "paisdestinatario",
            "idcliente",
            "empleado",
            "cliente",
            "contacto"
        )

    var idempresaenvio = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        idempresaenvio = intent.getStringExtra("idempresaenvio").toString()

        leerServicio(idempresaenvio)

    }

    private fun leerServicio(idempresaenvio: String?) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/pedidosenvio.php?idempresaenvio=" + idempresaenvio

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
                                    text = "Detalle Envio ${idempresaenvio}",
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
                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .padding(it)
                            .padding(vertical = 10.dp, horizontal = 2.dp)
                    ) {
                        LazyRow(
                            content = {
                                items(
                                    items = arrayList,
                                    itemContent = { detalleEnvio->
                                        Card(
                                            colors = CardDefaults.cardColors(
                                                containerColor = MaterialTheme.colorScheme.primary
                                            ),
                                            modifier = Modifier
                                                .padding(all = dimensionResource(id = R.dimen.espacio2))
                                                .fillParentMaxSize()
                                                .clickable {
                                                    Log.i("Pruebas", "${detalleEnvio}")
                                                    val intent = Intent(this@DetalleEnvioActivity, DetallePedidoActivity::class.java)
                                                    intent.putExtra("idpedido", detalleEnvio["idpedido"].toString())


                                                    startActivity(intent)
                                                },

                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .padding(50.dp)
                                                    .fillMaxSize(),
                                                horizontalAlignment = Alignment.Start
                                            ) {

                                                Spacer(modifier = Modifier.height(20.dp))

                                                for (i in 0 until campos.size-1) {
                                                    Text(
                                                        text ="${campos[i]}: ${detalleEnvio[campos[i]].toString()}",
                                                        modifier = Modifier.padding(vertical = 5.dp),
                                                        color = MaterialTheme.colorScheme.background,
                                                        textAlign = TextAlign.Left,
                                                        fontSize = 14.sp
                                                    )
                                                }
                                            }
                                        }
                                    })
                            })
                    }

                }

            }
        }
    }


}

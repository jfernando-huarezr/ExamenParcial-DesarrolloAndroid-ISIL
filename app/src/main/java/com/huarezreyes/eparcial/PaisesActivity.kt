package com.huarezreyes.eparcial

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Intents.Insert
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.huarezreyes.eparcial.ui.theme.ColorAF
import com.huarezreyes.eparcial.ui.theme.ColorAS
import com.huarezreyes.eparcial.ui.theme.ColorEU
import com.huarezreyes.eparcial.ui.theme.ColorNA
import com.huarezreyes.eparcial.ui.theme.ColorOC
import com.huarezreyes.eparcial.ui.theme.ColorSA
import com.huarezreyes.eparcial.ui.theme.EParcialTheme
import com.huarezreyes.eparcial.ui.theme.PrimaryColor2
import com.huarezreyes.eparcial.ui.theme.PrimaryColor3
import com.huarezreyes.eparcial.utils.Total
import org.json.JSONArray

@OptIn(ExperimentalMaterial3Api::class)
class PaisesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        leerServicio()

    }

    private fun leerServicio() {
        val queue = Volley.newRequestQueue(this)
        val url = Total.rutaServicio + "paises.php"
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
        for (i in 0 until jsonArray.length()) {
            val idpais = jsonArray.getJSONObject(i).getString("idpais")
            val codpais = jsonArray.getJSONObject(i).getString("codpais")
            val pais = jsonArray.getJSONObject(i).getString("pais")
            val capital = jsonArray.getJSONObject(i).getString("capital")
            val area = jsonArray.getJSONObject(i).getString("area")
            val poblacion = jsonArray.getJSONObject(i).getString("poblacion")
            val continente = jsonArray.getJSONObject(i).getString("continente")
            val map = HashMap<String, String>();
            map.put("idpais", idpais)
            map.put("codpais", codpais)
            map.put("pais", pais)
            map.put("capital", capital)
            map.put("area", area)
            map.put("poblacion", poblacion)
            map.put("continente", continente)
            arrayList.add(map)
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
                                    text = stringResource(id = R.string.title_activity_paises),
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
                        Column (
                            modifier = Modifier.padding(bottom = 80.dp)
                        ) {

                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                content = {
                                    items(items = arrayList, itemContent = { pais ->

                                        val continente = pais["continente"].toString()
                                        Row(
                                            modifier = Modifier
                                                .padding(
                                                    vertical = dimensionResource(id = R.dimen.espacio2)
                                                )
                                                .fillMaxWidth()
                                                .background(
                                                    when(continente) {
                                                        "EU" -> ColorEU
                                                        "NA" -> ColorNA
                                                        "AS" -> ColorAS
                                                        "AF" -> ColorAF
                                                        "SA" -> ColorSA
                                                        "OC" -> ColorOC
                                                        "AN" -> Color.White
                                                        else -> Color.Transparent
                                                    }
                                                )

                                        ) {
                                            Text(
                                                text = pais["idpais"].toString(),
                                                style = MaterialTheme.typography.headlineLarge,
                                                modifier = Modifier.width(70.dp),
                                                color = PrimaryColor2
                                            )
                                            Column ( modifier = Modifier.width(220.dp)) {
                                                Text(
                                                    text = pais["codpais"].toString(),
                                                    style = MaterialTheme.typography.titleMedium,
                                                    color = PrimaryColor3
                                                )
                                                Text(text = pais["pais"].toString(),
                                                    color = PrimaryColor3)
                                                Text(text = "Capital: "+pais["capital"].toString(),
                                                    color = PrimaryColor3)
                                                Text(text = "Area: "+pais["area"].toString()+" km2",
                                                    color = PrimaryColor3)
                                                Text(text = "Poblacion: "+pais["poblacion"].toString(),
                                                    color = PrimaryColor3)
                                                Text(text = "Continente: "+pais["continente"].toString(),
                                                    color = PrimaryColor3)
                                            }

//                                            IconButton(onClick = {
//                                                editarPais(pais)
//                                            }) {
//                                                Icon(Icons.Filled.Edit, contentDescription = null,
//                                                    tint = PrimaryColor2)
//                                            }
                                        }
                                    })//items
                                })//LazyColumn
                        }//Column
                        FloatingActionButton(
                            onClick = {
                                startActivity(Intent(this@PaisesActivity, InsertPaisActivity::class.java))
                            }, modifier = Modifier
                                .padding(all = 15.dp)
                                .align(Alignment.BottomEnd),
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = null)
                        }
                    }

                }
            }
        }
    }

//    private fun editarPais(pais: HashMap<String, String>) {
//        val bundle = Bundle().apply {
//            putString("iddirector", director["iddirector"].toString())
//            putString("nombres", director["nombres"].toString())
//            putString("peliculas", director["peliculas"].toString())
//        }
//        val intent = Intent(this, DirectoresUpdateActivity::class.java  )
//        intent.putExtras(bundle)
//        startActivity(intent)
//    }
}

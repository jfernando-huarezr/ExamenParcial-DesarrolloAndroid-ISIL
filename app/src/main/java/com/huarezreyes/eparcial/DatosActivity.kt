package com.huarezreyes.eparcial

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.huarezreyes.eparcial.ui.theme.EParcialTheme
import com.huarezreyes.eparcial.ui.theme.PrimaryColor2
import com.huarezreyes.eparcial.utils.Total


@OptIn(ExperimentalMaterial3Api::class)
class DatosActivity : ComponentActivity() {
    val campos =
        arrayOf(
            "nombres",
            "apellidos",
            "cargo",
            "ciudad",
            "pais",
            "direccion",
            "telefono",
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        val colaborador = intent.getSerializableExtra("colaborador") as HashMap<String, String>?
        Log.d("HASHMAP", colaborador.toString())

        setContent {
            EParcialTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                if (colaborador != null) {
                                    Text(
                                        text = "${colaborador.get("nombres").toString()} ${colaborador.get("apellidos").toString()}",
                                        style = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier
                                            .padding(30.dp),
                                        color = PrimaryColor2
                                    )
                                }
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
                            .fillMaxSize()
                            .padding(it)
                            .padding(vertical = 10.dp, horizontal = 15.dp)
                    ) {

                        Column (
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Box {
                                if (colaborador != null) {
                                    if(colaborador.get("foto").toString() == "null"){
                                        Image(painter = painterResource(id = R.drawable.nofoto), contentDescription = null,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(300.dp))
                                    } else {
                                        AsyncImage(
                                            model = Total.rutaServicio + "fotos/"+ colaborador.get("foto").toString(),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(300.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            for (i in 0 until campos.size) {
                                if (colaborador != null) {
                                    Text(text = "${campos[i]}: ${colaborador.get(campos[i])}",
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.primary)
                                }
                            }



                        }

                    }
                }

            }
        }
    }
}

package com.huarezreyes.eparcial

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.huarezreyes.eparcial.ui.theme.EParcialTheme
import com.huarezreyes.eparcial.ui.theme.PrimaryColor3

class MainActivity : ComponentActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        setContent {
            EParcialTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 25.dp, vertical = 80.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ){
                    Column (modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Evaluacion Final",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary)

                        Text(text = "Jose Fernando Huarez Reyes",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary)
                    }

                    Box(modifier = Modifier.fillMaxWidth().clip(CircleShape).background(color = PrimaryColor3),
                        contentAlignment = Alignment.Center) {
                        Image(painter = painterResource(id = R.drawable.home), contentDescription = "home",
                            modifier = Modifier.height(300.dp),
                            contentScale = ContentScale.Crop)
                    }

                    Box(modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center) {
                        Text(text = "We will Rock You...",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary)
                    }





                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally)
                    ){

                        Column {
                            Button(onClick = {
                                startActivity(Intent(this@MainActivity, ColaboradoresActivity::class.java))
                            },
                                shape = MaterialTheme.shapes.medium,
                                modifier = Modifier.width(150.dp)
                            ) {
                                Text(text = "Colaboradores")
                            }
                            Button(onClick = {
                                startActivity(Intent(this@MainActivity, PaisesActivity::class.java)) },
                                shape = MaterialTheme.shapes.medium,
                                modifier = Modifier.width(150.dp)
                            ) {
                                Text(text = "Paises")
                            }
                        }

                        Column {
                            Button(onClick = {
                                startActivity(Intent(this@MainActivity, EnviosActivity::class.java))
                            },
                                shape = MaterialTheme.shapes.medium,
                                modifier = Modifier.width(150.dp)
                            ) {
                                Text(text = "Envios")
                            }
                            Button(onClick = {
                                startActivity(Intent(this@MainActivity, AlumnosInsertActivity::class.java)) },
                                shape = MaterialTheme.shapes.medium,
                                modifier = Modifier.width(150.dp)
                            ) {
                                Text(text = "Alumnos")
                            }

                            Button(onClick = {
                                iniciarSesion.launch(googleSignInClient.signInIntent)
                           },
                                shape = MaterialTheme.shapes.medium,
                                modifier = Modifier.width(150.dp)
                            ) {
                                Text(text = "Iniciar Sesion")
                            }
                        }
                    }


                }
            }
        }
    }

    var iniciarSesion =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: androidx.activity.result.ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@MainActivity, EscritorioGmailActivity::class.java))
            }
        }
}

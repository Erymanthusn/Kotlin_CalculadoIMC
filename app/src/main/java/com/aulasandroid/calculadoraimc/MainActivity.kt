package com.aulasandroid.calculadoraimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.aulasandroid.calculadoraimc.ui.theme.CalculadoraIMCTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraIMCTheme {

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    IMCScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun IMCScreen(
    modifier: Modifier = Modifier
) {
    var peso by remember {
        mutableStateOf("")
    }
    var altura by remember {
        mutableStateOf("")
    }
    var imc by remember {
        mutableStateOf<Double?>(null)
    }
    var classificacao by remember {
        mutableStateOf("")
    }
    var corResultado by remember {
        mutableStateOf(Color(0xFF2FA66A))
    }

    fun calcularIMC() {
        val pesoValor = peso
            .replace(",", ".")
            .toDoubleOrNull()

        val alturaValor = altura
            .replace(",", ".")
            .toDoubleOrNull()

        if (
            pesoValor != null &&
            alturaValor != null &&
            pesoValor > 0 &&
            alturaValor > 0
        ) {
            val alturaMetros = alturaValor / 100
            val resultado = pesoValor / (alturaMetros * alturaMetros)

            imc = resultado

            when {

                resultado < 18.5 -> {
                    classificacao = "Abaixo do peso"
                    corResultado = Color(0xFFE53935)
                }
                resultado < 25 -> {
                    classificacao = "Peso ideal"
                    corResultado = Color(0xFF2FA66A)
                }
                resultado < 30 -> {
                    classificacao = "Levemente acima do peso"
                    corResultado = Color(0xFFFF9800)
                }

                resultado < 35 -> {
                    classificacao = "Obesidade grau I"
                    corResultado = Color(0xFFE53935)
                }

                resultado < 40 -> {
                    classificacao = "Obesidade grau II"
                    corResultado = Color(0xFFE53935)
                }

                else -> {
                    classificacao = "Obesidade grau III"
                    corResultado = Color(0xFFE53935)
                }
            }
        }
    }
    fun limparDados() {
        peso = ""
        altura = ""
        imc = null
        classificacao = ""
        corResultado = Color(0xFF2FA66A)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFF7FC))
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .background(
                    color = colorResource(id = R.color.cor_app)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.bmi),
                contentDescription = "Logo do aplicativo",
                modifier = Modifier
                    .size(80.dp)
                    .padding(top = 12.dp)
            )

            Text(
                text = "Calculadora IMC",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-45).dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF9F6F6)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 5.dp
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Seus dados",
                        fontSize = 24.sp,
                        color = colorResource(
                            id = R.color.cor_app
                        ),
                        fontWeight = FontWeight.Bold
                    )


                    Spacer(
                        modifier = Modifier.height(18.dp)
                    )

                    //PESO
                    OutlinedTextField(
                        value = peso,
                        onValueChange = {
                            peso = it
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = {
                            Text("Peso (kg)")
                        },
                        placeholder = {
                            Text("Ex.: 70")
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        )
                    )

                    Spacer(
                        modifier = Modifier.height(14.dp)
                    )

                    //ALTURA
                    OutlinedTextField(
                        value = altura,
                        onValueChange = {
                            altura = it
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = {
                            Text("Altura (cm)")
                        },
                        placeholder = {
                            Text("Ex.: 175")
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        )
                    )


                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    // BOTÕES
                    Row(
                        modifier = Modifier.fillMaxWidth(),

                        horizontalArrangement = Arrangement.spacedBy(
                            10.dp
                        )
                    ) {

                        Button(
                            onClick = {
                                calcularIMC()
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(
                                    id = R.color.cor_app
                                )
                            )
                        ) {
                            Text(
                                text = "CALCULAR",
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Button(
                            onClick = {
                                limparDados()
                            },

                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Gray
                            )
                        ) {

                            Text(
                                text = "LIMPAR",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
            if (imc != null) {

                Card(

                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-20).dp),
                    colors = CardDefaults.cardColors(
                        containerColor = corResultado
                    ),

                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 5.dp
                    )
                ) {

                    Column(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 20.dp,
                                vertical = 18.dp
                            ),

                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = String.format("%.1f", imc),
                            fontSize = 30.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(5.dp)
                        )

                        Text(
                            text = classificacao,
                            fontSize = 20.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
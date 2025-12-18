package com.example.calculadoraimc.feature.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoraimc.datasource.Calculations
import com.example.calculadoraimc.feature.home.model.HealthMetrics
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home() {
    var metrics by remember { mutableStateOf<HealthMetrics?>(null) }
    var showError by remember { mutableStateOf(false) }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var sex by remember { mutableStateOf("Masculino") }
    var activityLevel by remember { mutableStateOf("Sedentário") }
    var expandedSex by remember { mutableStateOf(false) }
    var expandedActivity by remember { mutableStateOf(false) }

    val activityLevels = listOf("Sedentário", "Pouco ativo", "Moderadamente ativo", "Muito ativo")
    val sexes = listOf("Masculino", "Feminino")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Calculadora de IMC",
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF2196F3)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            // Entrada de dados
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TextField(
                    value = height,
                    onValueChange = { height = it },
                    label = { Text("Altura (cm)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text("Peso (kg)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = age,
                    onValueChange = { age = it },
                    label = { Text("Idade") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(expanded = expandedSex, onExpandedChange = { expandedSex = !expandedSex }) {
                    TextField(
                        value = sex,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Sexo") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSex) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(expanded = expandedSex, onDismissRequest = { expandedSex = false }) {
                        sexes.forEach {
                            DropdownMenuItem(text = { Text(it) }, onClick = {
                                sex = it
                                expandedSex = false
                            })
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    expanded = expandedActivity,
                    onExpandedChange = { expandedActivity = !expandedActivity }
                ) {
                    TextField(
                        value = activityLevel,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Nível de Atividade") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedActivity) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(expanded = expandedActivity, onDismissRequest = { expandedActivity = false }) {
                        activityLevels.forEach {
                            DropdownMenuItem(text = { Text(it) }, onClick = {
                                activityLevel = it
                                expandedActivity = false
                            })
                        }
                    }
                }

                Button(
                    onClick = {
                        Calculations.calculateAllMetrics(
                            height = height,
                            weight = weight,
                            age = age,
                            sex = sex,
                            activityLevel = activityLevel,
                            response = { result ->
                                metrics = result
                                showError = false
                            },
                            onError = {
                                showError = true
                                metrics = null
                            }
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Calcular")
                }
            }

            Spacer(Modifier.height(32.dp))

            if (showError) {
                Text(
                    text = "Por favor, preencha todos os campos corretamente.",
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            metrics?.let {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "IMC: ${it.imcData.value} (${it.imcData.text})",
                        color = Color(0xFF2196F3),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Taxa Metabólica Basal: ${it.bmr}",
                        color = Color.DarkGray,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Peso Ideal (Devine): ${it.idealWeight}",
                        color = Color.DarkGray,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Necessidade Calórica Diária: ${it.dailyCaloricNeeds}",
                        color = Color.DarkGray,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    CalculadoraIMCTheme {
        Home()
    }
}

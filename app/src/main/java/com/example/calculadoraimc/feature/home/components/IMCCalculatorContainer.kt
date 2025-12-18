package com.example.calculadoraimc.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun IMCCalculatorContainer(
    onMetricsCalculated: (HealthMetrics) -> Unit,
    onInvalidInput: () -> Unit
) {

    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var sex by remember { mutableStateOf("Masculino") }
    var activityLevel by remember { mutableStateOf("Sedentário") }
    var expandedActivity by remember { mutableStateOf(false) }

    val activityLevels = listOf("Sedentário", "Pouco ativo", "Moderadamente ativo", "Muito ativo")


    Column(modifier = Modifier.fillMaxWidth()) {

        // Inputs de altura e peso
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // --- Input da altura ---
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Altura (cm)",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = height,
                    onValueChange = { height = it },
                    placeholder = { Text("Ex: 165") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            // --- Input do peso ---
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Peso (kg)",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    placeholder = { Text("Ex: 70,50") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // Inputs de idade e sexo
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // --- Input da idade ---
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Idade",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = age,
                    onValueChange = { age = it },
                    placeholder = { Text("Ex: 25") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            // --- Seleção de sexo ---
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Sexo",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = { sex = "Masculino" },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (sex == "Masculino") Color(0xFF2196F3) else Color.Transparent,
                            contentColor = if (sex == "Masculino") Color.White else Color(0xFF2196F3)
                        )
                    ) {
                        Text("M")
                    }
                    OutlinedButton(
                        onClick = { sex = "Feminino" },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (sex == "Feminino") Color(0xFF2196F3) else Color.Transparent,
                            contentColor = if (sex == "Feminino") Color.White else Color(0xFF2196F3)
                        )
                    ) {
                        Text("F")
                    }
                }
            }
        }
        
        Spacer(Modifier.height(16.dp))

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

        Spacer(Modifier.height(32.dp))

        // Botao
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = {
                Calculations.calculateAllMetrics(
                    height = height,
                    weight = weight,
                    age = age,
                    sex = sex,
                    activityLevel = activityLevel,
                    response = onMetricsCalculated,
                    onError = onInvalidInput
                )
            },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3),
            )
        ) {
            Text(text = "CALCULAR", fontSize = 18.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun IMCCalculatorContainerPreview() {
    CalculadoraIMCTheme {
        IMCCalculatorContainer(
            onMetricsCalculated = {},
            onInvalidInput = {}
        )
    }
}
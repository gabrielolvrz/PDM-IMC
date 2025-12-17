package com.example.calculadoraimc.datasource

import android.annotation.SuppressLint
import com.example.calculadoraimc.feature.home.model.IMCData

object Calculations {

    @SuppressLint("DefaultLocale")
    fun calculateIMC(height: String, weight: String, response: (IMCData) -> Unit) {
        if (height.isNotEmpty() && weight.isNotEmpty()) {
            val heightFormatted = height.replace(",", ".").toDoubleOrNull()
            val weightFormatted = weight.replace(",", ".").toDoubleOrNull()

            if (weightFormatted != null && heightFormatted != null && heightFormatted > 0) {
                val alturaEmMetros = heightFormatted / 100
                val imc = weightFormatted / (alturaEmMetros * alturaEmMetros)
                val imcFormate = String.format("%.2f", imc)

                val imcData = when {
                    imc < 18.5 -> IMCData(imcFormate, "Abaixo do peso")
                    imc < 25 -> IMCData(imcFormate, "Peso normal")
                    imc < 30 -> IMCData(imcFormate, "Sobrepeso")
                    imc < 35 -> IMCData(imcFormate, "Obesidade Grau I")
                    imc < 40 -> IMCData(imcFormate, "Obesidade Grau II")
                    else -> IMCData(imcFormate, "Obesidade Grau III")
                }

                response(imcData)
            } else {
                response(IMCData("null", "Valores inválidos"))
            }
        } else {
            response(IMCData("null", "Nenhum IMC Calculado"))
        }
    }
}
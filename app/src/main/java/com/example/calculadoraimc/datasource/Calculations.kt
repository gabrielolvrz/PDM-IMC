package com.example.calculadoraimc.datasource

import android.annotation.SuppressLint
import com.example.calculadoraimc.feature.home.model.HealthMetrics
import com.example.calculadoraimc.feature.home.model.IMCData

object Calculations {

    /**
     * Calcula o IMC, a Taxa Metabólica Basal (TMB), o Peso Ideal e a Necessidade Calórica Diária.
     */
    @SuppressLint("DefaultLocale")
    fun calculateAllMetrics(
        height: String,
        weight: String,
        age: String,
        sex: String,
        activityLevel: String,
        response: (HealthMetrics) -> Unit,
        onError: () -> Unit
    ) {
        val heightCm = height.replace(",", ".").toDoubleOrNull()
        val weightKg = weight.replace(",", ".").toDoubleOrNull()
        val ageYears = age.toIntOrNull()

        // Validação para campos nulos e valores não positivos
        if (heightCm == null || weightKg == null || ageYears == null || heightCm <= 0 || weightKg <= 0 || ageYears <= 0) {
            onError()
            return
        }

        // --- Cálculo do IMC ---
        val imcValue = weightKg / ((heightCm / 100) * (heightCm / 100))
        val imcFormate = String.format("%.2f", imcValue)
        val imcData = when {
            imcValue < 18.5 -> IMCData(imcFormate, "Abaixo do peso")
            imcValue < 25 -> IMCData(imcFormate, "Peso normal")
            imcValue < 30 -> IMCData(imcFormate, "Sobrepeso")
            imcValue < 35 -> IMCData(imcFormate, "Obesidade Grau I")
            imcValue < 40 -> IMCData(imcFormate, "Obesidade Grau II")
            else -> IMCData(imcFormate, "Obesidade Grau III")
        }

        // --- Cálculo da TMB (Fórmula de Mifflin-St Jeor) ---
        val bmr = if (sex == "Masculino") {
            (10 * weightKg) + (6.25 * heightCm) - (5 * ageYears) + 5
        } else { // Feminino
            (10 * weightKg) + (6.25 * heightCm) - (5 * ageYears) - 161
        }
        val bmrFormatted = String.format("%.2f kcal", bmr)

        // --- Cálculo do Peso Ideal (Fórmula de Devine) ---
        val idealWeight = if (sex == "Masculino") {
            50 + 2.3 * ((heightCm / 2.54) - 60)
        } else { // Feminino
            45.5 + 2.3 * ((heightCm / 2.54) - 60)
        }
        val idealWeightFormatted = String.format("%.2f kg", idealWeight)

        // --- Cálculo da Necessidade Calórica Diária ---
        val activityFactor = when (activityLevel) {
            "Pouco ativo" -> 1.375
            "Moderadamente ativo" -> 1.55
            "Muito ativo" -> 1.725
            else -> 1.2 // Sedentário
        }
        val dailyCaloricNeeds = bmr * activityFactor
        val dailyCaloricNeedsFormatted = String.format("%.2f kcal", dailyCaloricNeeds)


        response(HealthMetrics(imcData, bmrFormatted, idealWeightFormatted, dailyCaloricNeedsFormatted))
    }
}
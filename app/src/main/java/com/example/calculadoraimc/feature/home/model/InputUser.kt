package com.example.calculadoraimc.feature.home.model

/**
 * Data class para agrupar todos os resultados dos cálculos de saúde.
 */
data class HealthMetrics(
    val imcData: IMCData,
    val bmr: String, // Taxa Metabólica Basal
    val idealWeight: String, // Peso Ideal
    val dailyCaloricNeeds: String // Necessidade Calórica Diária
)

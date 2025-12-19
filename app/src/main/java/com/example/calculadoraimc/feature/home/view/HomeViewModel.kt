package com.example.calculadoraimc.feature.home.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.calculadoraimc.datasource.Calculations
import com.example.calculadoraimc.feature.home.model.HealthMetrics

class HomeViewModel : ViewModel() {

    // Estado da UI (o que a tela vai mostrar)
    var height by mutableStateOf("")
    var weight by mutableStateOf("")
    var age by mutableStateOf("")
    var sex by mutableStateOf("Masculino")
    var activityLevel by mutableStateOf("Sedentário")

    var metrics by mutableStateOf<HealthMetrics?>(null)
    var showError by mutableStateOf(false)

    // Eventos da UI (o que o usuário faz)
    fun onHeightChange(newHeight: String) {
        height = newHeight
    }

    fun onWeightChange(newWeight: String) {
        weight = newWeight
    }

    fun onAgeChange(newAge: String) {
        age = newAge
    }

    fun onSexChange(newSex: String) {
        sex = newSex
    }

    fun onActivityLevelChange(newLevel: String) {
        activityLevel = newLevel
    }

    fun calculate() {
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
    }
}
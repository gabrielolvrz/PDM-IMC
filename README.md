# Calculadora de IMC e Saúde

Este é um aplicativo Android simples, desenvolvido como um projeto de faculdade, que calcula o Índice de Massa Corporal (IMC) e outras métricas de saúde.

## Funcionalidades

- **Cálculo de IMC:** Calcula o IMC com base no peso e na altura e exibe a classificação (ex: Abaixo do peso, Peso normal, Sobrepeso, etc.).
- **Taxa Metabólica Basal (TMB):** Calcula a TMB utilizando a fórmula de Mifflin-St Jeor, considerando peso, altura, idade e sexo.
- **Peso Ideal:** Estima o peso ideal com base na altura e sexo, utilizando a fórmula de Devine.
- **Necessidade Calórica Diária:** Calcula a quantidade de calorias diárias necessárias com base na TMB e no nível de atividade física selecionado pelo usuário.

## Formatação e Fórmulas

As fórmulas utilizadas estão centralizadas no arquivo `Calculations.kt`.

- **IMC:** `peso (kg) / (altura (m) * altura (m))`
- **TMB (Mifflin-St Jeor):**
  - Homem: `(10 * peso) + (6.25 * altura) - (5 * idade) + 5`
  - Mulher: `(10 * peso) + (6.25 * altura) - (5 * idade) - 161`
- **Peso Ideal (Devine):**
  - Homem: `50 + 2.3 * ((altura em cm / 2.54) - 60)`
  - Mulher: `45.5 + 2.3 * ((altura em cm / 2.54) - 60)`
- **Necessidade Calórica:** `TMB * Fator de Atividade` (O fator varia de 1.2 para sedentário a 1.725 para muito ativo).

## Arquitetura

O projeto utiliza a arquitetura **MVVM (Model-View-ViewModel)** de forma simples:

- **View (Tela):** A tela `Home.kt`, construída com Jetpack Compose, é responsável apenas por exibir os campos e os resultados.
- **ViewModel:** A classe `HomeViewModel.kt` gerencia o estado da tela (os valores dos campos de texto) e lida com as ações do usuário, chamando a camada de lógica.
- **Model:** Representado pelas classes de dados (`HealthMetrics`, `IMCData`) e pela lógica de negócio (`Calculations.kt`), que realiza todos os cálculos.

## Tecnologias Utilizadas

- Kotlin
- Jetpack Compose
- Android ViewModel (para a arquitetura MVVM)

## Como Executar

1. Clone este repositório.
2. Abra o projeto no Android Studio.
3. Sincronize o projeto com os arquivos Gradle.
4. Execute o aplicativo em um emulador ou dispositivo Android.

## Melhorias Futuras

- **Sistema de Histórico:** A principal melhoria seria implementar a persistência de dados com Room para salvar os resultados dos cálculos e criar uma tela para listar o histórico.
- **Navegação:** Adicionar um sistema de navegação (Jetpack Navigation) para permitir a troca entre a tela da calculadora e a futura tela de histórico.
- **Validação Melhorada:** Aprimorar a validação das entradas para aceitar apenas valores dentro de faixas realistas (ex: altura entre 50cm e 250cm).

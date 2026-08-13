package com.example.weather.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

data class Gradient(
    val primaryGradient: Brush,
    val secondaryGradient: Brush,
    val shadowColor: Color
) {
    constructor(
        firstColor: Color,
        secondColor: Color,
        thirdColor: Color,
        fourthColor: Color,
        cardAlpha: Float = 0.8f
    ) : this(
        primaryGradient = Brush.linearGradient(
            listOf(
                firstColor.copy(alpha = firstColor.alpha * cardAlpha),
                secondColor.copy(alpha = secondColor.alpha * cardAlpha)
            )
        ),


        secondaryGradient = Brush.linearGradient(
            listOf(
                thirdColor.copy(alpha = thirdColor.alpha * cardAlpha),
                fourthColor.copy(alpha = fourthColor.alpha * cardAlpha)
            )
        ),

        shadowColor = firstColor.copy(alpha = firstColor.alpha * cardAlpha * 0.6f)
    )
}


object CardGradients {
    val gradients = listOf(

        Gradient(
            firstColor = Color(0xFFFFD1B3),
            secondColor = Color(0xFFFFB4A2),
            thirdColor = Color(0xFFFFE5D4),
            fourthColor = Color(0xFFFFD1C0),
        ),


        Gradient(
            firstColor = Color(0xFFF9C5D1),
            secondColor = Color(0xFFF4A7B9),
            thirdColor = Color(0xFFFCE0E7),
            fourthColor = Color(0xFFF9C5D1),
        ),


        Gradient(
            firstColor = Color(0xFFD5C6F0),
            secondColor = Color(0xFFBCA7E6),
            thirdColor = Color(0xFFEBE4F9),
            fourthColor = Color(0xFFD5C6F0),
        ),


        Gradient(
            firstColor = Color(0xFFBEE7DC),
            secondColor = Color(0xFF98D6C6),
            thirdColor = Color(0xFFE0F5EE),
            fourthColor = Color(0xFFBEE7DC),
        ),


        Gradient(
            firstColor = Color(0xFFF5D7B5),
            secondColor = Color(0xFFEBC193),
            thirdColor = Color(0xFFFAEBD7),
            fourthColor = Color(0xFFF5D7B5),
        )
    )
}
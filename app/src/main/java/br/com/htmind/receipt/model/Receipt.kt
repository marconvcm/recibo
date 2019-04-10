package br.com.htmind.receipt.model

import java.util.*

data class Receipt (
    val recipient: String,
    val refers: List<String>,
    val date: Calendar,
    val amount: Float
)
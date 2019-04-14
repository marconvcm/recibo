package br.com.htmind.receipt.model

import java.util.*

data class Receipt (
    val payer: String,
    val refers: String,
    val date: Calendar,
    val amount: Float
)
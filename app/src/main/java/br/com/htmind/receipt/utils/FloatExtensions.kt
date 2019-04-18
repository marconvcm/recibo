package br.com.htmind.receipt.utils

import java.text.NumberFormat
import java.util.*

fun Float.currency(): String {
    return "R$${this}0"
}
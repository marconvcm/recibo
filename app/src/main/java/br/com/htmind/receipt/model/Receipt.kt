package br.com.htmind.receipt.model

import br.com.htmind.receipt.utils.md5
import java.util.*

data class Receipt (
    val payer: String,
    val refers: String,
    val date: Calendar,
    val amount: Float
) {

    fun authHash(): String {
        return "${payer}-${refers}-${date}-${amount}".md5()
    }
}
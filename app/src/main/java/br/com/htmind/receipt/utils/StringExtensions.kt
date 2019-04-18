package br.com.htmind.receipt.utils

import java.math.BigInteger
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

val simpleDateFormat = SimpleDateFormat("dd/MM/YYYY", Locale("pt", "br"))

fun Calendar.formated(): String {
    return simpleDateFormat.format(this.time)
}
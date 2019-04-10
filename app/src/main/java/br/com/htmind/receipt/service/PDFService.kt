package br.com.htmind.receipt.service

import br.com.htmind.receipt.model.Receipt
import io.reactivex.Observable

interface PDFService {

    fun generate(receipt: Receipt): Observable<String>

    fun list(): Observable<List<String>>
}
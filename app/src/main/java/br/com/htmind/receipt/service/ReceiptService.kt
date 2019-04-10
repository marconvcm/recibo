package br.com.htmind.receipt.service

import br.com.htmind.receipt.model.Receipt
import io.reactivex.Observable

interface ReceiptService {

    fun save(receipt: Receipt): Observable<String>

    fun send(receipt: Receipt): Observable<String>
}
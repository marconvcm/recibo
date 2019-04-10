package br.com.htmind.receipt.service

import br.com.htmind.receipt.model.Receipt
import io.reactivex.Observable

class PDFServiceImpl : PDFService {
    override fun generate(receipt: Receipt): Observable<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun list(): Observable<List<String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
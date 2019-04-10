package br.com.htmind.receipt.service

import br.com.htmind.receipt.model.Receipt
import io.reactivex.Observable

class ReceiptServiceImpl : ReceiptService {

    override fun save(receipt: Receipt): Observable<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun send(receipt: Receipt): Observable<String> {
        return Observable.fromArray("this is a test!")
    }
}
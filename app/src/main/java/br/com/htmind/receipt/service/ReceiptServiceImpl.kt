package br.com.htmind.receipt.service

import br.com.htmind.receipt.model.Receipt
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ReceiptServiceImpl (val pdfService: PDFService) : ReceiptService {

    override fun save(receipt: Receipt): Observable<String> {
        return pdfService
            .generate(receipt)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun send(receipt: Receipt): Observable<String> {
        return Observable.fromArray("this is a test!");
    }
}
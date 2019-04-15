package br.com.htmind.receipt.service

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import br.com.htmind.receipt.model.Receipt
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import java.util.concurrent.TimeUnit

class ReceiptServiceImpl(val context: Context, val pdfService: PDFService) : ReceiptService {

    override fun save(receipt: Receipt): Observable<String> =
        pdfService.generate(receipt)

    override fun send(receipt: Receipt): Observable<String> =
        checkWritePermission()
            .flatMap { mapper ->  save(receipt) }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())

    private fun checkWritePermission(): Observable<Boolean> = Observable.create { emitter ->
        when(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            PackageManager.PERMISSION_GRANTED -> {
                emitter.onNext(true)
                emitter.onComplete()
            }
            else -> emitter.onError(Exception("WRITE_EXTERNAL_STORAGE_NO_GRANTED"))
        }
    }
}
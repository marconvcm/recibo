package br.com.htmind.receipt.service

import android.content.Context
import br.com.htmind.receipt.model.Receipt
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import io.reactivex.Observable
import java.io.File
import java.io.FileOutputStream

class PDFServiceImpl(val context: Context) : PDFService {

    val filePath by lazy { File(context.filesDir, "myfile.pdf") }

    override fun generate(receipt: Receipt): Observable<String> = Observable.create { emitter ->
        val document = Document()
        PdfWriter.getInstance(document, FileOutputStream(filePath))
        document.open()
        document.add(Paragraph("Hello World!"))
        document.close()
        emitter.onNext(filePath.absolutePath)
        emitter.onComplete()
    }

    override fun list(): Observable<List<String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
package br.com.htmind.receipt.service

import android.content.Context
import android.os.Environment
import br.com.htmind.receipt.model.Receipt
import br.com.htmind.receipt.utils.currency
import br.com.htmind.receipt.utils.formated
import com.itextpdf.text.Chunk
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import io.reactivex.Observable
import java.io.File
import java.io.FileOutputStream
import java.util.*

class PDFServiceImpl(val context: Context) : PDFService {

    val filePath by lazy { File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "receipt.pdf") }

    override fun generate(receipt: Receipt): Observable<String> = Observable.create { emitter ->
        val document = Document()
        val today = Calendar.getInstance()
        PdfWriter.getInstance(document, FileOutputStream(filePath))
        document.open()
        document.addTitle("Recibo")
        document.add(Paragraph("Recibo").apply {
            font.size = 18f
            alignment = Element.ALIGN_CENTER
        })
        document.add(Chunk.NEWLINE)
        document.add(Paragraph("Recebido da ${receipt.payer} em ${receipt.date.formated()}, a importância de ${ receipt.amount.currency() } referente à ${receipt.refers}"))
        document.add(Chunk.NEWLINE)

        document.add(Paragraph("Caxias do Sul, ${today.formated()}").apply {
            add(Chunk.NEWLINE)
            add(Chunk("Síndico"))
            add(Chunk.NEWLINE)
            add(Chunk("AUTENTICAÇÃO MECANICA: ${receipt.authHash()}"))
        })
        document.close()
        emitter.onNext(filePath.absolutePath)
        emitter.onComplete()
    }

    override fun list(): Observable<List<String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
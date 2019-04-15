package br.com.htmind.receipt.vm

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.htmind.receipt.model.Receipt
import br.com.htmind.receipt.service.ReceiptService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import java.util.*

class ReceiptViewModel(val receiptService: ReceiptService): ViewModel() {

    private val disposable = CompositeDisposable()

    var payer = MutableLiveData<String>()
    var amount = MutableLiveData<String>()
    var refers = MutableLiveData<String>()
    var loading = MutableLiveData<Boolean>()

    var hasWritePermission = MutableLiveData<Boolean>()
    var pdfFilePath = MutableLiveData<String?>()

    init {
        loading.postValue(false)
    }

    fun reset() {
        payer.postValue("")
        amount.postValue("")
        refers.postValue("")
        loading.postValue(false)
        pdfFilePath.postValue(null)
    }

    fun submit() {
        val receipt = buildReceipt()
        loading.postValue(true)
        disposable.add(receiptService.save(receipt).subscribe({ handleSendResult(it) }, { handleSendError(it) }))
    }

    private fun handleSendResult(filePath: String) {
        pdfFilePath.postValue(filePath)
    }

    private fun handleSendError(error: Throwable) {
        when(error.message) {
            "WRITE_EXTERNAL_STORAGE_NO_GRANTED" -> hasWritePermission.postValue(false)
            else -> throw error
        }
    }

    private fun buildReceipt(): Receipt = Receipt(
        payer = payer.value ?: "",
        amount = amount.value?.toFloatOrNull() ?: 0f,
        date = Calendar.getInstance(),
        refers = refers.value ?: ""
    )

    override fun onCleared() {
        disposable.dispose()
    }
}
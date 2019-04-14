package br.com.htmind.receipt.vm

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.htmind.receipt.model.Receipt
import br.com.htmind.receipt.service.ReceiptService
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class ReceiptViewModel(val receiptService: ReceiptService): ViewModel() {

    private val disposable = CompositeDisposable()

    var payer = MutableLiveData<String>()
    var amount = MutableLiveData<String>()
    var refers = MutableLiveData<String>()
    var loading = MutableLiveData<Boolean>()

    init {
        loading.postValue(false)
    }

    fun reset() {
        payer.postValue("")
        amount.postValue("")
        refers.postValue("")
        loading.postValue(false)
    }

    fun submit() {
        val receipt = buildReceipt()
        loading.postValue(true)
        receiptService.save(receipt).subscribe { filePath ->
            Log.d("MARCOS", filePath)
            reset();
        }
    }

    private fun buildReceipt(): Receipt = Receipt(
        payer = payer.value ?: "",
        amount = amount.value?.toFloatOrNull() ?: 0f,
        date = Calendar.getInstance(),
        refers = refers.value ?: ""
    )

    override fun onCleared() {
        disposable.clear()
    }
}
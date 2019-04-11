package br.com.htmind.receipt.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReceiptViewModel: ViewModel() {

    var payer = MutableLiveData<String>()
    var amount = MutableLiveData<String>()
    var refers = MutableLiveData<String>()

    fun say() {
        Log.d("MARCOS", amount.value);
    }
}
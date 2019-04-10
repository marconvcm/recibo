package br.com.htmind.receipt

import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.com.htmind.receipt.vm.ReceiptViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val receiptViewModel by viewModel<ReceiptViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}

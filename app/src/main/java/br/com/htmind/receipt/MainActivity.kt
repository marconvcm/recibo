package br.com.htmind.receipt

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import br.com.htmind.receipt.databinding.ActivityMainBinding

import br.com.htmind.receipt.vm.ReceiptViewModel
import kotlinx.android.synthetic.main.activity_main.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val payerEntries by lazy { resources.getStringArray(R.array.casas) }

    val model by viewModel<ReceiptViewModel>()

    var dialog: DialogInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        title = getString(br.com.htmind.receipt.R.string.recibo_activity_title)
        binding.model = model
        binding.lifecycleOwner = this
        binding.editTextPayer.setOnClickListener { view ->
            val editText = view as EditText
            AlertDialog.Builder(this@MainActivity).apply {
                setTitle(editText.hint)
                setItems(payerEntries) { dialog, which ->
                    model.payer.postValue(payerEntries[which])
                }
                setNegativeButton("Cancel") { dialog, which -> }
            }.apply {
                create()
                show()
            }
        }

        model.loading.observe(this, Observer { value ->
            Log.d("MARCOS", "loading: $value")
        })
    }
}

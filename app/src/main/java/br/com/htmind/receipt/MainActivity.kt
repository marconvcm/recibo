package br.com.htmind.receipt

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import br.com.htmind.receipt.databinding.ActivityMainBinding
import br.com.htmind.receipt.vm.ReceiptViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.ContextCompat
import java.io.File
import android.os.StrictMode

class MainActivity : AppCompatActivity() {

    val payerEntries by lazy { resources.getStringArray(R.array.casas) }

    val model by viewModel<ReceiptViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().build())
        if(hasPermissions()) {
            bootstrap()
        }
    }

    fun bootstrap() {
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        title = getString(br.com.htmind.receipt.R.string.recibo_activity_title)
        binding.model = model
        binding.lifecycleOwner = this
        binding.editTextPayer.setOnClickListener { view ->
            val editText = view as EditText
            AlertDialog.Builder(this@MainActivity).apply {
                setTitle(editText.hint)
                setItems(payerEntries) { _ , which ->
                    model.payer.postValue(payerEntries[which])
                }
                setNegativeButton("Cancel") { _, _ -> }
            }.apply {
                create()
                show()
            }
        }
        model.loading.observe(this, Observer { value ->
            Log.d("MARCOS", "loading: $value")
        })
        model.pdfFilePath.observe(this, Observer { value ->
            when(value) {
                is String -> {
                    shareFileOnWhatsApp(value)
                }
            }
        })
    }

    fun hasPermissions(): Boolean {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 100)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults.contains(PackageManager.PERMISSION_GRANTED) && requestCode.equals(100)) {
            bootstrap()
        }
    }

    private fun shareFileOnWhatsApp(filePath: String) {
        val share = Intent()
        share.action = Intent.ACTION_SEND
        share.type = "application/pdf"
        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(File(filePath)))
        share.setPackage("com.whatsapp")
        startActivity(share)
    }
}

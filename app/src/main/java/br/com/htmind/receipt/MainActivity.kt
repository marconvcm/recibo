package br.com.htmind.receipt

import android.Manifest
import android.app.Activity
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
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import br.com.htmind.receipt.databinding.ActivityMainBinding

import br.com.htmind.receipt.vm.ReceiptViewModel
import kotlinx.android.synthetic.main.activity_main.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.os.Environment.*
import java.io.File


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

        model.hasWritePermission.observe(this, Observer { value ->
            if(!value) {
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 100)
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        var grant = grantResults
        grant += requestCode
        when(grant) {
            intArrayOf(PermissionChecker.PERMISSION_GRANTED, 100) -> {
                model.submit()
            }
        }
    }

    private fun shareFileOnWhatsApp(filePath: String) {
        val outputFile = File(filePath)
        val uri = Uri.fromFile(outputFile)
        val share = Intent()
        share.action = Intent.ACTION_SEND
        share.type = "application/pdf"
        share.putExtra(Intent.EXTRA_STREAM, uri)
        share.setPackage("com.whatsapp")
        startActivity(share)
    }
}

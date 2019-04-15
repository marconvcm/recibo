package br.com.htmind.receipt.di

import br.com.htmind.receipt.service.PDFService
import br.com.htmind.receipt.service.PDFServiceImpl
import br.com.htmind.receipt.service.ReceiptService
import br.com.htmind.receipt.service.ReceiptServiceImpl
import br.com.htmind.receipt.vm.ReceiptViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val receiptModule = module {

    single { PDFServiceImpl(get()) as PDFService }
    single { ReceiptServiceImpl(get(), get()) as ReceiptService }

    viewModel { ReceiptViewModel(get()) }
}
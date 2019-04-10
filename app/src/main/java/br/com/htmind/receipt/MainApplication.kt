package br.com.htmind.receipt

import android.app.Application
import br.com.htmind.receipt.di.receiptModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {

    override fun onCreate(){
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(receiptModule)
        }
    }
}
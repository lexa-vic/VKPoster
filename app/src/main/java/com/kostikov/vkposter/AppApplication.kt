package com.kostikov.vkposter

import android.app.Application
import com.kostikov.vkposter.savedata.FileManager
import com.kostikov.vkposter.savedata.FileSaveService

/**
 * @author Kostikov Aleksey.
 */
class AppApplication: Application() {

    private lateinit var fileSaveService: FileSaveService

    override fun onCreate() {
        super.onCreate()

        fileSaveService = FileManager()
    }

    fun getFileSaveService(): FileSaveService = fileSaveService
}
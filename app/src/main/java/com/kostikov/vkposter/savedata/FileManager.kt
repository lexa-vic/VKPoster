package com.kostikov.vkposter.savedata

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import io.reactivex.Completable
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*


/**
 * @author Kostikov Aleksey.
 */

interface FileSaveService {
    fun storePost(bitmap: Bitmap): Completable
}

class FileManager(private val context: Context): FileSaveService {
    private val FOLDER_NAME = "VkPosts"

    override fun storePost(bitmap: Bitmap): Completable = Completable.fromAction {
        val mediaStorageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), FOLDER_NAME
        )
        // Create a storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(FOLDER_NAME, "Failed to create directory")
            }
        }

        // Create a media file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageName = "VkPost_$timeStamp.jpg"

        val selectedOutputPath = mediaStorageDir.getPath() + File.separator + imageName
        Log.d(FOLDER_NAME, "selected camera path $selectedOutputPath")

        var fOut: OutputStream? = null
        try {
            val file = File(selectedOutputPath)
            fOut = FileOutputStream(file)

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
            fOut!!.flush()
            fOut!!.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
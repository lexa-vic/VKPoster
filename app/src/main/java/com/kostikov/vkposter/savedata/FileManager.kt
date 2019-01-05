package com.kostikov.vkposter.savedata

import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import com.kostikov.vkposter.savedata.FileSaveService.Companion.FOLDER_NAME
import io.reactivex.Completable
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


/**
 * @author Kostikov Aleksey.
 */

interface FileSaveService {
    companion object {
        val FOLDER_NAME = "VkPosts"
    }

    fun storePost(bitmap: Bitmap, imageName: String): Completable
}

class FileManager: FileSaveService {


    override fun storePost(bitmap: Bitmap, imageName: String): Completable = Completable.fromAction {
        val mediaStorageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), FOLDER_NAME
        )
        // Create a storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(FOLDER_NAME, "Failed to create directory")
            }
        }

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
package com.marina.surfgallery.core.data.local.file

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.IOException


class SavePictureInFile(
    private val context: Application
) : SavePictureInStorage {

    override suspend fun savePicture(pic: InternalStoragePicture) {
        try {
            context.openFileOutput(pic.name, MODE_PRIVATE).use { stream ->
                if (!pic.bmp.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw IOException("Couldn't save bitmap.")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override suspend fun deletePicture(name: String) {
        Log.d("SavePictureInFile", "name: $name")
        try {
            context.deleteFile(name)
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
    }

    override suspend fun loadPicture(name: String): InternalStoragePicture {
        val files = context.filesDir.listFiles()
        files?.first { it.canRead() && it.isFile && it.name.equals(name) }?.apply {
            val bytes = this.readBytes()
            val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            return InternalStoragePicture(this.name, bmp)
        }
        throw RuntimeException("File $name not found")
    }

    override suspend fun loadAllPictures(): List<InternalStoragePicture> {
        val files = context.filesDir.listFiles()
        return files?.filter { it.canRead() && it.isFile && it.name.endsWith(".jpg") }?.map {
            val bytes = it.readBytes()
            val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            InternalStoragePicture(it.name, bmp)
        } ?: listOf()
    }

    override suspend fun deleteAllPictures() {
        val allPics = loadAllPictures()
        for (pic in allPics) {
            deletePicture(pic.name)
        }
    }
}
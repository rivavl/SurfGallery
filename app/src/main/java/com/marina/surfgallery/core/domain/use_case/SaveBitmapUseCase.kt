package com.marina.surfgallery.core.domain.use_case

import android.graphics.Bitmap
import com.marina.surfgallery.core.domain.repository.PictureRepository

class SaveBitmapUseCase(
    private val repository: PictureRepository
) {

    suspend operator fun invoke(bitmap: Bitmap, name: String) {
        repository.saveBitmap(bitmap, name)
    }
}
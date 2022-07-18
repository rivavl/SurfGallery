package com.marina.surfgallery.core.domain.use_case

import com.marina.surfgallery.core.domain.entity.Picture
import com.marina.surfgallery.core.domain.repository.PictureRepository

class SavePictureInfoUseCase(
    private val repository: PictureRepository
) {

    suspend operator fun invoke(picture: Picture) {
        repository.savePicture(picture)
    }
}
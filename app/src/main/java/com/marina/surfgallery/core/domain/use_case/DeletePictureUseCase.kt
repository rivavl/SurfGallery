package com.marina.surfgallery.core.domain.use_case

import com.marina.surfgallery.core.domain.repository.PictureRepository

class DeletePictureUseCase(
    private val repository: PictureRepository
) {
    suspend operator fun invoke(id: String, name: String) {
        repository.deletePicture(id, name)
    }
}
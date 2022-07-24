package com.marina.surfgallery.core.domain.use_case

import com.marina.surfgallery.core.domain.repository.PictureRepository
import javax.inject.Inject

class DeletePictureUseCase @Inject constructor(
    private val repository: PictureRepository
) {
    suspend operator fun invoke(id: String, name: String) {
        repository.deletePicture(id, name)
    }
}
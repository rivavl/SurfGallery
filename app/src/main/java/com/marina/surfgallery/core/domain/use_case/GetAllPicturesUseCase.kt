package com.marina.surfgallery.core.domain.use_case

import com.marina.surfgallery.common.Resource
import com.marina.surfgallery.core.domain.entity.Picture
import com.marina.surfgallery.core.domain.repository.PictureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GetAllPicturesUseCase(
    private val repository: PictureRepository
) {

    suspend operator fun invoke(): Flow<Resource<List<Picture>>> = flow {
        try {
            emit(Resource.Loading())
            val pictures = repository.getPictures()
            emit(Resource.Success(pictures.data!!))
        } catch (e: IOException) {
            emit(Resource.Error("Отсутствует интернет соединение\n Попробуйте позже"))
        }
    }
}
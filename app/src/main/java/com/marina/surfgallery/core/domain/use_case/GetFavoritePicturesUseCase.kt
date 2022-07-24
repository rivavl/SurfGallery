package com.marina.surfgallery.core.domain.use_case

import com.marina.surfgallery.common.entity.Resource
import com.marina.surfgallery.core.domain.entity.Picture
import com.marina.surfgallery.core.domain.repository.PictureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetFavoritePicturesUseCase @Inject constructor(
    private val repository: PictureRepository
) {

    suspend operator fun invoke(): Flow<Resource<List<Picture>>> = flow {
        try {
            emit(Resource.Loading())
            val pictures = repository.getFavoritePictures()
            emit(Resource.Success(pictures))
        } catch (e: IOException) {
            emit(Resource.Error("Отсутствует интернет соединение\n Попробуйте позже"))
        }
    }
}
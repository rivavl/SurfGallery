package com.marina.surfgallery.core.domain.use_case

import com.marina.surfgallery.common.Resource
import com.marina.surfgallery.core.domain.entity.Picture
import com.marina.surfgallery.core.domain.repository.PictureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetFilteredPicturesUseCase(
    private val repository: PictureRepository
) {
    suspend operator fun invoke(query: String): Flow<Resource<List<Picture>>> = flow {
        try {
            emit(Resource.Loading())
            val pictures = repository.getFilteredPictures(query)
            emit(Resource.Success(pictures.data!!))
        } catch (e: IOException) {
            emit(Resource.Error("Отсутствует интернет соединение\n Попробуйте позже"))
        }
    }
}
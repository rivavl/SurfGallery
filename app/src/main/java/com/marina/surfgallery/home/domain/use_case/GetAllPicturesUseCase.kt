package com.marina.surfgallery.home.domain.use_case

import com.marina.surfgallery.core.util.Resource
import com.marina.surfgallery.home.domain.entity.Picture
import com.marina.surfgallery.home.domain.repository.PictureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetAllPicturesUseCase(
    private val repository: PictureRepository
) {

    suspend operator fun invoke(): Flow<Resource<List<Picture>>> = flow {
        try {
            emit(Resource.Loading())
            val pictures = repository.getPictures()
            emit(Resource.Success(pictures))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Непредвиденная ошибка"))
        } catch (e: IOException) {
            emit(Resource.Error("Отсутствует интернет соединение\n Попробуйте позже"))
        }
    }
}
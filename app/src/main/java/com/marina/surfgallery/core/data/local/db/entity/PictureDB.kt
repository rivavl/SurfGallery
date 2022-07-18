package com.marina.surfgallery.core.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marina.surfgallery.core.domain.entity.Picture

@Entity(tableName = "picture", indices = [Index(value = ["id"], unique = true)])
data class PictureDB(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "photo_url")
    val photoUrl: String,
    @ColumnInfo(name = "publication_date")
    val publicationDate: Long
) {

    fun toPicture(): Picture {
        return Picture(
            id = id,
            title = title,
            content = content,
            photoUrl = photoUrl,
            publicationDate = publicationDate,
            isFavorite = true
        )
    }
}
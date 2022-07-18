package com.marina.surfgallery.core.presentation.adapter

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marina.surfgallery.R
import com.marina.surfgallery.core.presentation.adapter.PicturesListAdapter.PictureItemViewHolder
import com.marina.surfgallery.core.presentation.entity.PictureItem

class PicturesListAdapter :
    ListAdapter<PictureItem, PictureItemViewHolder>(PictureDiffUtilCallback()) {

    var onPictureItemClickListenerSave: ((PictureItem, Bitmap) -> Unit)? = null
    var onPictureItemClickListenerDelete: ((PictureItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureItemViewHolder {
        val layout = R.layout.image_item
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return PictureItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: PictureItemViewHolder, position: Int) {
        val picture = getItem(position)

        holder.heartFill.setOnClickListener {
            onPictureItemClickListenerDelete?.invoke(picture)
            holder.heartFill.visibility = View.GONE
            holder.heartOutline.visibility = View.VISIBLE
        }

        holder.heartOutline.setOnClickListener {
            onPictureItemClickListenerSave?.invoke(picture, holder.image.drawable.toBitmap())
            holder.heartFill.visibility = View.VISIBLE
            holder.heartOutline.visibility = View.GONE
        }

        with(holder) {
            loadImage(picture.photoUrl, image, holder.itemView.context)
            title.text = picture.title

            if (picture.isFavorite) {
                heartOutline.visibility = View.GONE
                heartFill.visibility = View.VISIBLE
            } else {
                heartOutline.visibility = View.VISIBLE
                heartFill.visibility = View.GONE
            }
        }
    }

    private fun loadImage(url: String, imageView: ImageView, context: Context) {
        Glide.with(context)
            .load(url)
            .into(imageView)
    }

    class PictureItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.rv_item_image)
        val title = view.findViewById<TextView>(R.id.rv_title)
        val heartOutline = view.findViewById<ImageView>(R.id.rv_heart_outline)
        val heartFill = view.findViewById<ImageView>(R.id.rv_heart_fill)
    }

    class PictureDiffUtilCallback : DiffUtil.ItemCallback<PictureItem>() {
        override fun areItemsTheSame(oldItem: PictureItem, newItem: PictureItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PictureItem, newItem: PictureItem): Boolean {
            return oldItem == newItem
        }

    }
}
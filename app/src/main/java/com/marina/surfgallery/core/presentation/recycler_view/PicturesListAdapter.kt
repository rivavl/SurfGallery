package com.marina.surfgallery.core.presentation.recycler_view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marina.surfgallery.R
import com.marina.surfgallery.core.presentation.entity.PictureItem
import com.marina.surfgallery.core.presentation.recycler_view.PicturesListAdapter.PictureItemViewHolder

class PicturesListAdapter :
    ListAdapter<PictureItem, PictureItemViewHolder>(PictureDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureItemViewHolder {
        val layout = R.layout.image_item
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return PictureItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: PictureItemViewHolder, position: Int) {
        val picture = getItem(position)

        with(holder) {
            loadImage(picture.photoUrl, image, holder.itemView.context)
            title.text = picture.title
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
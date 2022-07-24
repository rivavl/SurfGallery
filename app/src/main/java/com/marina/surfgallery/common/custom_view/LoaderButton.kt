package com.marina.surfgallery.common.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.marina.surfgallery.R
import com.marina.surfgallery.databinding.LoaderButtonViewBinding

class LoaderButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {

    private val binding = LoaderButtonViewBinding.inflate(LayoutInflater.from(context), this)

    var text: String = ""
        set(value) {
            field = value
            binding.textTv.text = value
        }

    var isLoading: Boolean = false
        set(value) {
            field = value
            binding.progressBar.isVisible = value
            binding.textTv.isVisible = !value
            binding.root.isEnabled = !value
        }

    init {
        context.withStyledAttributes(attributeSet, R.styleable.LoaderButton) {
            text = getString(R.styleable.LoaderButton_textBtn) ?: ""
            isLoading = getBoolean(R.styleable.LoaderButton_isLoading, false)
        }
    }
}
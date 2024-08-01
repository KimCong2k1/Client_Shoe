package com.fpoly.shoes_app.framework.presentation.ui.shoes.shoesdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fpoly.shoes_app.databinding.ItemImageShoeDetailViewBinding
import javax.inject.Inject

class ImageShoeDetailAdapter @Inject constructor() : RecyclerView.Adapter<ImageShoeViewHolder>() {
    var images: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageShoeViewHolder =
        ImageShoeViewHolder(
            ItemImageShoeDetailViewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageShoeViewHolder, position: Int) {
        holder.bind(images[position])
    }
}

class ImageShoeViewHolder(
    private val binding: ItemImageShoeDetailViewBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(image: String) {
        binding.run {

        }
    }
}
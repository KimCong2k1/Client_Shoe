package com.fpoly.shoes_app.framework.presentation.ui.shoes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fpoly.shoes_app.databinding.ItemShoeViewBinding
import com.fpoly.shoes_app.framework.domain.model.Shoes
import com.fpoly.shoes_app.utility.formatPriceShoe
import com.fpoly.shoes_app.utility.formatSoldShoe
import com.fpoly.shoes_app.utility.loadImage
import javax.inject.Inject

private val shoesDiff = object : DiffUtil.ItemCallback<Shoes>() {
    override fun areItemsTheSame(oldItem: Shoes, newItem: Shoes) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Shoes, newItem: Shoes) = oldItem == newItem
}

class ShoesAdapter @Inject constructor() : ListAdapter<Shoes, ShoesViewHolder>(shoesDiff) {
    private lateinit var _onClick: (Shoes) -> Unit

    fun setOnClick(onClick: (Shoes) -> Unit) {
        _onClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ShoesViewHolder(
        ItemShoeViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ), _onClick
    )

    override fun onBindViewHolder(holder: ShoesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ShoesViewHolder(
    private val binding: ItemShoeViewBinding,
    private val onClick: (Shoes) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(shoes: Shoes) {
        binding.run {
            imgShoe.loadImage(shoes.thumbnail)
            tvNameShoe.text = shoes.name
            tvRateShoe.text = "${shoes.rate?.rate}"
            tvSoldShoe.text = shoes.sold?.formatSoldShoe()
            tvPriceShoe.text = shoes.price?.formatPriceShoe()
            cvImageShoe.setOnClickListener { onClick(shoes) }
        }
    }
}

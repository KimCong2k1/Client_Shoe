package com.fpoly.shoes_app.framework.presentation.ui.checkout.discount

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fpoly.shoes_app.databinding.ItemDiscountCheckoutBinding
import com.fpoly.shoes_app.framework.domain.model.Comment
import javax.inject.Inject

private val diff = object : DiffUtil.ItemCallback<Comment>() {
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment) = oldItem == newItem
}

class ReviewAdapter @Inject constructor() :
    ListAdapter<Comment, ReviewViewHolder>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ReviewViewHolder(
        ItemDiscountCheckoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ReviewViewHolder(
    private val binding: ItemDiscountCheckoutBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(review: Comment) {
        binding.run {
            Log.d("123123", "bind: $review")
        }
    }
}

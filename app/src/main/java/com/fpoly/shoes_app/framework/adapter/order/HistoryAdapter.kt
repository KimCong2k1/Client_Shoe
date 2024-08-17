package com.fpoly.shoes_app.framework.adapter.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fpoly.shoes_app.R
import com.fpoly.shoes_app.framework.domain.model.history.HistoryShoe
class HistoryAdapter(
    private var historyShoes: List<HistoryShoe>,
    private val onClick: (HistoryShoe) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.HistoryShoeViewHolder>() {

    inner class HistoryShoeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageShoeItemImageView: ImageView = itemView.findViewById(R.id.imageShoeItem)
        private val nameShoeTextView: TextView = itemView.findViewById(R.id.nameShoeItem)
        private val contentShoeTextView: TextView = itemView.findViewById(R.id.contentShoeItem)
        private val colorShoeTextView: TextView = itemView.findViewById(R.id.colorShoeItem)
        private val sizeShoeTextView: TextView = itemView.findViewById(R.id.sizeShoeItem)

        fun bind(historyShoe: HistoryShoe) {
            nameShoeTextView.text = historyShoe.name
            contentShoeTextView.text = historyShoe.dateOrder
            Glide.with(itemView.context)
                .load(historyShoe.thumbnail)
                .placeholder(R.drawable.download) // Placeholder image
                .error(R.drawable.download) // Error image
                .into(imageShoeItemImageView)

            // Check if orderDetails is not empty
            if (historyShoe.orderDetails.isNotEmpty()) {
                val firstOrderDetail = historyShoe.orderDetails[0]
                colorShoeTextView.text = firstOrderDetail.textColor
                sizeShoeTextView.text = firstOrderDetail.size.toString()
            }

            itemView.setOnClickListener {
                onClick(historyShoe)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryShoeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryShoeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HistoryShoeViewHolder, position: Int) {
        holder.bind(historyShoes[position])
    }

    override fun getItemCount() = historyShoes.size

    fun updateHistoryShoes(newHistoryShoes: List<HistoryShoe>) {
        historyShoes = newHistoryShoes
        notifyDataSetChanged()
    }
}
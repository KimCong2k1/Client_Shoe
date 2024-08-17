package com.fpoly.shoes_app.framework.presentation.ui.orders.history.detaiHistory

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.fpoly.shoes_app.R
import com.fpoly.shoes_app.databinding.FragmentDetailHistoryBinding
import com.fpoly.shoes_app.framework.domain.model.history.HistoryShoe
import com.fpoly.shoes_app.framework.presentation.common.BaseFragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.oned.Code128Writer

class DetailHistoryFragment : BaseFragment<FragmentDetailHistoryBinding, DetailHistoryViewModel>(
    FragmentDetailHistoryBinding::inflate,
    DetailHistoryViewModel::class.java
) {
    @SuppressLint("SetTextI18n")
    override fun setupViews() {
        val historyShoe = arguments?.getParcelable<HistoryShoe>("history")

        val barcodeData = historyShoe?._id // Dữ liệu bạn muốn mã hóa
            val width = 1000
            val height = 300

            try {
                val bitmap = generateBarcode(barcodeData, width, height)
                binding.barcodeImageView.setImageBitmap(bitmap)
            } catch (e: WriterException) {
                e.printStackTrace()
            }

        binding.apply {
            Glide.with(requireContext())
            .load(historyShoe?.thumbnail)
            .placeholder(R.drawable.download) // Placeholder image
            .error(R.drawable.download) // Error image
            .into(imageShoe)
            nameShoe.text = historyShoe?.name
            contentShoe.text = historyShoe?.orderDetails?.get(0)?.quantity.toString()
            colorShoe.text = historyShoe?.orderDetails?.get(0)?.textColor
            sizeShoe.text = getString(R.string.size)+":"+historyShoe?.orderDetails?.get(0)?.size
            priceDemoOriginal.text = historyShoe?.totalPre.toString()
                priceDemoSell.text =historyShoe?.promo.toString()
                totalDemo.text =historyShoe?.total.toString()
                methodDemo.text =historyShoe?.pay
                dateDemo.text =historyShoe?.dateOrder
                statusDemo.text =historyShoe?.status
        }
        }
        @Throws(WriterException::class)
        private fun generateBarcode(text: String?, width: Int, height: Int): Bitmap {
            val writer = Code128Writer()
            val bitMatrix: BitMatrix = writer.encode(text, BarcodeFormat.CODE_128, width, height)
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            return bitmap
        }

    override fun bindViewModel() {
    }

    override fun setOnClick() {
        binding.toolbar.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}
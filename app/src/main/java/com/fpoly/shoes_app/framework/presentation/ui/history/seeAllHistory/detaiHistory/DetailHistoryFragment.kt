package com.fpoly.shoes_app.framework.presentation.ui.history.seeAllHistory.detaiHistory

import android.graphics.Bitmap
import android.graphics.Color
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fpoly.shoes_app.R
import com.fpoly.shoes_app.databinding.FragmentDetailHistoryBinding
import com.fpoly.shoes_app.databinding.FragmentSeeAllHistoryBinding
import com.fpoly.shoes_app.framework.presentation.common.BaseFragment
import com.fpoly.shoes_app.framework.presentation.ui.history.seeAllHistory.SeeAllHistoryViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.oned.Code128Writer

class DetailHistoryFragment : BaseFragment<FragmentDetailHistoryBinding, DetailHistoryViewModel>(
    FragmentDetailHistoryBinding::inflate,
    DetailHistoryViewModel::class.java
) {
    override fun setupViews() {
            val barcodeData = "sdsfsdfsd1234567890" // Dữ liệu bạn muốn mã hóa
            val width = 1000
            val height = 300

            try {
                val bitmap = generateBarcode(barcodeData, width, height)
                binding.barcodeImageView.setImageBitmap(bitmap)
            } catch (e: WriterException) {
                e.printStackTrace()
            }

        }
        @Throws(WriterException::class)
        private fun generateBarcode(text: String, width: Int, height: Int): Bitmap {
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
    }

}
package com.fpoly.shoes_app.framework.presentation.ui.setUpAccount

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.fpoly.shoes_app.R
import com.fpoly.shoes_app.databinding.FragmentSetUpAccountBinding
import com.fpoly.shoes_app.framework.data.othetasks.AddImage
import com.fpoly.shoes_app.framework.data.module.CheckValidate
import com.fpoly.shoes_app.framework.domain.model.setUp.SetUpAccount
import com.fpoly.shoes_app.framework.presentation.common.BaseFragment
import com.fpoly.shoes_app.utility.Status
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import io.github.muddz.styleabletoast.StyleableToast
import kotlinx.coroutines.launch
import java.io.File
import java.util.Calendar

@Suppress("DEPRECATION")
@AndroidEntryPoint
class SetUpAccountFragment : BaseFragment<FragmentSetUpAccountBinding, SetUpAccountViewModel>(
    FragmentSetUpAccountBinding::inflate, SetUpAccountViewModel::class.java
) {
    private val gender = arrayOf( "Nữ","Nam")
    private var uriPath : Uri?=null
    private var id=""
    private var type=1
    private fun showDatePickerDialog(dateEditText: EditText, layoutData: TextInputLayout) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, yearBirth, monthBirth, dayOfMonth ->
            val selectedDate = "$yearBirth-${monthBirth + 1}-$dayOfMonth"
            dateEditText.setText(selectedDate)
            layoutData.requestFocus()
        }, year, month, day)

        datePickerDialog.show()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            uriPath = AddImage.handleImageSelectionResult(requestCode, resultCode, data)
            uriPath?.let {
                Glide.with(requireContext())
                    .load( it)
                    .placeholder(R.drawable.baseline_account_circle_24)
                    .error(R.drawable.baseline_account_circle_24)
                    .circleCrop() // Bo tròn ảnh
                    .into(binding.imgAvatar);
//                binding.imgAvatar.setImageURI(it) // Set the image URI to the ImageView
            }
        }
    }
    override fun setupPreViews() {

    }
    override fun setupViews() {
        id = arguments?.getString("id").toString()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, gender)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
    }

    override fun bindViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.setUpResult.collect { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        showProgressbar(false)
                        val signUpResponse = result.data
                        if (signUpResponse?.success == true) {
                            val navController = findNavController()
                            binding.nameEditText.text?.clear()
                            binding.mailEditText.text?.clear()
                            binding.phoneEditText.text?.clear()
                            fragmentManager?.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

                            navController.navigate(
                                R.id.loginFragmentScreen, null, NavOptions.Builder().setPopUpTo(
                                    navController.currentDestination?.id ?: -1, true
                                ).build()
                            )
                            StyleableToast.makeText(
                                requireContext(), getString(R.string.success), R.style.success
                            ).show()
                            return@collect
                        }

                    }

                    Status.ERROR -> {
                        val errorMessage = result.message ?: "Unknown error"
                        Log.e("error",errorMessage)
                        showProgressbar(false)
                    }

                    Status.LOADING -> {
                        showProgressbar(true)
                    }

                    Status.INIT -> {
                    }
                }
            }
        }
    }

    override fun setOnClick() {
        binding.relative.setOnClickListener {
            AddImage.openImageDialog(requireContext(), requireActivity(), this@SetUpAccountFragment)
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                parent?.getItemAtPosition(position) as String
                type = position
                Log.e("type",(type + position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Xử lý khi không có mục nào được chọn
            }
        }
        binding.mailEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                CheckValidate.checkEmail(
                    requireContext(),
                    binding.mailEditText,
                    binding.layoutInputMail,
                    binding.layoutInputPhone
                )
                return@setOnEditorActionListener true
            }
            false
        }
        binding.phoneEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                CheckValidate.checkPhone(
                    requireContext(),
                    binding.phoneEditText,
                    binding.layoutInputPhone,
                    binding.btnNextPager
                )
                return@setOnEditorActionListener true
            }
            false
        }
        binding.nameEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                CheckValidate.checkStr(
                    requireContext(),
                    binding.phoneEditText,
                    binding.layoutInputPhone,
                    binding.layoutInputMail
                )
                return@setOnEditorActionListener true
            }
            false
        }
        binding.dateEditText.setOnClickListener {
            showDatePickerDialog(binding.dateEditText, binding.layoutInputMail)
        }
        binding.btnNextPager.setOnClickListener {
            val imageAccount = uriPath?.let { Uri.parse(it.toString())?.path?.let { path -> File(path) } }
            val fullName= binding.nameEditText.text.toString().trim()
            val phoneNumber= binding.phoneEditText.text.toString().trim()
            val birthDay= binding.dateEditText.text.toString().trim()
            val grender=type.toString()
            Log.e("uri",uriPath.toString())
            Log.e("idUser",id)
            Log.e("type",type.toString())
            viewModel.setUp(id, SetUpAccount(imageAccount.toString(), fullName, "", phoneNumber, birthDay, grender), imageAccount)
        }
    }
}

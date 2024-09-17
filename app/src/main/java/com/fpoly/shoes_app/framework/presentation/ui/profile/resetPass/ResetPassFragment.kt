package com.fpoly.shoes_app.framework.presentation.ui.profile.resetPass

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fpoly.shoes_app.R
import com.fpoly.shoes_app.databinding.FragmentResetPassBinding
import com.fpoly.shoes_app.framework.data.othetasks.CheckValidate.strNullOrEmpty
import com.fpoly.shoes_app.framework.domain.model.newPass.NewPass
import com.fpoly.shoes_app.framework.presentation.common.BaseFragment
import com.fpoly.shoes_app.framework.presentation.ui.forgot.CustomDialogFragment
import com.fpoly.shoes_app.framework.presentation.ui.forgot.createNewPass.CreateNewPassViewModel
import com.fpoly.shoes_app.utility.Status
import com.fpoly.shoes_app.utility.toMD5
import dagger.hilt.android.AndroidEntryPoint
import io.github.muddz.styleabletoast.StyleableToast
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResetPassFragment : BaseFragment<FragmentResetPassBinding, CreateNewPassViewModel>(
    FragmentResetPassBinding::inflate, CreateNewPassViewModel::class.java
) {
    private  var userId :String=""
    private val dialog = CustomDialogFragment()
    override fun setupPreViews() {

    }

    override fun setupViews() {
        userId = sharedPreferences.getIdUser()
    }

    override fun bindViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.createPassResult.collect { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        dialog.dismiss()
                        val forgotMailResponse = result.data
                        if (forgotMailResponse?.success == true) {
                            childFragmentManager.popBackStack()
                            childFragmentManager.popBackStackImmediate(
                                null, FragmentManager.POP_BACK_STACK_INCLUSIVE
                            )
                            StyleableToast.makeText(
                                requireContext(), getString(R.string.success), R.style.success
                            ).show()
                            return@collect
                        }
                        StyleableToast.makeText(
                            requireContext(), forgotMailResponse?.message, R.style.fail
                        ).show()
                        binding.layoutInputpassWord.error = forgotMailResponse?.message
                        binding.layoutInputrePassWord.error = forgotMailResponse?.message

                    }

                    Status.ERROR -> {
                        val errorMessage = strNullOrEmpty(result.message)
                        StyleableToast.makeText(
                            requireContext(), strNullOrEmpty(errorMessage), R.style.fail
                        ).show()
                        binding.layoutInputpassWord.error = strNullOrEmpty(errorMessage)
                        binding.layoutInputrePassWord.error = strNullOrEmpty(errorMessage)
                    }

                    Status.LOADING -> {
                        dialog.isCancelable = false
                        dialog.show(childFragmentManager, "CustomDialogFragment")
                    }

                    Status.INIT -> {
                    }
                }
                binding.btnNextPager.isEnabled = true
            }
        }
    }

    override fun setOnClick() {
        binding.btnNextPager.setOnClickListener {
            val newPass = binding.passWordEditText.text.toString().trim()
            val reNewPass = binding.rePassWordEditText.text.toString().trim()
            val oldPass = binding.oldPassWordEditText.text.toString().toMD5()
            val oldPassShare = sharedPreferences.getPassWord()
            if ( oldPass == oldPassShare){
                if (newPass == reNewPass && newPass.isNotEmpty()) {
                    viewModel.newPass(NewPass(userId, newPass.toMD5()))
                    sharedPreferences.setPassWord(sharedPreferences.getUserName(),newPass)
                }
            }else{
                StyleableToast.makeText(
                    requireContext(), strNullOrEmpty(oldPass), R.style.fail
                ).show()
                binding.layoutInputoldPassWord.error = getString(R.string.fail_password)
            }


        }
    }
}


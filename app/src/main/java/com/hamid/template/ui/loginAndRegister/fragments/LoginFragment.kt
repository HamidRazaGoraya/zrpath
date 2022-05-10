package com.hamid.template.ui.loginAndRegister.fragments

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import com.hamid.template.BuildConfig
import com.hamid.template.base.BaseFragment
import com.hamid.template.databinding.LoginFragmentBinding
import com.hamid.template.ui.loginAndRegister.RegisterVM
import com.hamid.template.ui.loginAndRegister.adopter.LoginAutoCompleteAdapter
import com.hamid.template.ui.loginAndRegister.customDialogs.EditCurrentAPI
import com.hamid.template.ui.loginAndRegister.logInRequestModel.LogInRequest
import com.hamid.template.ui.loginAndRegister.logResponseModel.LogInResponse
import com.hamid.template.ui.loginAndRegister.model.Savepassowrd
import com.hamid.template.utils.Resource
import com.hamid.template.utils.SharedPreferenceManager
import com.hamid.template.utils.checkForStaging
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding, RegisterVM>() {

    override val viewModel: RegisterVM by activityViewModels()

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    lateinit var arrayList: ArrayList<Savepassowrd.Passwords>

    override fun setBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): LoginFragmentBinding {
        return LoginFragmentBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         setUpListners()
    }

    private fun setUpListners() {
        binding.version.text="Version ${BuildConfig.VERSION_NAME}"
        arrayList= ArrayList()
        Thread{
            arrayList.addAll(sharedPreferenceManager.getSavedPassword())
            requireActivity().runOnUiThread {
                val adapter = LoginAutoCompleteAdapter(requireContext(), R.layout.simple_dropdown_item_1line, R.id.text2, arrayList)
                binding.userName.setAdapter(adapter)
                binding.userName.setOnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
                    binding.pin.setText(
                        arrayList[position].password
                    )
                }
            }
        }.start()
        binding.createNewAccount.setOnClickListener {
            viewModel.onRegisterClick()
        }
        binding.LogInButton.setOnLongClickListener {
            viewModel.moveToDashboard()
            return@setOnLongClickListener true
        }
        binding.LogInButton.setOnClickListener {
            if (varified()){
                val email=binding.userName.text.toString()
                val pin=binding.pin.text.toString()
                if (binding.SaveLogIn.isChecked){
                    Thread{
                        sharedPreferenceManager.savePassword(email,pin)
                    }.start()
                }
                viewModel.signInUser(email,pin)
                    .observe(viewLifecycleOwner){
                        when (it.status) {
                            Resource.Status.SUCCESS -> {
                                viewModel.hideLoading()
                                it.data?.let { it1 ->
                                    HandleLogIn(it1)
                                }
                            }
                            Resource.Status.ERROR -> {
                                viewModel.hideLoading()
                                it.message?.let { it1 -> showSnackBar(it1) }
                            }
                            Resource.Status.LOADING -> {
                                viewModel.showLoading()
                            }
                        }
                    }
            }
        }
        binding.forgotPassword.setOnClickListener {
            viewModel.moveToForgotPassword()
        }
        binding.version.setOnLongClickListener {
            EditCurrentAPI().show(childFragmentManager,"Edit API")
            return@setOnLongClickListener true
        }
        binding.version.checkForStaging(requireContext())

    }

    private fun HandleLogIn(it1: LogInResponse) {
        if (it1.isSuccess){
            sharedPreferenceManager.UserLogInResponse=it1
            sharedPreferenceManager.getToken=it1.data.loginResponse.token
            if (it1.data.employee.isTempPassword){
                viewModel.moveToUserDetailsFill()
            }else{
                viewModel.moveToDashboard()
            }
        }else{
            it1.message.let {
                showSnackBar(it)
            }
        }
    }

    private fun varified(): Boolean {
        if (binding.userName.text.isNullOrEmpty() || binding.userName.text.toString().replace(" ","").length<3){
         binding.userName.error="Too short"
            return false
        }
        if (binding.pin.text.isNullOrEmpty() || binding.pin.text.toString().replace(" ","").length<3){
            binding.pin.error="Too short"
            return false
        }
        return true

    }


    override fun onResume() {
        super.onResume()
    }

}
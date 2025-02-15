package com.example.androidsamplekotlinnoncompose.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.androidsamplekotlinnoncompose.EdgeMode
import com.example.androidsamplekotlinnoncompose.databinding.ActivityViewModelBinding
import com.example.androidsamplekotlinnoncompose.repository.model.user.LoginRequest
import com.example.androidsamplekotlinnoncompose.sealed.ResponseState
import com.example.androidsamplekotlinnoncompose.viewmodel.CourseViewModel
import com.example.androidsamplekotlinnoncompose.viewmodel.UserViewModel

class ViewModelActivity : BaseActivity() {
    private lateinit var binding: ActivityViewModelBinding
    private var viewModelOld: UserViewModel? = null
    private val userViewModelNew: UserViewModel by viewModels()
    private val courseViewModel: CourseViewModel by viewModels()
//    private val viewModelNew2 by viewModels<UserViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewModelBinding.inflate(layoutInflater)
        enableEdgeToEdgeMode(binding.root, binding.mainContainerCL, EdgeMode.NEW_EDGE_TO_EDGE)
        setContentView(binding.root)


        setViewModel()
        setUI()
        onBackPressedDispatcher.addCallback {
            if(binding.progressContainerCL.visibility == View.VISIBLE){
                userViewModelNew.cancelJobOrAsync()
                courseViewModel.cancelJobOrAsync()
                return@addCallback
            }

            finish()
        }
    }

//    private fun setBindingAndEdgeToEdgeUI() {
//        enableEdgeToEdge()
//
//        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
//            //調整View
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, 0, systemBars.right, 0)
//            //調整底部元件距離、頂部元件距離 (由於此案例是剛好同一元件就改一個元件，若不同元件就分開處理)
//            val param = binding.LiveDataCL.layoutParams as ViewGroup.MarginLayoutParams
//            param.setMargins(0, systemBars.top, 0, systemBars.bottom)
//            binding.LiveDataCL.layoutParams = param
//
//            insets
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            window.isNavigationBarContrastEnforced = false
//        }
//    }

    private fun setViewModel() {
        viewModelOld = ViewModelProvider(this)[UserViewModel::class.java]
        viewModelOld!!.user.observe(this) {
            it?.let {
                binding.btnLiveDataLogin.text = it.name
            }
        }

        userViewModelNew.user.observe(this) {
            it?.let {
                binding.btnLiveDataLogin.text = it.name
            }
        }

        userViewModelNew.userMern.observe(this){
            it?.let {
                binding.progressContainerCL.visibility  = when(it){
                    is ResponseState.Loading -> View.VISIBLE
                    else-> View.GONE
                }

                when(it){
                    is ResponseState.ResSuccess -> {
                        binding.groupLogin.visibility = View.GONE
                    }
                    is ResponseState.ResFailure -> {
                        Toast.makeText(this, it.resErrorMessage, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setUI() {
        binding.TIETEmail.setText("timStu01@gmail.com")
        binding.TIETPassword.setText("12345678")


        binding.btnLiveDataLogin.setOnClickListener {
            userViewModelNew.getMernLoginByGson(LoginRequest(
                binding.TIETEmail.text.toString(),
                binding.TIETPassword.text.toString()
            ))

//            userViewModelNew.getMernLoginByMoshi(LoginRequest(
//                binding.TIETEmail.text.toString(),
//                binding.TIETPassword.text.toString()
//            ))
        }

        binding.btnTestGsonAndMoshi.setOnClickListener {
            courseViewModel.testGsonAndMoshiSerialized()
        }

        binding.btnLiveDataGetCourse.setOnClickListener {

            courseViewModel.getCourse(userViewModelNew.token.value)
        }

    }





}
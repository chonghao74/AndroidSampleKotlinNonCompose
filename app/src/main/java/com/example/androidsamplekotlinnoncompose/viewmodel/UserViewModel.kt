package com.example.androidsamplekotlinnoncompose.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsamplekotlinnoncompose.repository.UserRepository
import com.example.androidsamplekotlinnoncompose.repository.model.user.LocalUser
import com.example.androidsamplekotlinnoncompose.repository.model.user.LoginRequest
import com.example.androidsamplekotlinnoncompose.repository.model.user.LoginResponse
import com.example.androidsamplekotlinnoncompose.repository.model.user.User
import com.example.androidsamplekotlinnoncompose.repository.model.user.UserTestResponse
import com.example.androidsamplekotlinnoncompose.repository.model.user.UserVO
import com.example.androidsamplekotlinnoncompose.sealed.ResponseState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel() : ViewModel() {
    private val _user = MediatorLiveData<LocalUser>() //確保資料設定都是由 ViewModel 內部調整
    val user: LiveData<LocalUser> = _user //此功能為取資料用
    private val _user2List = MediatorLiveData<List<UserTestResponse>>()
    val user2List: LiveData<List<UserTestResponse>> = _user2List //此功能為取資料用

    //private val _userMern = MediatorLiveData<UserVO>() //確保資料設定都是由 ViewModel 內部調整
    //val userMern: LiveData<UserVO> = _userMern //此功能為取資料用
    private val _userMern = MediatorLiveData<ResponseState<String?>>() //確保資料設定都是由 ViewModel 內部調整
    val userMern: LiveData<ResponseState<String?>> = _userMern //此功能為取資料用

    private val _token = MediatorLiveData<String?>() //確保資料設定都是由 ViewModel 內部調整
    val token: LiveData<String?> = _token //此功能為取資料用

    //    private lateinit var userJob: Job
    private var userJob: Job? = null
    private var userMernJob: Job? = null


    private val testURL1 = "http://192.168.131.245:1001/api/user/test-params/23123123"
    private val testURL2 = "https://jsonplaceholder.typicode.com/posts"

    private var userRepository = UserRepository()


    fun getUser(): LocalUser? {
        return user.value
    }

    fun setUser(user: LocalUser) {
        _user.value = user
    }

    fun getServerUserData() {
        viewModelScope.launch(Dispatchers.Main) {
            var json: String? = null
            runCatching {
                withContext(Dispatchers.IO) {
                    json = userRepository.getServerUserDataByURL(testURL2)
                }
            }.onSuccess {
                val listType = object : TypeToken<List<UserTestResponse>>() {}.type
                val userTest2 = Gson().fromJson<List<UserTestResponse>>(json, listType)
                _user2List.value = userTest2
                Log.i("success", "onCreate: $it")

            }.onFailure {
                Log.i("fail", "onCreate: $it")
            }
        }
    }

    fun getMernLoginByGson(loginRequest: LoginRequest) {
        userMernJob = viewModelScope.launch(Dispatchers.Main) {
            var json: LoginResponse? = null
            _userMern.value = ResponseState.Loading()

            runCatching {
                withContext(Dispatchers.IO) {
                    delay(10000)
                    json = userRepository.getUserDataByGson(loginRequest)
                    Log.i("runCatching", "onCreate: runCatching")
                }
            }.onSuccess {
                val (email, token) = setUserLoginData(json)
                if (email == null || token == null) {
                    _userMern.value = ResponseState.ResFailure("Lost email or token data")
                }
                else{
                    _userMern.value = ResponseState.ResSuccess(email)
                    _token.value = token
                }

                Log.i("success", "onCreate: $it")

            }.onFailure {
                Log.i("failure", "onCreate: $it")
                _userMern.value = ResponseState.ResFailure(it.message)
            }

        }
    }

    private fun setUserLoginData(resData: LoginResponse?): Pair<String?, String?> {

        val email = resData?.data?.user?.email
        val token = resData?.data?.token // 這個通常寫在資料庫，測試故暫時寫在 ViewModel

        return Pair(email, token)
    }

    fun getMernLoginByMoshi(loginRequest: LoginRequest) {
        userMernJob = viewModelScope.launch(Dispatchers.Main) {
            var json: LoginResponse? = null

            runCatching {
                withContext(Dispatchers.IO) {
                    json = userRepository.getUserDataByMoshi(loginRequest)
                    Log.i("runCatching", "onCreate: runCatching")
                }
            }.onSuccess {
                Log.i("success", "onCreate: $it")

            }.onFailure {
                Log.i("fail", "onCreate: $it")

            }

        }
    }

    fun cancelJobOrAsync(){
        userJob?.cancel()
        userMernJob?.cancel()
    }


    override fun onCleared() {
        super.onCleared()
        cancelJobOrAsync()
    }
}
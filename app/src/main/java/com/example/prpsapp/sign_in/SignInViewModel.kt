package com.example.prpsapp.sign_in

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.prpsapp.database.DatabaseDao
import com.example.prpsapp.prefs
import kotlinx.coroutines.*

class SignInViewModel(val database: DatabaseDao, application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val email = MutableLiveData<String>()
    val correctEmail = MutableLiveData<Boolean>()


    val password = MutableLiveData<String>()
    val correctPassword = MutableLiveData<Boolean>()

    private suspend fun getClient(email: String, password: String): Long? {
        return withContext(Dispatchers.IO) {
            async {
                database.checkClientExist(email, password)
            }.await()
        }
    }


    private val  _problemSignIn = MutableLiveData<Int>()
    val problemSignIn : LiveData<Int>
        get() = _problemSignIn

    fun onClick(){
        uiScope.launch {
            if(!(correctEmail.value ?: false) ||  !(correctPassword.value  ?: false)){
                _problemSignIn.value = 1
            } else {
                val id = getClient(email.value ?: "", password.value ?: "")
                if(id == null){
                    _problemSignIn.value = 2
                } else {
                    prefs.emailClient = email.value
                    _problemSignIn.value = 0
                }
            }

        }
    }

    private val _eventSignUp = MutableLiveData<Boolean>()
    val eventSignUp: LiveData<Boolean>
        get() = _eventSignUp

    fun signUp(){
        _eventSignUp.value = true
    }

    fun endSignUpEvent(){
        _eventSignUp.value = false
    }

    fun signOut(){
        prefs.emailClient = null
        _problemSignIn.value = 3
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
package com.example.prpsapp.registration

import android.app.Application
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.prpsapp.database.Client
import com.example.prpsapp.database.DatabaseDao
import com.example.prpsapp.database.Session
import kotlinx.coroutines.*

class RegistrationViewModel(val database: DatabaseDao, application: Application) : AndroidViewModel(application){

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val maskWatcher = MaskWatcher.buildCpf()

    val regexNumber = Regex(pattern = "^\\+375 (17|29|33|44) [0-9]{3}-[0-9]{2}-[0-9]{2}\$")

    val regexEmail = Regex(pattern = "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")




    private fun insertClient(fullName: String, phoneNumber: String, email: String, password:String){
        uiScope.launch {
            insertDatabaseClient(fullName, phoneNumber, email, password)
        }
    }

    private suspend fun insertDatabaseClient(fullName: String, phoneNumber: String, email: String, password:String) {
        withContext(Dispatchers.IO) {
            database.insertClient(Client(fullName = fullName, phoneNumber = phoneNumber, email = email, password = password))
        }
    }


    val fullName = MutableLiveData<String>()
    val correctFullName = MutableLiveData<Boolean>()


    val phoneNumber = MutableLiveData<String>()
    val correctPhoneNumber = MutableLiveData<Boolean>()


    val email = MutableLiveData<String>()
    val correctEmail = MutableLiveData<Boolean>()



    val password = MutableLiveData<String>()
    val correctPassword = MutableLiveData<Boolean>()



    val confirmPassword = MutableLiveData<String>()
    val correctConfirmPassword = MutableLiveData<Boolean>()

    private suspend fun getClient(email: String): List<Client> {
        return withContext(Dispatchers.IO) {
            async {
                database.getClientsWithEmail(email)
            }.await()
        }
    }

    private fun insertClient(client: Client) {
        uiScope.launch {
          insertClientDB(client)
        }
    }

    private suspend fun insertClientDB(client: Client) {
        withContext(Dispatchers.IO) {
            database.insertClient(client)
        }
    }


    private val  _problemCreateCode = MutableLiveData<Int>()
    val problemCreateCode : LiveData<Int>
        get() = _problemCreateCode

    fun addClientBinding(){
        uiScope.launch {
            if(!(correctFullName.value ?: false) || !(correctPhoneNumber.value ?: false) || !(correctEmail.value ?: false) ||
                !(correctPassword.value  ?: false) || !(correctConfirmPassword.value ?: false)){
                _problemCreateCode.value = 1
            } else {
                val result = getClient(email.value ?: "")

                if(!result.isEmpty()){
                    _problemCreateCode.value = 2
                } else {
                    insertClient( Client( fullName = fullName.value ?: "", phoneNumber = phoneNumber.value ?: "", email = email.value ?: "",
                        password = password.value ?: ""))
                    _problemCreateCode.value = 0
                }
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

class MaskWatcher(private val mask: String) : TextWatcher {
    private var isRunning = false
    private var isDeleting = false

    override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {
        isDeleting = count > after
    }

    override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(editable: Editable) {
        if (isRunning || isDeleting) {
            return
        }
        isRunning = true

        val editableLength = editable.length
        if (editableLength < mask.length) {
            if (mask[editableLength] != '#') {
                editable.append(mask[editableLength])
            } else if (mask[editableLength - 1] != '#') {
                editable.insert(editableLength - 1, mask, editableLength - 1, editableLength)
            }
        }

        isRunning = false
    }

    companion object {

        fun buildCpf(): MaskWatcher {
            return MaskWatcher("+### ## ###-##-##")
        }
    }
}
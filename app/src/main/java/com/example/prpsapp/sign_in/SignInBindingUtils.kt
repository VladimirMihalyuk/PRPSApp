package com.example.prpsapp.sign_in

import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter


@BindingAdapter("email")
fun EditText.setEmailListener(item: SignInViewModel?) {
    setOnFocusChangeListener(
        View.OnFocusChangeListener { _, focused ->
            if(!focused){
                if(item?.email?.value?:"" == ""){
                    item?.correctEmail?.value = false
                }
            } else {
                item?.correctEmail?.value = true
            }
        }
    )
}

@BindingAdapter("password")
fun EditText.setPasswordListener(item: SignInViewModel?) {
    setOnFocusChangeListener(
        View.OnFocusChangeListener { _, focused ->
            if(!focused){
                if(item?.password?.value?:"" == ""){
                    item?.correctPassword?.value = false
                }
            } else {
                item?.correctPassword?.value = true
            }
        }
    )
}
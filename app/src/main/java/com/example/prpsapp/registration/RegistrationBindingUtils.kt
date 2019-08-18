package com.example.prpsapp.registration

import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter
import kotlinx.android.synthetic.main.fragment_registration.view.*


@BindingAdapter("fullNameFocus")
fun EditText.setFullNameFocusListener(item: RegistrationViewModel?) {
    setOnFocusChangeListener(
        View.OnFocusChangeListener { _, focused ->
            if(!focused){
              if(item?.fullName?.value?:"" == ""){
                  item?.correctFullName?.value = false
              }
            } else {
                item?.correctFullName?.value = true
            }
        }
    )
}

@BindingAdapter("phoneNumberFocus")
fun EditText.setPhoneNumberFocusListener(item: RegistrationViewModel?) {
    setOnFocusChangeListener(
        View.OnFocusChangeListener { _, focused ->
            if(!focused){
                item?.correctPhoneNumber?.value = item?.regexNumber?.matches((item.phoneNumber.value) as CharSequence)?: false
            }
        }
    )
}

@BindingAdapter("emailFocus")
fun EditText.setEmailFocusListener(item: RegistrationViewModel?) {
    setOnFocusChangeListener(
        View.OnFocusChangeListener { _, focused ->
            if(!focused){
                item?.correctEmail?.value = item?.regexEmail?.matches((item.email.value) as CharSequence)?: false
            }
        }
    )
}


@BindingAdapter("passwordFocus")
fun EditText.setPasswordFocusListener(item: RegistrationViewModel?) {
    setOnFocusChangeListener(
        View.OnFocusChangeListener { _, focused ->
            if(!focused){
                item?.correctPassword?.value = item?.password?.value?.length ?: 0 >= 8
            }
        }
    )
}


@BindingAdapter("confirmPasswordFocus")
fun EditText.setConfirmPasswordFocusListener(item: RegistrationViewModel?) {
    setOnFocusChangeListener(
        View.OnFocusChangeListener { _, focused ->
            if(!focused){
                if(item?.confirmPassword?.value?:"" != item?.password?.value?:" "){
                    item?.correctConfirmPassword?.value = false
                }
            } else {
                item?.correctConfirmPassword?.value = true
            }

        }
    )
}


package com.example.apiClient.ui

import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apiClient.models.Person
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener


@BindingAdapter("persons")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Person>?) {
    val adapter = recyclerView.adapter as PersonAdapter
    adapter.setPersons(data ?: emptyList())
}

@BindingAdapter("android:text")
fun setIntToText(editText: EditText, value: Int?) {
    if (value != null) {
        editText.setText(value.toString())
    }
}

@InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
fun getTextAsInt(editText: EditText): Int {
    return editText.text.toString().toIntOrNull() ?: 0
}

@BindingAdapter("android:textAttrChanged")
fun setTextWatcher(editText: EditText, textAttrChanged: InverseBindingListener?) {
    if (textAttrChanged == null) {
        editText.addTextChangedListener(null)
    } else {
        editText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                textAttrChanged.onChange()
            }
        })
    }
}
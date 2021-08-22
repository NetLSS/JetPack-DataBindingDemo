package com.lilcode.example.viewmodeldemo.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

const val RESULT_KEY = "Euro Value"
const val DOLLAR_KEY = "Dollar Value"

class MainViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val usd_to_eu_rate = 0.74f

    val dollarValue: MutableLiveData<String> = savedStateHandle.getLiveData(DOLLAR_KEY)
    // viewModel 과 ui 컨트롤러 모두 변경 할 수 있도록 Mutable 로 선언
    var result: MutableLiveData<Float> = savedStateHandle.getLiveData(RESULT_KEY)

    fun convertValue() {
        dollarValue.let {
            if (!it.value.equals("")) {
                result.value = it.value?.toFloat()?.times(usd_to_eu_rate)
            } else {
                result.value = 0f
            }

            savedStateHandle.set(RESULT_KEY, result.value)
            savedStateHandle.set(DOLLAR_KEY, dollarValue.value)
        }
    }
}
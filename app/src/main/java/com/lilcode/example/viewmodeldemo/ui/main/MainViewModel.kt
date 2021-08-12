package com.lilcode.example.viewmodeldemo.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val usd_to_eu_rate = 0.74f

    val dollarValue: MutableLiveData<String> = MutableLiveData()
    // viewModel 과 ui 컨트롤러 모두 변경 할 수 있도록 Mutable 로 선언
    var result: MutableLiveData<Float> = MutableLiveData()
}
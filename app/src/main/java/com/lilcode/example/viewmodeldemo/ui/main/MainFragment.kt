package com.lilcode.example.viewmodeldemo.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import com.lilcode.example.viewmodeldemo.BR.myViewModel
import com.lilcode.example.viewmodeldemo.R
import com.lilcode.example.viewmodeldemo.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        binding.lifecycleOwner = this // 프레그먼트가 존재할 때만 바인딩이 남아있어야 하기 떄문에 연결. (소멸시 같이 소멸)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.application?.let {
            // SavedState 뷰모델로 생성 (아래와 같이 팩토리 사용해야함)
            val factory = SavedStateViewModelFactory(it, this)
            viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java) // factory 사용

            binding.setVariable(myViewModel, viewModel)
        }
    }

}
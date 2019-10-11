package com.koko.www.androiddemo.jetpack.dataBinding

import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.koko.www.androiddemo.R
import com.koko.www.androiddemo.databinding.ActivityDataBindingBinding
import com.koko.www.androiddemo.ui.SimpleViewModel

class DataBindingActivity : AppCompatActivity() {
    // Obtain ViewModel from ViewModelProviders
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(SimpleViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_data_binding)
//        baseUse()
        viewModelUse()
    }
    //只能使用一个binding
    fun baseUse() {
        //ActivityDataBindingBinding 是根据布局activity_data_binding名生成的
        val binding: ActivityDataBindingBinding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding)
        binding.name = "koko"
    }

    private fun viewModelUse() {
        val binding: ActivityDataBindingBinding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding)
        binding.simpleViewModel = viewModel
    }
}

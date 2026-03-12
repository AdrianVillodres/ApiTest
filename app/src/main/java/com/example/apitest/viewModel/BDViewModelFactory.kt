package com.example.apitest.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apitest.data.IDao

class BDViewModelFactory(private val dao: IDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BDViewModel(dao) as T
    }
}
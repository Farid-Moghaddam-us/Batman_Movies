package com.farid.coroutine.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.farid.coroutine.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository, application: Application) : AndroidViewModel(application) {

}
package com.example.pertemuan11.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan11.modeldata.DataSiswa
import com.example.pertemuan11.repositori.RepositoryDataSiswa
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface StatusUiSiswa {
    data class Success(val siswa: List<DataSiswa>) : StatusUiSiswa
    object Loading : StatusUiSiswa
    object Error : StatusUiSiswa
}

class HomeViewModel(
    private val repositoryDataSiswa: RepositoryDataSiswa
) : ViewModel() {

    private val _listSiswa = MutableStateFlow<StatusUiSiswa>(
        StatusUiSiswa.Loading
    )

    val listSiswa: StateFlow<StatusUiSiswa> =
        _listSiswa.asStateFlow()

    init {
        loadSiswa()
    }

    fun loadSiswa() {
        viewModelScope.launch {
            _listSiswa.value = StatusUiSiswa.Loading

            try {
                val data = repositoryDataSiswa.getDataSiswa()
                _listSiswa.value = StatusUiSiswa.Success(data)
            } catch (e: Exception) {
                _listSiswa.value = StatusUiSiswa.Error
            }
        }
    }
}
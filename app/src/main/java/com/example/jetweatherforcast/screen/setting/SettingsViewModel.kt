package com.example.jetweatherforcast.screen.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetweatherforcast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: WeatherDbRepository
): ViewModel() {
    private val _unitList = MutableStateFlow<List<com.example.jetweatherforcast.model.Unit>>(emptyList())
    val unitList = _unitList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUnits().distinctUntilChanged()
                .collect { listOfUnits ->
                    if (listOfUnits.isNullOrEmpty()) {
                        Log.d("TAG", ":Empty list ")
                    }else {
                        _unitList.value = listOfUnits

                    }

                }

        }
    }

    fun insertUnit(unit: com.example.jetweatherforcast.model.Unit) = viewModelScope.launch { repository.insertUnit(unit) }
    fun updateUnit(unit: com.example.jetweatherforcast.model.Unit) = viewModelScope.launch { repository.updateUnit(unit) }
    fun deleteUnit(unit: com.example.jetweatherforcast.model.Unit) = viewModelScope.launch { repository.deleteUnit(unit) }
    fun deleteAllUnits() = viewModelScope.launch { repository.deleteAllUnits() }

}
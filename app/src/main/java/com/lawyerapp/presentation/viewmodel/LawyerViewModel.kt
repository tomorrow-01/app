package com.lawyerapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lawyerapp.data.model.FilterOptions
import com.lawyerapp.data.model.Lawyer
import com.lawyerapp.data.model.LawyerUiState
import com.lawyerapp.data.repository.LawyerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LawyerViewModel(
    private val repository: LawyerRepository = LawyerRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(LawyerUiState())
    val uiState: StateFlow<LawyerUiState> = _uiState.asStateFlow()

    // Static data from repository
    val cities = repository.getCities()
    val legalFields = repository.getLegalFields()
    val industries = repository.getIndustries()

    init {
        loadAllLawyers()
    }

    private fun loadAllLawyers() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            repository.getAllLawyers().collect { result ->
                result.fold(
                    onSuccess = { lawyers ->
                        _uiState.update { currentState ->
                            currentState.copy(
                                lawyers = lawyers,
                                filteredLawyers = lawyers,
                                isLoading = false,
                                error = null
                            )
                        }
                    },
                    onFailure = { error ->
                        _uiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                error = error.message ?: "Đã xảy ra lỗi không xác định"
                            )
                        }
                    }
                )
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { currentState ->
            currentState.copy(
                filterOptions = currentState.filterOptions.copy(searchQuery = query)
            )
        }
    }

    fun updateCity(city: String) {
        _uiState.update { currentState ->
            currentState.copy(
                filterOptions = currentState.filterOptions.copy(selectedCity = city)
            )
        }
        applyFilters()
    }

    fun updateField(field: String) {
        _uiState.update { currentState ->
            currentState.copy(
                filterOptions = currentState.filterOptions.copy(selectedField = field)
            )
        }
        applyFilters()
    }

    fun updateIndustry(industry: String) {
        _uiState.update { currentState ->
            currentState.copy(
                filterOptions = currentState.filterOptions.copy(selectedIndustry = industry)
            )
        }
        applyFilters()
    }

    fun updateMinRating(rating: Float) {
        _uiState.update { currentState ->
            currentState.copy(
                filterOptions = currentState.filterOptions.copy(minRating = rating)
            )
        }
        applyFilters()
    }

    fun updateOnlineOnly(onlineOnly: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                filterOptions = currentState.filterOptions.copy(onlineOnly = onlineOnly)
            )
        }
        applyFilters()
    }

    fun searchLawyers() {
        applyFilters()
    }

    private fun applyFilters() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            val options = _uiState.value.filterOptions
            repository.searchLawyers(
                query = options.searchQuery,
                city = options.selectedCity,
                field = options.selectedField,
                industry = options.selectedIndustry,
                minRating = options.minRating,
                onlineOnly = options.onlineOnly
            ).collect { result ->
                result.fold(
                    onSuccess = { lawyers ->
                        _uiState.update { currentState ->
                            currentState.copy(
                                filteredLawyers = lawyers,
                                isLoading = false,
                                error = null
                            )
                        }
                    },
                    onFailure = { error ->
                        _uiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                error = error.message ?: "Đã xảy ra lỗi không xác định"
                            )
                        }
                    }
                )
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    fun refreshData() {
        loadAllLawyers()
    }

    fun clearFilters() {
        _uiState.update { currentState ->
            currentState.copy(
                filterOptions = FilterOptions(),
                filteredLawyers = currentState.lawyers
            )
        }
    }

    fun getLawyerById(id: Int): Lawyer? {
        return repository.getLawyerById(id)
    }
}

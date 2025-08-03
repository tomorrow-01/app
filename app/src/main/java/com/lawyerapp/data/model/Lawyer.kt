package com.lawyerapp.data.model

data class Lawyer(
    val id: Int,
    val name: String,
    val specialties: List<String>,
    val industries: List<String>,
    val city: String,
    val rating: Float,
    val reviews: Int,
    val experience: String,
    val pricePerHour: String,
    val avatarUrl: String,
    val responseTime: String,
    val description: String,
    val phoneNumber: String,
    val isOnline: Boolean = false,
    val address: String = "",
    val education: String = "",
    val languages: List<String> = listOf("Tiếng Việt"),
    val certifications: List<String> = emptyList(),
    val successRate: Float = 0f,
    val totalCases: Int = 0
)

data class FilterOptions(
    val selectedCity: String = "Tất cả",
    val selectedField: String = "Tất cả",
    val selectedIndustry: String = "Tất cả",
    val searchQuery: String = "",
    val minRating: Float = 0f,
    val maxPrice: Int = Int.MAX_VALUE,
    val onlineOnly: Boolean = false
)

data class LawyerUiState(
    val lawyers: List<Lawyer> = emptyList(),
    val filteredLawyers: List<Lawyer> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val filterOptions: FilterOptions = FilterOptions(),
    val isRefreshing: Boolean = false
)

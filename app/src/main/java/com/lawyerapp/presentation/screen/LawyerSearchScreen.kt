package com.lawyerapp.presentation.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lawyerapp.presentation.components.LawyerCard
import com.lawyerapp.presentation.components.SearchBar
import com.lawyerapp.presentation.components.FilterChips
import com.lawyerapp.presentation.components.FilterBottomSheet
import com.lawyerapp.presentation.components.ErrorMessage
import com.lawyerapp.presentation.viewmodel.LawyerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LawyerSearchScreen(
    viewModel: LawyerViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showFilterSheet by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Tìm Luật Sư",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Kết nối với luật sư chuyên nghiệp",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Row {
                IconButton(onClick = viewModel::clearFilters) {
                    Icon(
                        imageVector = Icons.Default.ClearAll,
                        contentDescription = "Xóa bộ lọc",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = { showFilterSheet = true }) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = "Bộ lọc",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Search Bar
        SearchBar(
            query = uiState.filterOptions.searchQuery,
            onQueryChange = viewModel::updateSearchQuery,
            onSearch = viewModel::searchLawyers,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Filter Chips
        FilterChips(
            selectedCity = uiState.filterOptions.selectedCity,
            selectedField = uiState.filterOptions.selectedField,
            selectedIndustry = uiState.filterOptions.selectedIndustry,
            cities = viewModel.cities,
            fields = viewModel.legalFields,
            industries = viewModel.industries,
            onCitySelected = viewModel::updateCity,
            onFieldSelected = viewModel::updateField,
            onIndustrySelected = viewModel::updateIndustry
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Results count and sorting
        if (!uiState.isLoading) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tìm thấy ${uiState.filteredLawyers.size} luật sư",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                if (uiState.filteredLawyers.isNotEmpty()) {
                    TextButton(
                        onClick = viewModel::refreshData
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Làm mới")
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Content
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Đang tìm kiếm luật sư...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
            uiState.error != null -> {
                ErrorMessage(
                    message = uiState.error!!,
                    onRetry = viewModel::refreshData,
                    onDismiss = viewModel::clearError
                )
            }
            
            uiState.filteredLawyers.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.SearchOff,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Không tìm thấy luật sư phù hợp",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Hãy thử điều chỉnh bộ lọc tìm kiếm",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = viewModel::clearFilters
                        ) {
                            Text("Xóa bộ lọc")
                        }
                    }
                }
            }
            
            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(
                        items = uiState.filteredLawyers,
                        key = { lawyer -> lawyer.id }
                    ) { lawyer ->
                        LawyerCard(
                            lawyer = lawyer,
                            onCallClick = {
                                try {
                                    val intent = Intent(Intent.ACTION_DIAL).apply {
                                        data = Uri.parse("tel:${lawyer.phoneNumber}")
                                    }
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    // Handle error
                                }
                            },
                            onMessageClick = {
                                try {
                                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                                        data = Uri.parse("smsto:${lawyer.phoneNumber}")
                                        putExtra("sms_body", "Xin chào ${lawyer.name}, tôi muốn tư vấn pháp lý...")
                                    }
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    // Handle error
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    // Filter Bottom Sheet
    if (showFilterSheet) {
        FilterBottomSheet(
            filterOptions = uiState.filterOptions,
            cities = viewModel.cities,
            fields = viewModel.legalFields,
            industries = viewModel.industries,
            onCitySelected = viewModel::updateCity,
            onFieldSelected = viewModel::updateField,
            onIndustrySelected = viewModel::updateIndustry,
            onMinRatingChanged = viewModel::updateMinRating,
            onOnlineOnlyChanged = viewModel::updateOnlineOnly,
            onDismiss = { showFilterSheet = false },
            onApply = {
                viewModel.searchLawyers()
                showFilterSheet = false
            },
            onClearFilters = {
                viewModel.clearFilters()
                showFilterSheet = false
            }
        )
    }
}

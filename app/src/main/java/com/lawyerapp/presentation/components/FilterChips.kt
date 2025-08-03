package com.lawyerapp.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChips(
    selectedCity: String,
    selectedField: String,
    selectedIndustry: String,
    cities: List<String>,
    fields: List<String>,
    industries: List<String>,
    onCitySelected: (String) -> Unit,
    onFieldSelected: (String) -> Unit,
    onIndustrySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showCityDropdown by remember { mutableStateOf(false) }
    var showFieldDropdown by remember { mutableStateOf(false) }
    var showIndustryDropdown by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // City Filter
        Box {
            FilterChip(
                onClick = { showCityDropdown = true },
                label = { 
                    Text(
                        text = if (selectedCity.length > 10) "${selectedCity.take(8)}..." else selectedCity,
                        style = MaterialTheme.typography.labelMedium
                    ) 
                },
                selected = selectedCity != "Tất cả",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
            
            DropdownMenu(
                expanded = showCityDropdown,
                onDismissRequest = { showCityDropdown = false }
            ) {
                cities.forEach { city ->
                    DropdownMenuItem(
                        text = { 
                            Text(
                                text = city,
                                style = MaterialTheme.typography.bodyMedium
                            ) 
                        },
                        onClick = {
                            onCitySelected(city)
                            showCityDropdown = false
                        },
                        leadingIcon = {
                            if (city == selectedCity) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    )
                }
            }
        }

        // Field Filter
        Box {
            FilterChip(
                onClick = { showFieldDropdown = true },
                label = { 
                    Text(
                        text = if (selectedField.length > 10) "${selectedField.take(8)}..." else selectedField,
                        style = MaterialTheme.typography.labelMedium
                    ) 
                },
                selected = selectedField != "Tất cả",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Gavel,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
            
            DropdownMenu(
                expanded = showFieldDropdown,
                onDismissRequest = { showFieldDropdown = false }
            ) {
                fields.forEach { field ->
                    DropdownMenuItem(
                        text = { 
                            Text(
                                text = field,
                                style = MaterialTheme.typography.bodyMedium
                            ) 
                        },
                        onClick = {
                            onFieldSelected(field)
                            showFieldDropdown = false
                        },
                        leadingIcon = {
                            if (field == selectedField) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    )
                }
            }
        }

        // Industry Filter
        Box {
            FilterChip(
                onClick = { showIndustryDropdown = true },
                label = { 
                    Text(
                        text = if (selectedIndustry.length > 10) "${selectedIndustry.take(8)}..." else selectedIndustry,
                        style = MaterialTheme.typography.labelMedium
                    ) 
                },
                selected = selectedIndustry != "Tất cả",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Business,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
            )
            
            DropdownMenu(
                expanded = showIndustryDropdown,
                onDismissRequest = { showIndustryDropdown = false }
            ) {
                industries.forEach { industry ->
                    DropdownMenuItem(
                        text = { 
                            Text(
                                text = industry,
                                style = MaterialTheme.typography.bodyMedium
                            ) 
                        },
                        onClick = {
                            onIndustrySelected(industry)
                            showIndustryDropdown = false
                        },
                        leadingIcon = {
                            if (industry == selectedIndustry) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

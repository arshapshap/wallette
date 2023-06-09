package com.example.feature_settings.presentation.screen.singleCategory

import com.example.common.domain.models.AccountIcon
import com.example.common.domain.models.CategoryIcon
import com.example.common.domain.models.Currency
import com.example.common.domain.models.TransactionType

data class EditingCategory(
    val id: String? = null,
    val name: String = "",
    val icon: CategoryIcon = CategoryIcon.Empty,
    val type: TransactionType? = null
)
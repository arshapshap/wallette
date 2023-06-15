package com.example.feature_statistics_impl.domain

import com.example.common.data.SettingsManager
import com.example.common.domain.models.Account
import com.example.common.domain.repositories.AccountRepository
import com.example.common.domain.repositories.TransactionRepository
import com.example.common.presentation.extensions.between
import com.example.feature_statistics_impl.domain.models.TransactionGroup
import com.example.feature_statistics_impl.domain.utils.filterByViewedAccount
import com.example.feature_statistics_impl.domain.utils.groupByCategory
import com.example.feature_statistics_impl.domain.utils.groupByDate
import com.example.feature_statistics_impl.domain.utils.groupByTag
import com.example.feature_statistics_impl.presentation.screen.transactionsList.SortingType
import java.util.*
import javax.inject.Inject

class TransactionsInteractor @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
    private val settingsManager: SettingsManager
) {

    suspend fun getTransactionGroups(sortingType: SortingType, periodStart: Date?, periodEnd: Date?): List<TransactionGroup> {
        val transactions = transactionRepository.getTransactions()
            .filterByViewedAccount(getViewedAccount()?.id)
            .filter { it.date.between(periodStart, periodEnd) }

        val groups = when (sortingType) {
            SortingType.ByDate -> transactions.groupByDate()
            SortingType.ByCategory -> transactions.groupByCategory()
            SortingType.ByTag -> transactions.groupByTag()
        }
        return groups
    }

    private suspend fun getViewedAccount(): Account? {
        val accountId = settingsManager.getViewedAccountId() ?: return null

        val account = accountRepository.getAccountById(accountId)
        if (account == null)
            settingsManager.setViewedAccount(null)

        return account
    }
}
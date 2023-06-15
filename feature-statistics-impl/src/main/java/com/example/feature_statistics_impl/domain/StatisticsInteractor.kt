package com.example.feature_statistics_impl.domain

import com.example.common.data.SettingsManager
import com.example.common.domain.models.Account
import com.example.common.domain.models.Category
import com.example.common.domain.models.Tag
import com.example.common.domain.models.Transaction
import com.example.common.domain.models.enums.Currency
import com.example.common.domain.repositories.AccountRepository
import com.example.common.domain.repositories.CategoryRepository
import com.example.common.domain.repositories.TagRepository
import com.example.common.domain.repositories.TransactionRepository
import com.example.feature_statistics_impl.domain.models.TransactionGroupByPeriod
import com.example.feature_statistics_impl.domain.utils.filterByViewedAccount
import com.example.feature_statistics_impl.domain.utils.groupByTimePeriod
import javax.inject.Inject

class StatisticsInteractor @Inject constructor(
    private val accountRepository: AccountRepository,
    private val categoryRepository: CategoryRepository,
    private val tagRepository: TagRepository,
    private val transactionRepository: TransactionRepository,
    private val settingsManager: SettingsManager
) {

    suspend fun getBalance(): Double {
        val viewedAccount = getViewedAccount()

        if (viewedAccount != null)
            return viewedAccount.currentBalance

        return accountRepository.getAccounts().sumOf { it.currentBalance }
    }

    fun getMainCurrency(): Currency
        = settingsManager.getMainCurrency()

    suspend fun getTransactionsByPeriod(): List<TransactionGroupByPeriod> {
        val viewedAccount = getViewedAccount()
        return transactionRepository.getTransactions()
            .filterByViewedAccount(viewedAccount?.id)
            .sortedBy { it.date }
            .groupByTimePeriod(
                timePeriod = settingsManager.getViewedTimePeriod(),
                firstDayOfWeek = settingsManager.getFirstDayOfWeek(),
                firstDayOfMonth = settingsManager.getFirstDayOfMonth(),
                viewedAccountId = viewedAccount?.id
            )
    }

    suspend fun createTransaction(transaction: Transaction) {
        transactionRepository.createTransaction(transaction)

        val account = transaction.account
        accountRepository.updateAccount(account.copy(
            currentBalance = account.currentBalance + transaction.amount
        ))
    }

    suspend fun editTransaction(transaction: Transaction) {
        val oldAmount = transactionRepository.getTransactionById(transaction.id).amount
        transactionRepository.updateTransaction(transaction)

        val account = transaction.account
        accountRepository.updateAccount(account.copy(
            currentBalance = account.currentBalance + transaction.amount - oldAmount
        ))
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        transactionRepository.deleteTransaction(transaction)

        val account = transaction.account
        accountRepository.updateAccount(account.copy(
            currentBalance = account.currentBalance - transaction.amount
        ))
    }

    suspend fun getCategories(): List<Category> {
        return categoryRepository.getCategories()
    }

    suspend fun getAccounts(): List<Account> {
        return accountRepository.getAccounts()
    }

    suspend fun getTags(): List<Tag> {
        return tagRepository.getTags()
    }

    private suspend fun getViewedAccount(): Account? {
        val accountId = settingsManager.getViewedAccountId() ?: return null

        val account = accountRepository.getAccountById(accountId)
        if (account == null)
            settingsManager.setViewedAccount(null)

        return account
    }
}
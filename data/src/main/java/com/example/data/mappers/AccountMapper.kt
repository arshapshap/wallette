package com.example.data.mappers

import com.example.core_network.data.models.response.AccountResponse
import com.example.common.domain.models.Account
import com.example.common.domain.models.enums.AccountIcon
import com.example.common.domain.models.enums.Currency
import com.example.core_db.models.entities.AccountLocal
import com.example.core_network.data.models.request.account.AccountCreatingModel
import com.example.core_network.data.models.request.account.AccountEditingModel
import javax.inject.Inject

class AccountMapper @Inject constructor() {

    fun map(response: AccountResponse?): Account {
        return response?.let {
            Account(
                id = response.id ?: 0,
                name = response.name ?: "",
                icon = AccountIcon.valueOf(response.icon ?: "Empty"),
                currentBalance = response.currentBalance ?: 0.0,
                startBalance = response.startBalance ?: 0.0,
                currency = Currency.valueOf(response.currency ?: "RUB")
            )
        } ?: Account(
            id = 0,
            name = "",
            icon = AccountIcon.Empty,
            currentBalance = 0.0,
            startBalance = 0.0,
            currency = Currency.RUB
        )
    }

    fun map(local: AccountLocal): Account {
        return with (local) {
            Account(
                id = accountId,
                name = name,
                icon = AccountIcon.valueOf(icon),
                currentBalance = currentBalance,
                startBalance = startBalance,
                currency = Currency.valueOf(currency)
            )
        }
    }

    fun map(account: Account): AccountLocal {
        return with (account) {
            AccountLocal(
                accountId = id,
                name = name,
                icon = icon.name,
                currentBalance = currentBalance,
                startBalance = startBalance,
                currency = currency.name
            )
        }
    }

    fun mapToCreatingModel(account: Account): AccountCreatingModel {
        return with (account) {
            AccountCreatingModel(
                id = id.toString(),
                name = name,
                icon = icon.name,
                startBalance = startBalance,
                currency = currency.name
            )
        }
    }

    fun mapToEditingModel(account: Account): AccountEditingModel {
        return with (account) {
            AccountEditingModel(
                id = id.toString(),
                name = name,
                icon = icon.name,
                currentBalance = currentBalance,
                startBalance = startBalance,
                currency = currency.name
            )
        }
    }
}
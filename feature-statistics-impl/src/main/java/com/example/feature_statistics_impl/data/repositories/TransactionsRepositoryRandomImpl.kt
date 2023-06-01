package com.example.feature_statistics_impl.data.repositories

import com.example.common.domain.models.*
import com.example.common.domain.models.Currency
import com.example.feature_statistics_impl.domain.repositories.TransactionsRepository
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

class TransactionsRepositoryRandomImpl @Inject constructor() : TransactionsRepository {

    override suspend fun getTransactions(): List<Transaction> {
        return getRandomList()
    }

    private fun getRandomList(): List<Transaction> {
        val account = Account(
            id = "acc123",
            name = "Main",
            icon = "card",
            balance = 3500,
            currency = Currency.RUB
        )
        val rand = Random(123)

        var amount = getRandomAmount(rand)
        var transaction = Transaction(
            id = "0",
            type = TransactionType.Expense,
            date = Calendar.getInstance().time,
            amount = amount,
            description = "",
            account = account,
            category = null,
            transactionGroup = null,
            isTransactionGroup = false,
            tags = listOf()
        )

        val list = arrayListOf<Transaction>()

        for (i in 0..50) {
            amount = getRandomAmount(rand)
            transaction = transaction.copy(
                id = "$i",
                date = getRandomDate(rand),
                amount = amount,
                category = getRandomCategory(amount, rand),
                description = "id $i",
                tags = getRandomTags(rand)
            )
            list.add(transaction)

        }

        return list
    }

    private fun getRandomDate(rand: Random): Date {
        val date = Calendar.getInstance()
        date.add(Calendar.DAY_OF_MONTH, (-5..0).random(rand))
        return date.time
    }

    private fun getRandomTags(rand: Random): List<Tag> {
        val tagRed = Tag(
            id = "tagRed",
            name = "Красный",
            color = "#FF0000"
        )
        val tagBlue = Tag(
            id = "tagBlue",
            name = "Синий",
            color = "#0000FF"
        )
        val tagGreen = Tag(
            id = "tagGreen",
            name = "Зелёный",
            color = "#00FF00"
        )

        val result = mutableListOf<Tag>()
        if ((0..1).random(rand) == 1)
            result.add(tagRed)
        if ((0..1).random(rand) == 1)
            result.add(tagBlue)
        if ((0..1).random(rand) == 1)
            result.add(tagGreen)

        return result
    }

    private fun getRandomAmount(rand: Random): Int {
        return (-1000..1000).random(rand)
    }

    private fun getRandomCategory(amount: Int, rand: Random): Category? {
        val category1 = Category(
            id = "cat1",
            name = "Доход 1",
            icon = "",
            type = TransactionType.Income
        )
        val category2 = Category(
            id = "cat2",
            name = "Доход 2",
            icon = "",
            type = TransactionType.Income
        )
        val category3 = Category(
            id = "cat3",
            name = "Расход 1",
            icon = "",
            type = TransactionType.Expense
        )
        val category4 = Category(
            id = "cat4",
            name = "Расход 2",
            icon = "",
            type = TransactionType.Expense
        )
        return when ((0..2).random(rand)) {
            1 -> if (amount > 0) category1 else category3
            2 -> if (amount > 0) category2 else category4
            else -> null
        }
    }
}
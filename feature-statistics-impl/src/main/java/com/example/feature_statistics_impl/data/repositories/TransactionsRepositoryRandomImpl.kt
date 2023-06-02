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
            icon = AccountIcon.Card,
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
        val list = arrayListOf<Tag>()
        val rand = Random(1234)
        for (i in 0..10) {
            list.add(
                Tag(
                    id = i.toString(),
                    name = "Метка $i",
                    color = getRandomColor(rand)
                )
            )
        }

        val result = mutableListOf<Tag>()
        list.forEach {
            if ((0..1).random(rand) == 1)
                result.add(it)
        }

        return result
    }

    private fun getRandomColor(rand: Random): String {
        val r = rand.nextInt(256).toString(16).padStart(2, '0')
        val g = rand.nextInt(256).toString(16).padStart(2, '0')
        val b = rand.nextInt(256).toString(16).padStart(2, '0')
        return "#$r$g$b"
    }

    private fun getRandomAmount(rand: Random): Int {
        return (-1000..1000).random(rand)
    }

    private fun getRandomCategory(amount: Int, rand: Random): Category? {
        val list = arrayListOf<Category>()
        for (i in 0..10) {
            list.add(
                Category(
                    id = i.toString(),
                    name = "Категория $i",
                    icon = CategoryIcon.values().filter { it.name != "Empty" }.random(rand),
                    type = TransactionType.values().random(rand)
                )
            )
        }
        return list.random(rand)
    }
}
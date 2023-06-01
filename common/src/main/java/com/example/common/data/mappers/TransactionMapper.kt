package com.example.common.data.mappers

import com.example.common.data.models.response.TransactionResponse
import com.example.common.domain.models.Transaction

interface TransactionMapper {

    fun map(response: TransactionResponse): Transaction
}
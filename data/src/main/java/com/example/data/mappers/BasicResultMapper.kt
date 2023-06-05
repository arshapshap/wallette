package com.example.data.mappers

import com.example.common.domain.models.network.BasicResult
import com.example.core_network.data.models.response.BasicResponse
import javax.inject.Inject

class BasicResultMapper @Inject constructor() {

    fun map(response: BasicResponse): BasicResult {
        return with(response) {
            BasicResult(
                isSuccessful = isSuccessful ?: false,
                errorMessage = errorMessage ?: ""
            )
        }
    }
}
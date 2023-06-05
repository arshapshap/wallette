package com.example.core_network.data.services

import com.example.core_network.data.models.request.category.CategoryCreatingModel
import com.example.core_network.data.models.request.category.CategoryEditingModel
import com.example.core_network.data.models.response.*
import retrofit2.http.*

interface CategoriesApiService {

    @GET("tags/get")
    suspend fun getCategories(): ArrayList<CategoryResponse>

    @GET("tags/get/:id")
    suspend fun getCategoryById(@Query("id")  id: Long): CategoryResponse

    @POST("tags/create")
    suspend fun createCategory(@Body model: CategoryCreatingModel): BasicResponse

    @PUT("tags/update")
    suspend fun updateCategory(@Body model: CategoryEditingModel): BasicResponse

    @DELETE("tags/delete/:id")
    suspend fun deleteCategoryById(@Query("id") id: Long): BasicResponse
}
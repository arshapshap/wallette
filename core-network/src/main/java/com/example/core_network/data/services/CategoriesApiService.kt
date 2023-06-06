package com.example.core_network.data.services

import com.example.core_network.data.models.request.category.CategoryCreatingModel
import com.example.core_network.data.models.request.category.CategoryEditingModel
import com.example.core_network.data.models.response.*
import retrofit2.http.*

interface CategoriesApiService {

    @GET("categories/get")
    suspend fun getCategories(): ArrayList<CategoryResponse>

    @GET("categories/get/:id")
    suspend fun getCategoryById(@Query("id")  id: Long): CategoryResponse

    @POST("categories/create")
    suspend fun createCategory(@Body model: CategoryCreatingModel): BasicResponse

    @PUT("categories/update")
    suspend fun updateCategory(@Body model: CategoryEditingModel): BasicResponse

    @DELETE("categories/delete/:id")
    suspend fun deleteCategoryById(@Query("id") id: Long): BasicResponse
}
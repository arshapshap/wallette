package com.example.core_network.data.services

import com.example.core_network.data.models.request.category.CategoryCreatingModel
import com.example.core_network.data.models.request.category.CategoryEditingModel
import com.example.core_network.data.models.response.*
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface CategoriesApiService {

    @GET("tags/get")
    suspend fun getCategories(): ArrayList<CategoryResponse>

    @GET("tags/get/:id")
    suspend fun getCategoryById(id: Long): CategoryResponse

    @POST("tags/create")
    suspend fun createCategory(model: CategoryCreatingModel): EntityCreatingResponse

    @PUT("tags/update")
    suspend fun updateCategory(model: CategoryEditingModel): BasicResponse

    @DELETE("tags/delete/:id")
    suspend fun deleteCategoryById(id: Long): BasicResponse
}
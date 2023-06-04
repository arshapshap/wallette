package com.example.core_network.data.services

import com.example.core_network.data.models.request.tag.TagCreatingModel
import com.example.core_network.data.models.request.tag.TagEditingModel
import com.example.core_network.data.models.response.BasicResponse
import com.example.core_network.data.models.response.EntityCreatingResponse
import com.example.core_network.data.models.response.TagResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface TagsApiService {

    @GET("tags/get")
    suspend fun getTags(): ArrayList<TagResponse>

    @GET("tags/get/:id")
    suspend fun getTagById(id: Long): TagResponse

    @POST("tags/create")
    suspend fun createTag(model: TagCreatingModel): EntityCreatingResponse

    @PUT("tags/update")
    suspend fun updateTag(model: TagEditingModel): BasicResponse

    @DELETE("tags/delete/:id")
    suspend fun deleteTagById(id: Long): BasicResponse
}
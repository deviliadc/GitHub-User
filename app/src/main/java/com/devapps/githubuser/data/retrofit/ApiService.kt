package com.devapps.githubuser.data.retrofit

import com.devapps.githubuser.data.response.DetailUserResponse
import com.devapps.githubuser.data.response.ItemsItem
import com.devapps.githubuser.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun searchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

}

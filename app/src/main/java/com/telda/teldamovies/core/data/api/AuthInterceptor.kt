package com.telda.teldamovies.core.data.api

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {


    /** The Token should not be added in the code
     * but as it is a simple task we will leave
     * it here
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkZGM5NjdiZDFmYzYyODY5ZDZjNTlhMTQyYjNhYTQ3OCIsIm5iZiI6MTc0ODM3OTQ4Ny40MTgsInN1YiI6IjY4MzYyNzVmMDc5YTQyZTI4NzAzN2VkMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ._lw8LIzh5FxRy55ubkULfvB_XQDskl_-bga1DO17DrQ"
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", token)
            .build()
        return chain.proceed(newRequest)
    }
}
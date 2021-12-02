package com.jetpack.retrofitdemo.network

import com.jetpack.retrofitdemo.model.Album
import com.jetpack.retrofitdemo.model.AlbumItem
import retrofit2.Response
import retrofit2.http.*

interface AlbumService {

    @GET("/albums")
    suspend fun getAlbums():Response<Album>

    @GET("/albums")
    suspend fun getSortedAlbums(@Query("userId") userId:Int ):Response<Album>

    @GET("/albums/{id}")
    suspend fun getAlbum(@Path("id")albumId:Int): Response<AlbumItem>

    @POST("/albums")
    suspend fun uploadAlbum(@Body albumItem: AlbumItem): Response<AlbumItem>
}
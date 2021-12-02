package com.jetpack.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.jetpack.retrofitdemo.model.Album
import com.jetpack.retrofitdemo.model.AlbumItem
import com.jetpack.retrofitdemo.network.AlbumService
import com.jetpack.retrofitdemo.network.RetrofitInstance
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    lateinit var retrofitService: AlbumService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        retrofitService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)

        getRequestWithQueryParameters()
        // getRequestWithPathParameters()
        //uploadAlbum()

    }

    private fun getRequestWithQueryParameters() {

        val responseLiveData: LiveData<Response<Album>> = liveData {
            val albums = retrofitService.getSortedAlbums(3)
            emit(albums)
        }

        responseLiveData.observe(this, Observer {
            val albumList = it.body()?.listIterator()
            if (albumList != null) {
                while (albumList.hasNext()) {
                    val albumItem = albumList.next()
                    val result = " " + "Album Title:  ${albumItem.title}" + "\n" +
                            " " + "Album Id:  ${albumItem.id}" + "\n" +
                            " " + "User Id:  ${albumItem.userId}" + "\n\n\n"
                    textView.append(result)
                }
            }
        })
    }

    private fun getRequestWithPathParameters() {
        val pathResponse: LiveData<Response<AlbumItem>> = liveData {
            val response = retrofitService.getAlbum(3)
            emit(response)
        }

        pathResponse.observe(this, Observer {
            val title = it.body()?.title
            Toast.makeText(applicationContext, title, Toast.LENGTH_SHORT).show()
        })
    }

    private fun uploadAlbum() {
        val album = AlbumItem(0, "My Title", 3)
        val postResponse: LiveData<Response<AlbumItem>> = liveData {
            val response = retrofitService.uploadAlbum(album)
            emit(response)
        }

        postResponse.observe(this, Observer {
            val item = it.body()
            val result = " " + "Album Title:  ${item?.title}" + "\n" +
                    " " + "Album Id:  ${item?.id}" + "\n" +
                    " " + "User Id:  ${item?.userId}" + "\n\n\n"
            textView.text = result
        })
    }
}
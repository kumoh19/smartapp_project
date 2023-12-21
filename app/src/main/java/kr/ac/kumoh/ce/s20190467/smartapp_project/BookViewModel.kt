package kr.ac.kumoh.ce.s20190467.smartapp_project

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookViewModel() : ViewModel() {
    private val SERVERURL = "https://port-0-backend-5r422alq94lc6u.sel4.cloudtype.app/"
    private val bookApi: BookApi
    private val _bookList = MutableLiveData<List<Book>>()
    val bookList: LiveData<List<Book>>
        get() = _bookList

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(SERVERURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        bookApi = retrofit.create(BookApi::class.java)
        fetchData()
    }

    private fun fetchData(){
        viewModelScope.launch {
            try {
                val response = bookApi.getBooks()
                _bookList.value = response
            } catch (e: Exception){
                Log.e("fetchData()", e.toString())
            }
        }
    }

}
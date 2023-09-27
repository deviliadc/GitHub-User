package com.devapps.githubuser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devapps.githubuser.data.response.DetailUserResponse
import com.devapps.githubuser.data.response.ItemsItem
import com.devapps.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _detailuser = MutableLiveData<DetailUserResponse>()
    val detailuser: LiveData<DetailUserResponse> = _detailuser

    private val _allfollowers = MutableLiveData<List<ItemsItem>>()
    val allfollowers: LiveData<List<ItemsItem>> = _allfollowers

    private val _allfollowings = MutableLiveData<List<ItemsItem>>()
    val allfollowings: LiveData<List<ItemsItem>> = _allfollowings

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoadingFollower = MutableLiveData<Boolean>()
    val isLoadingFollower: LiveData<Boolean> = _isLoadingFollower

    private val _isLoadingFollowing = MutableLiveData<Boolean>()
    val isLoadingFollowing: LiveData<Boolean> = _isLoadingFollowing

    private var userlogin: String = ""

    fun setUserLogin(userLogin: String) {
        userlogin = userLogin
        getDetailUser()
        getDetailUserFollowers()
        getDetailUserFollowings()
    }

    internal fun getDetailUser() {
        _isLoading.value = true
        val service = ApiConfig.getApiService().getUserDetail(userlogin)
        service.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(call: Call<DetailUserResponse>, response: Response<DetailUserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _detailuser.value = responseBody!!
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun getDetailUserFollowers() {
        _isLoadingFollower.value = true
        val service = ApiConfig.getApiService().getUserFollowers(userlogin)
        service.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoadingFollower.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _allfollowers.value = responseBody!!
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoadingFollower.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun getDetailUserFollowings() {
        _isLoadingFollowing.value = true
        val service = ApiConfig.getApiService().getUserFollowing(userlogin)
        service.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoadingFollowing.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _allfollowings.value = responseBody!!
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoadingFollowing.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}

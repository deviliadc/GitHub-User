package com.devapps.githubuser.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.devapps.githubuser.R
import com.devapps.githubuser.data.response.ItemsItem
import com.devapps.githubuser.databinding.ActivityMainBinding
import com.devapps.githubuser.ui.detail.DetailUserActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    private var adapter: UserAdapter = UserAdapter(object : UserAdapter.OnItemClickCallback {
        override fun onItemClicked(data: ItemsItem) {
            val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
             intent.putExtra(DetailUserActivity.EXTRA_USER, data.login)
             startActivity(intent)
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSearchView()

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        binding.rvUsers.adapter = adapter

        mainViewModel.listUser.observe(this) { githubUsers ->
            githubUsers?.let { displayUser(it) }
        }

        mainViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        mainViewModel.error.observe(this) { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupSearchView() {
        val searchView = binding.searchView

        searchView.setOnClickListener {
            searchView.isFocusable = true
            searchView.isIconified = false
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    mainViewModel.getData(query)
                    searchView.clearFocus()
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }


    private fun displayUser(user: List<ItemsItem>) {
        adapter.submitList(user)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.rvUsers.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.rvUsers.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }
}

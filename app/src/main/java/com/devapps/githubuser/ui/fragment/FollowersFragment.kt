package com.devapps.githubuser.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.devapps.githubuser.data.response.ItemsItem
import com.devapps.githubuser.databinding.FragmentFollowersBinding
import com.devapps.githubuser.ui.detail.DetailUserActivity
import com.devapps.githubuser.ui.detail.DetailViewModel
import com.devapps.githubuser.ui.main.UserAdapter

class FollowersFragment : Fragment() {
    private lateinit var binding: FragmentFollowersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFollowersBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(context)
        binding.rvFollowers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvFollowers.addItemDecoration(itemDecoration)

        val detailViewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)

        detailViewModel.allfollowers.observe(viewLifecycleOwner, { followersData ->
            showFollowers(followersData)
        })

        detailViewModel.isLoadingFollower.observe(viewLifecycleOwner, { isLoading ->
            showLoading(isLoading)
        })

        return binding.root
    }

    private fun showFollowers(dataUsers: List<ItemsItem>) {
        val adapter = UserAdapter(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                onUserItemClicked(data)
            }
        })
        adapter.submitList(dataUsers)
        binding.rvFollowers.adapter = adapter
    }

    private fun onUserItemClicked(data: ItemsItem) {
        val intent = Intent(activity, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_USER, data.login)
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarFollower.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

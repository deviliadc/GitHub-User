package com.devapps.githubuser.ui.main
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devapps.githubuser.R
import com.devapps.githubuser.data.response.ItemsItem

class UserAdapter(private val onItemClickCallback: OnItemClickCallback) : ListAdapter<ItemsItem, UserAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    override fun getItemCount() = currentList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvUsername: TextView = itemView.findViewById(R.id.tvUsername)
        private val imgProfile: ImageView = itemView.findViewById(R.id.imgProfile)

        fun bind(user: ItemsItem) {
            tvUsername.text = user.login

            if (!user.avatarUrl.isNullOrEmpty()) {
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .centerCrop()
                    .into(imgProfile)
            } else {
                Glide.with(itemView.context)
                    .load(R.drawable.ic_launcher_foreground)
                    .into(imgProfile)
            }

            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(user)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem.login == newItem.login
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}

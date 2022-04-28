package com.yoenas.githubusers.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yoenas.githubusers.core.R
import com.yoenas.githubusers.core.databinding.RowItemUserBinding
import com.yoenas.githubusers.core.domain.model.User
import com.yoenas.githubusers.core.utils.DiffCallback
import com.yoenas.githubusers.core.utils.OnItemClickCallback

class UserAdapter : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    private var listUserResponse = ArrayList<User>()
    private var onItemClicked: OnItemClickCallback? = null

    fun setData(data: List<User>?) {
        if (data == null) return
        val userDiffUtil = DiffCallback(listUserResponse, data)
        val diffUtilResult = DiffUtil.calculateDiff(userDiffUtil)
        listUserResponse.clear()
        listUserResponse.addAll(data)
        diffUtilResult.dispatchUpdatesTo(this)
    }

    class MyViewHolder(val binding: RowItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        RowItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            with(listUserResponse[position]) {
                tvUsername.text = login
                tvUrl.text = htmlUrl

                // set drawable from uri
                Glide.with(imgAvatar.context).load(avatarUrl)
                    .error(R.drawable.ic_broken_image_white).into(imgAvatar)

                cvRowUser.setOnClickListener {
                    onItemClicked?.onItemClicked(this)
                }
            }
        }
    }

    override fun getItemCount() = listUserResponse.size

    fun setOnItemClickCallback(onItemClicked: OnItemClickCallback) {
        this.onItemClicked = onItemClicked
    }
}
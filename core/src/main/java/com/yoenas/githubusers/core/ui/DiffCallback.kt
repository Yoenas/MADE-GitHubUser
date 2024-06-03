package com.yoenas.githubusers.core.ui

import androidx.recyclerview.widget.DiffUtil
import com.yoenas.githubusers.core.domain.model.User

class DiffCallback(private val oldList: List<User>, private val newList: List<User>) :
    DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldData = oldList[oldItemPosition]
        val newData = newList[newItemPosition]
        return oldData.login == newData.login
                && oldData.avatarUrl == newData.avatarUrl
                && oldData.htmlUrl == newData.htmlUrl
                && oldData.company == newData.company
                && oldData.email == newData.email
                && oldData.followers == newData.followers
                && oldData.following == newData.following
                && oldData.location == newData.location
                && oldData.name == newData.name
                && oldData.publicRepos == newData.publicRepos
    }
}
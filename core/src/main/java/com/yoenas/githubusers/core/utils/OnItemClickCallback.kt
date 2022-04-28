package com.yoenas.githubusers.core.utils

import com.yoenas.githubusers.core.domain.model.User

interface OnItemClickCallback {
    fun onItemClicked(userResponse: User)
}
package com.yoenas.githubusers.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class GitHubUsers(
    @field:SerializedName("items")
    val items: ArrayList<UserResponse>
)
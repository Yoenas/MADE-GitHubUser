package com.yoenas.githubusers.core.data.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GitHubUsers(
    @field:SerializedName("items")
    val items: ArrayList<UserResponse>
)
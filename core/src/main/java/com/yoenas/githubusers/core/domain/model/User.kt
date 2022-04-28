package com.yoenas.githubusers.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val login: String,

    val company: String? = null,

    val publicRepos: Int? = null,

    val email: String? = null,

    val followers: Int? = null,

    val avatarUrl: String? = null,

    val htmlUrl: String? = null,

    val following: Int? = null,

    val name: String? = null,

    val location: String? = null
) : Parcelable
package com.yoenas.githubusers.core.utils

import com.yoenas.githubusers.core.domain.model.User

object DummyData {

    fun generateRemoteDataDummy(): List<User> {
        val listUsers = ArrayList<User>()
        listUsers.add(
            User(
                "Yoenas",
                "SMK IDN Boarding School",
                30,
                "yusrilnurhadi63@gmail.com",
                76,
                "https://avatars.githubusercontent.com/u/36739722?v=4",
                "https://github.com/Yoenas",
                19,
                "Yusril Nurhadi AS",
                "Kab. Bogor"
            )
        )
        return listUsers
    }
}
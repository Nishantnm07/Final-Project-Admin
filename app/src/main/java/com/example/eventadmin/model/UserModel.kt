package com.example.eventadmin.model

data class UserModel(
    val name:String? = null,
    val nameOfShup:String? = null,
    val email:String? = null,
    val password:String? = null,
    var address:String?= null,
    var phone:String? = null
)
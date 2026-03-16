package com.elpablo.motogram.domain.model

data class User(
    var id: Long? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var photoURL: String? = null
)
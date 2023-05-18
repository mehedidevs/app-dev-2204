package com.ju.simplequiz2204.class29

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var address: String= "",
    var userId: String= "",
    var profileImgUrl: String= "",
    var email: String="",
    var name: String="",
    var password: String="",
    var phone: String=""
) : Parcelable




package com.s.data.remote.request

import com.google.gson.annotations.SerializedName

/**
 * Created by victor on 12/26/18
 */
data class UpdatePasswordRequest(val password: String, @SerializedName("password_confirmation") val passwordConfirmation: String)
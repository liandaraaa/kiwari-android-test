package com.android.simplechatapp.depth.service.model

import java.lang.RuntimeException

data class ResponseException(val error:ErrorResponse):RuntimeException()
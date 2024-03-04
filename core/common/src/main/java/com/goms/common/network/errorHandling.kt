package com.goms.common.network

import com.goms.common.exception.*

fun Throwable.errorHandling(
    badRequestAction: () -> Unit = {},
    unauthorizedAction: () -> Unit = {},
    forbiddenAction: () -> Unit = {},
    notFoundAction: () -> Unit = {},
    notAcceptableAction: () -> Unit = {},
    timeOutAction: () -> Unit = {},
    conflictAction: () -> Unit = {},
    serverAction: () -> Unit = {},
    otherHttpAction: () -> Unit = {},
    unknownAction: () -> Unit = {},
) {
    when (this) {
        is BadRequestException -> badRequestAction()
        is UnauthorizedException, is TokenExpirationException -> unauthorizedAction()
        is ForBiddenException -> forbiddenAction()
        is NotFoundException -> notFoundAction()
        is NotAcceptableException -> notAcceptableAction()
        is TimeOutException -> timeOutAction()
        is ConflictException -> conflictAction()
        is ServerException -> serverAction()
        is OtherHttpException -> otherHttpAction()
        else -> unknownAction()
    }
}
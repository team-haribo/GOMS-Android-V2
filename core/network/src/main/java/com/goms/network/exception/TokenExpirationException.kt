package com.goms.network.exception

import java.io.IOException

class TokenExpirationException : IOException() {
    override val message: String
        get() = "토큰이 만료되었습니다. 다시 로그인 해주세요"
}
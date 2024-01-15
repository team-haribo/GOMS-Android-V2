package com.goms.common.exception

class NoInternetException : RuntimeException() {
    override val message: String
        get() = "네트워크가 불안정합니다, 데이터나 와이파이 연결 상태를 확인해주세요."
}
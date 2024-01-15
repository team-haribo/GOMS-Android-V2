package com.goms.common

sealed class Event<out T>(
    val data: T? = null
) {

    object Loading : Event<Nothing>()

    // 20X: 성공
    class Success<T>(data: T? = null) : Event<T>(data = data)

    // 400: 올바르지 않은 요청
    object BadRequest : Event<Nothing>()

    // 401: 비인증 상태
    object Unauthorized : Event<Nothing>()

    // 403: 권한이 없음
    object ForBidden : Event<Nothing>()

    // 404: 요청한 리소스를 찾을 수 없음
    object NotFound : Event<Nothing>()

    // 406: 클라이언트가 허용되지 않는 규격을 요청
    object NotAcceptable : Event<Nothing>()

    // 408: 요청시간초과
    object TimeOut : Event<Nothing>()

    // 409: 충돌발생
    object Conflict : Event<Nothing>()

    // 50X: 서버에러
    object Server : Event<Nothing>()

    // 예상하지 못한 에러
    object UnKnown : Event<Nothing>()
}
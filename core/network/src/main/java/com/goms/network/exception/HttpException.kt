package com.goms.network.exception

// 400: 올바르지 않은 요청
class BadRequestException(
    override val message: String?
) : RuntimeException()


// 401: 비인증 상태
class UnauthorizedException(
    override val message: String?
) : RuntimeException()


// 403: 권한이 없음
class ForBiddenException(
    override val message: String?
) : RuntimeException()


// 404: 요청한 리소스를 찾을 수 없음
class NotFoundException(
    override val message: String?
) : RuntimeException()


// 406: 클라이언트가 허용되지 않는 규격을 요청
class NotAcceptableException(
    override val message: String?
) : RuntimeException()

// 408: 요청시간초과
class TimeOutException(
    override val message: String?
) : RuntimeException()


// 409: 충돌발생
class ConflictException(
    override val message: String?
) : RuntimeException()


// 50X: 서버에러
class ServerException(
    override val message: String?
) : RuntimeException()


// 정의되지 않은 HTTP 상태 코드나 사용자 정의 상태 코드
class OtherHttpException(
    val code: Int,
    override val message: String?
) : RuntimeException()

// 예상하지 못한 에러
class UnKnownException(
    override val message: String?
) : RuntimeException()
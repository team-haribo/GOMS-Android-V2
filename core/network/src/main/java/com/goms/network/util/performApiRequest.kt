package com.goms.network.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

/**
 * API 요청을 수행하고 결과를 Flow로 반환하는 제네릭 함수입니다.
 *
 * @param apiCall API 요청을 수행하는 일시 중단 람다 함수
 * @return API 요청 결과를 방출하는 Flow
 */
internal inline fun <reified T> performApiRequest(crossinline apiCall: suspend () -> T): Flow<T> = flow {
    emit(
        GomsApiHandler<T>()
            .httpRequest { apiCall() }
            .sendRequest()
    )
}.flowOn(Dispatchers.IO)
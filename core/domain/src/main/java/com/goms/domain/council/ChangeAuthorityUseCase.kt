package com.goms.domain.council

import com.goms.data.repository.council.CouncilRepository
import com.goms.model.request.council.AuthorityRequestModel
import javax.inject.Inject

class ChangeAuthorityUseCase @Inject constructor(
    private val councilRepository: CouncilRepository
) {
    operator fun invoke(body: AuthorityRequestModel) = runCatching {
        councilRepository.changeAuthority(body = body)
    }
}
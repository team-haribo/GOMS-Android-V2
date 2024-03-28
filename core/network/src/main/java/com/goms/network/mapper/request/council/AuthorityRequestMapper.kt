package com.goms.network.mapper.request.council

import com.goms.model.request.council.AuthorityRequestModel
import com.goms.network.dto.request.council.AuthorityRequest

fun AuthorityRequestModel.toDto(): AuthorityRequest =
    AuthorityRequest(
        accountIdx = this.accountIdx,
        authority = this.authority
    )
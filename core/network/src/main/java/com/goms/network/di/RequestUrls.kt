package com.goms.network.di

internal object RequestUrls {
    object PATH {
        const val accountIdx = "accountIdx"
        const val deviceToken = "deviceToken"
        const val outingUUID = "outingUUID"
        const val password = "password"
    }

    object ACCOUNT {
        private const val path = "api/v2/account"

        const val account = path
        const val profile = "$path/profile"
        const val image = "$path/image"
        const val newPassword = "$path/new-password"
        const val changePassword = "$path/change-password"
        const val withdraw = "$path/withdraw/{${PATH.password}}"
    }

    object AUTH {
        private const val path = "api/v2/auth"

        const val auth = "$path/"
        const val signUp = "$path/signup"
        const val signIn = "$path/signin"
        const val send = "$path/email/send"
        const val verify = "$path/email/verify"
    }

    object COUNCIL {
        private const val path = "api/v2/student-council"

        const val council = path
        const val accounts = "$path/accounts"
        const val authority = "$path/authority"
        const val blackList = "$path/black-list/{${PATH.accountIdx}}"
        const val search = "$path/search"
        const val outing = "$path/outing"
        const val deleteOuting = "$path/outing/{${PATH.accountIdx}}"
        const val late = "$path/late"
    }

    object LATE {
        private const val path = "api/v2/late"

        const val late = path
        const val rank = "$path/rank"
    }

    object NOTIFICATION {
        private const val path = "api/v2/notification"

        const val notification = path
        const val saveToken = "$path/token/{${PATH.deviceToken}}"
        const val deleteToken = "$path/token"
    }

    object OUTING {
        private const val path = "api/v2/outing"

        const val outing = path
        const val outingUUID = "$path/{${PATH.outingUUID}}"
        const val count = "$path/count"
        const val search = "$path/search"
    }
}
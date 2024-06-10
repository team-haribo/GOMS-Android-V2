package com.goms.datastore.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.goms.datastore.SettingInfo
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class SettingSerializer @Inject constructor() : Serializer<SettingInfo> {
    override val defaultValue: SettingInfo = SettingInfo.getDefaultInstance()
    override suspend fun readFrom(input: InputStream): SettingInfo =
        try {
            SettingInfo.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto", e)
        }

    override suspend fun writeTo(t: SettingInfo, output: OutputStream) {
        t.writeTo(output)
    }
}
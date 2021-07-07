package com.yujie.utilmodule.http

import retrofit.converter.guava.GuavaOptionalConverterFactory
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.jaxb.JaxbConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.protobuf.ProtoConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.converter.wire.WireConverterFactory

enum class ConverterFactory(val factory: Converter.Factory) {
    GsonFactory(GsonConverterFactory.create()),
    GuavaFactory(GuavaOptionalConverterFactory.create()),
    JacksonFactory(JacksonConverterFactory.create()),
    JaxbFactory(JaxbConverterFactory.create()),
    MoshiFactory(MoshiConverterFactory.create()),
    ProtobufFactory(ProtoConverterFactory.create()),
    ScalarsFactory(ScalarsConverterFactory.create()),
    SimplexmlFactory(SimpleXmlConverterFactory.create()),
    WireFactory(WireConverterFactory.create()),
}
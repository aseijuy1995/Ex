package com.yujie.core_lib.http

import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory

enum class ConverterFactory(val factory: Converter.Factory) {
		GsonFactory(GsonConverterFactory.create()),
//		GuavaFactory(GuavaOptionalConverterFactory.create()),
//		JacksonFactory(JacksonConverterFactory.create()),
//		JaxbFactory(JaxbConverterFactory.create()),
//		MoshiFactory(MoshiConverterFactory.create()),
//
//		//    ProtobufFactory(ProtoConverterFactory.create()),
////    ScalarsFactory(ScalarsConverterFactory.create()),
//		SimplexmlFactory(SimpleXmlConverterFactory.create()),
//		WireFactory(WireConverterFactory.create()),
}
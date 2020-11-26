package com.example.websockerext.conn;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J%\u0010\u0002\u001a\u00020\u00032\b\b\u0003\u0010\u0004\u001a\u00020\u00052\b\b\u0003\u0010\u0006\u001a\u00020\u0005H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0007J\"\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\t2\b\b\u0003\u0010\u0004\u001a\u00020\u00052\b\b\u0003\u0010\u0006\u001a\u00020\u0005H\'\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\n"}, d2 = {"Lcom/example/websockerext/conn/IApiService;", "", "postAppResult", "Lcom/example/websockerext/AppResult;", "path", "", "cmd", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "postAppResultDef", "Lkotlinx/coroutines/Deferred;", "app_debug"})
public abstract interface IApiService {
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object postAppResult(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Path(value = "path")
    java.lang.String path, @org.jetbrains.annotations.NotNull()
    @retrofit2.http.Field(value = "cmd")
    java.lang.String cmd, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.websockerext.AppResult> p2);
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.POST(value = "ct/{path}")
    @retrofit2.http.FormUrlEncoded()
    public abstract kotlinx.coroutines.Deferred<com.example.websockerext.AppResult> postAppResultDef(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Path(value = "path")
    java.lang.String path, @org.jetbrains.annotations.NotNull()
    @retrofit2.http.Field(value = "cmd")
    java.lang.String cmd);
    
    @kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 3)
    public final class DefaultImpls {
    }
}
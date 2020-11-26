package com.example.websockerext.pattern;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\u0004\b\u0001\u0010\u00032\u00020\u0002B\u0019\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00000\u0005\u00a2\u0006\u0002\u0010\u0006J\u0013\u0010\t\u001a\u00028\u00002\u0006\u0010\n\u001a\u00028\u0001\u00a2\u0006\u0002\u0010\u000bR\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00000\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u0004\u0018\u00018\u0000X\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\b\u00a8\u0006\f"}, d2 = {"Lcom/example/websockerext/pattern/SingletonParamsHolder;", "T", "", "A", "creator", "Lkotlin/Function1;", "(Lkotlin/jvm/functions/Function1;)V", "sInstance", "Ljava/lang/Object;", "getInstance", "arg", "(Ljava/lang/Object;)Ljava/lang/Object;", "app_debug"})
public class SingletonParamsHolder<T extends java.lang.Object, A extends java.lang.Object> {
    private T sInstance;
    private final kotlin.jvm.functions.Function1<A, T> creator = null;
    
    @org.jetbrains.annotations.NotNull()
    public final T getInstance(A arg) {
        return null;
    }
    
    public SingletonParamsHolder(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super A, ? extends T> creator) {
        super();
    }
}
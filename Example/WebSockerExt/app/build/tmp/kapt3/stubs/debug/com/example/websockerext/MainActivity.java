package com.example.websockerext;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014J\u0006\u0010\u0015\u001a\u00020\u0014J\u0012\u0010\u0016\u001a\u00020\u000f2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0014J\u000e\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/example/websockerext/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/example/websockerext/databinding/ActivityMainBinding;", "viewModel", "Lcom/example/websockerext/MainViewModel;", "getViewModel", "()Lcom/example/websockerext/MainViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "wsClient", "Lokhttp3/WebSocket;", "wsServer", "clientClick", "", "v", "Landroid/view/View;", "initClient", "server", "Lokhttp3/mockwebserver/MockWebServer;", "mockServer", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "serverClick", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    private okhttp3.WebSocket wsClient;
    private okhttp3.WebSocket wsServer;
    private com.example.websockerext.databinding.ActivityMainBinding binding;
    private final kotlin.Lazy viewModel$delegate = null;
    private java.util.HashMap _$_findViewCache;
    
    private final com.example.websockerext.MainViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    public final void serverClick(@org.jetbrains.annotations.NotNull()
    android.view.View v) {
    }
    
    public final void clientClick(@org.jetbrains.annotations.NotNull()
    android.view.View v) {
    }
    
    public final void initClient(@org.jetbrains.annotations.NotNull()
    okhttp3.mockwebserver.MockWebServer server) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final okhttp3.mockwebserver.MockWebServer mockServer() {
        return null;
    }
    
    public MainActivity() {
        super();
    }
}
package com.yujie.utilmodule.http

import okhttp3.Callback
import okhttp3.WebSocketListener

//--------------------------------------------------------------------------------
//Get
fun OkHttpUtil.getSync(url: String, params: Map<String, String>) = sync(get(url, params)).body?.string()

fun OkHttpUtil.getAsync(url: String, params: Map<String, String>, callback: Callback) = async(get(url, params), callback)

//--------------------------------------------------------------------------------
//Head
fun OkHttpUtil.headSync(url: String, params: Map<String, String>) = sync(head(url, params)).headers.toString()

fun OkHttpUtil.headAsync(url: String, params: Map<String, String>, callback: Callback) = async(head(url, params), callback)

//--------------------------------------------------------------------------------
//Post
fun OkHttpUtil.postFromDataSync(url: String, params: Map<String, String>) = sync(post(url, fromDataToBody(params))).body?.string()

fun OkHttpUtil.postJsonSync(url: String, json: String) = sync(post(url, jsonToBody(json))).body?.string()

fun OkHttpUtil.postFromDataAsync(url: String, params: Map<String, String>, callback: Callback) = async(post(url, fromDataToBody(params)), callback)

fun OkHttpUtil.postJsonAsync(url: String, json: String, callback: Callback) = async(post(url, jsonToBody(json)), callback)

//--------------------------------------------------------------------------------
//Delete
fun OkHttpUtil.deleteFromDataSync(url: String, params: Map<String, String>) = sync(delete(url, fromDataToBody(params))).body?.string()

fun OkHttpUtil.deleteJsonSync(url: String, json: String) = sync(delete(url, jsonToBody(json))).body?.string()

fun OkHttpUtil.deleteFromDataAsync(url: String, params: Map<String, String>, callback: Callback) = async(delete(url, fromDataToBody(params)), callback)

fun OkHttpUtil.deleteJsonAsync(url: String, json: String, callback: Callback) = async(delete(url, jsonToBody(json)), callback)

//--------------------------------------------------------------------------------
//Put
fun OkHttpUtil.putFromDataSync(url: String, params: Map<String, String>) = sync(put(url, fromDataToBody(params))).body?.string()

fun OkHttpUtil.putJsonSync(url: String, json: String) = sync(put(url, jsonToBody(json))).body?.string()

fun OkHttpUtil.putFromDataAsync(url: String, params: Map<String, String>, callback: Callback) = async(put(url, fromDataToBody(params)), callback)

fun OkHttpUtil.putJsonAsync(url: String, json: String, callback: Callback) = async(put(url, jsonToBody(json)), callback)

//--------------------------------------------------------------------------------
//Patch
fun OkHttpUtil.patchFromDataSync(url: String, params: Map<String, String>) = sync(patch(url, fromDataToBody(params))).body?.string()

fun OkHttpUtil.patchJsonSync(url: String, json: String) = sync(patch(url, jsonToBody(json))).body?.string()

fun OkHttpUtil.patchFromDataAsync(url: String, params: Map<String, String>, callback: Callback) = async(patch(url, fromDataToBody(params)), callback)

fun OkHttpUtil.patchJsonAsync(url: String, json: String, callback: Callback) = async(patch(url, jsonToBody(json)), callback)

//--------------------------------------------------------------------------------
//WebSocket
fun OkHttpUtil.createWebSocket(url: String, webSocketListener: WebSocketListener) = webSocket(request(url), webSocketListener)
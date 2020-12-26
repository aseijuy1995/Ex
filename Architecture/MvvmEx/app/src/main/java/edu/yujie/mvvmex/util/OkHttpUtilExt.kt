package edu.yujie.mvvmex.util

import edu.yujie.mvvmex.util.OkHttpUtil
import okhttp3.Callback
import okhttp3.WebSocketListener

//--------------------------------------------------------------------------------
//Get
fun OkHttpUtil.syncGet(url: String, params: Map<String, String>) = sync(get(url, params)).body?.string()

fun OkHttpUtil.asyncGet(url: String, params: Map<String, String>, callback: Callback) = async(get(url, params), callback)

//--------------------------------------------------------------------------------
//Head
fun OkHttpUtil.syncHead(url: String, params: Map<String, String>) = sync(head(url, params)).headers.toString()

fun OkHttpUtil.asyncHead(url: String, params: Map<String, String>, callback: Callback) = async(head(url, params), callback)

//--------------------------------------------------------------------------------
//Post
fun OkHttpUtil.syncPostFromData(url: String, params: Map<String, String>) = sync(post(url, fromDataToBody(params))).body?.string()

fun OkHttpUtil.syncPostJson(url: String, json: String) = sync(post(url, jsonToBody(json))).body?.string()

fun OkHttpUtil.asyncPostFromData(url: String, params: Map<String, String>, callback: Callback) = async(post(url, fromDataToBody(params)), callback)

fun OkHttpUtil.asyncPostJson(url: String, json: String, callback: Callback) = async(post(url, jsonToBody(json)), callback)

//--------------------------------------------------------------------------------
//Delete
fun OkHttpUtil.syncDeleteFromData(url: String, params: Map<String, String>) = sync(delete(url, fromDataToBody(params))).body?.string()

fun OkHttpUtil.syncDeleteJson(url: String, json: String) = sync(delete(url, jsonToBody(json))).body?.string()

fun OkHttpUtil.asyncDeleteFromData(url: String, params: Map<String, String>, callback: Callback) = async(delete(url, fromDataToBody(params)), callback)

fun OkHttpUtil.asyncDeleteJson(url: String, json: String, callback: Callback) = async(delete(url, jsonToBody(json)), callback)

//--------------------------------------------------------------------------------
//Put
fun OkHttpUtil.syncPutFromData(url: String, params: Map<String, String>) = sync(put(url, fromDataToBody(params))).body?.string()

fun OkHttpUtil.syncPutJson(url: String, json: String) = sync(put(url, jsonToBody(json))).body?.string()

fun OkHttpUtil.asyncPutFromData(url: String, params: Map<String, String>, callback: Callback) = async(put(url, fromDataToBody(params)), callback)

fun OkHttpUtil.asyncPutJson(url: String, json: String, callback: Callback) = async(put(url, jsonToBody(json)), callback)

//--------------------------------------------------------------------------------
//Patch
fun OkHttpUtil.syncPatchFromData(url: String, params: Map<String, String>) = sync(patch(url, fromDataToBody(params))).body?.string()

fun OkHttpUtil.syncPatchJson(url: String, json: String) = sync(patch(url, jsonToBody(json))).body?.string()

fun OkHttpUtil.asyncPatchFromData(url: String, params: Map<String, String>, callback: Callback) = async(patch(url, fromDataToBody(params)), callback)

fun OkHttpUtil.asyncPatchJson(url: String, json: String, callback: Callback) = async(patch(url, jsonToBody(json)), callback)

//--------------------------------------------------------------------------------
//WebSocket
fun OkHttpUtil.createWebSocket(url: String, webSocketListener: WebSocketListener) = webSocket(request(url), webSocketListener)
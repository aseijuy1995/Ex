package com.yujie.utilmodule.ext

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import jp.wasabeef.glide.transformations.BlurTransformation

private fun Bitmap.rsBlur(context: Context, radius: Int): Bitmap {
		//(1)
		val renderScript = RenderScript.create(context)
		// Allocate memory for Renderscript to work with
		//(2)
		val input = Allocation.createFromBitmap(renderScript, this)
		val output = Allocation.createTyped(renderScript, input.type)
		//(3)
		// Load up an instance of the specific script that we want to use.
		val scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
		//(4)
		scriptIntrinsicBlur.setInput(input)
		//(5)
		// Set the blur radius
		scriptIntrinsicBlur.setRadius(radius.toFloat())
		//(6)
		// Start the ScriptIntrinisicBlur
		scriptIntrinsicBlur.forEach(output)
		//(7)
		// Copy the output to the blurred bitmap
		output.copyTo(this)
		//(8)
		renderScript.destroy()
		return this
}
package edu.yujie.lifecyclesex

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import edu.yujie.lifecyclesex.common.MyDefaultObserver

class MainActivity : Activity() ,LifecycleOwner{
    private lateinit var lifecycleRegistry: LifecycleRegistry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleRegistry = LifecycleRegistry(this)
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
        MyObserver(this)
        MyDefaultObserver(this)
    }

    override fun onStart() {
        super.onStart()
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
    }

    override fun onResume() {
        super.onResume()
        lifecycleRegistry.currentState = Lifecycle.State.RESUMED
    }

    override fun onPause() {
        super.onPause()
        lifecycleRegistry.currentState = Lifecycle.State.RESUMED
    }

    override fun onStop() {
        super.onStop()
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun getLifecycle(): Lifecycle  = lifecycleRegistry
}
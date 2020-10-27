package edu.yujie.datastoreext

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    companion object {
        val EXAMPLE_COUNTER = preferencesKey<Int>("example_counter")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dataStore = createDataStore(name = "settings")
        //read
        read(dataStore)
        //write
        write(dataStore)
    }

    private fun read(dataStore: DataStore<Preferences>) {
        val exampleCounterFlow = dataStore.data.map { it[EXAMPLE_COUNTER] ?: 0 }

        lifecycleScope.launch {
            exampleCounterFlow.collect {
                println("$TAG:exampleCounterFlow = $it")
            }
        }
    }

    private fun write(dataStore: DataStore<Preferences>) {
        lifecycleScope.launch {
            dataStore.edit {
                it[EXAMPLE_COUNTER] = (it[EXAMPLE_COUNTER] ?: 0) + 1
            }
        }
    }
}
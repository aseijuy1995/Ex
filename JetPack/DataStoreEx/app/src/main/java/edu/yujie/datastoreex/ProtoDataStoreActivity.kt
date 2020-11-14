package edu.yujie.datastoreex

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.DataStore
import androidx.datastore.createDataStore
import androidx.lifecycle.lifecycleScope
import com.example.application.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class ProtoDataStoreActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    private val settingDataStore: DataStore<Settings> by lazy {
        applicationContext.createDataStore(
            fileName = "setting.proto",
            serializer = SettingsSerializer
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proto_data_store)
        //read
        read()

        //read object
        lifecycleScope.launch(Dispatchers.Main) {
            findViewById<TextView>(R.id.textView).text = readObject()
        }

        //write
        write()
    }

    private fun read() {
        //operator map get property
        lifecycleScope.launch {
            val nameFlow = settingDataStore.data
                .map {
                    it.name
                }
                .catch {
                    if (this is IOException)
                        emit(Settings.getDefaultInstance().name)
                    else
                        this
                }
            nameFlow.collect { println("$TAG:name = $it") }
        }
    }

    private suspend fun readObject() =
        //read object
        withContext(Dispatchers.IO) {
            delay(3000L)
            val settings = settingDataStore.data.first()
            println("$TAG:name = ${settings.name}")
            println("$TAG:description = ${settings.description}")
            "name:${settings.name}\ndescription:${settings.description}"
        }


    private fun write() {
        lifecycleScope.launch(Dispatchers.IO) {
            settingDataStore.updateData {
                it.toBuilder()
                    .setName("Kotlin")
                    .setDescription("Kotlin Description")
                    .build()
            }
        }
    }
}
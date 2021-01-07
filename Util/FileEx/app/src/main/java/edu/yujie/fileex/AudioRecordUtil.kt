package edu.yujie.fileex

import android.media.MediaRecorder
import android.os.Environment
import java.io.File

//https://codertw.com/%E7%A8%8B%E5%BC%8F%E8%AA%9E%E8%A8%80/524572/
class AudioRecordUtil {
    private var mMediaRecorder: MediaRecorder? = null
    private var startRecorderTime: Long? = null

    fun doStart(): Boolean {
        try {
            mMediaRecorder = MediaRecorder()
            //建立錄音檔案
            val mRecorderFile = File("${Environment.getExternalStorageDirectory().absolutePath}/recorderdemo/${System.currentTimeMillis()}.m4a");
            println("mRecorderFile:${mRecorderFile}")
            if (!mRecorderFile.parentFile.exists()) {
                mRecorderFile.parentFile.mkdirs();
            }
            mRecorderFile.createNewFile();

            mMediaRecorder?.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)//採集
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)//MP4格式
                setAudioSamplingRate(44100)//所有android系統都支援的適中取樣的頻率
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)//通用的AAC編碼格式
                setAudioEncodingBitRate(96000)//設定音質頻率
                setOutputFile(mRecorderFile.absolutePath)//設定檔案錄音的位置
                prepare()
            }?.start()//開始錄音

            startRecorderTime = System.currentTimeMillis()
        } catch (e: Exception) {
            return false
        }
        return true//記錄開始錄音時間，用於統計時長，小於3秒中，錄音不傳送
    }

    fun doStop(): Boolean {
        try {
            mMediaRecorder?.stop();
            val stopRecorderTime = System.currentTimeMillis();
            val second = (stopRecorderTime - startRecorderTime!!) / 1000;//按住時間小於3秒鐘，算作錄取失敗，不進行傳送
            if (second < 3) return false;

            println("Success!")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return true;
    }
}
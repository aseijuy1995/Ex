package edu.yujie.navigationex.ex

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import java.io.Serializable

@Keep class ParcelableArgs() :Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<ParcelableArgs> {
        override fun createFromParcel(parcel: Parcel): ParcelableArgs {
            return ParcelableArgs(parcel)
        }

        override fun newArray(size: Int): Array<ParcelableArgs?> {
            return arrayOfNulls(size)
        }
    }
}

@Keep class SerializableArg :Serializable

@Keep enum class EnumArgs
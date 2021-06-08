package com.example.nagwatask.core.utils

import android.os.Parcel

import android.os.Parcelable


class Download : Parcelable {
    var progress = 0
    var currentFileSize: Long = 0
    var totalFileSize: Long = 0

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(progress)
        dest.writeLong(currentFileSize)
        dest.writeLong(totalFileSize)
    }

    constructor() {}
    protected constructor(`in`: Parcel) {
        progress = `in`.readInt()
        currentFileSize = `in`.readLong()
        totalFileSize = `in`.readLong()
    }

    companion object {

        @JvmField val CREATOR: Parcelable.Creator<Download?> = object : Parcelable.Creator<Download?> {
            override fun createFromParcel(source: Parcel): Download? {
                return Download(source)
            }

            override fun newArray(size: Int): Array<Download?> {
                return arrayOfNulls(size)
            }
        }
    }
}
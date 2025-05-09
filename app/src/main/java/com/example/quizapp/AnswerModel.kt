package com.example.quizapp

import android.os.Parcel
import android.os.Parcelable

data class AnswerModel(
    val userAnswer: String?,
    val correctAnswer: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userAnswer)
        parcel.writeString(correctAnswer)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<AnswerModel> {
        override fun createFromParcel(parcel: Parcel): AnswerModel {
            return AnswerModel(parcel)
        }

        override fun newArray(size: Int): Array<AnswerModel?> {
            return arrayOfNulls(size)
        }
    }
}

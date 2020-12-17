package com.example.myphamacy

import com.google.gson.annotations.SerializedName
import retrofit2.http.Url

data class Pill (val item_name:String , val entp_name:String, val chart:String, val drug_shape:String,
            val drug_color:String ,val drug_effect:String, val fom_code_name:String,val image_url:Int) {
}

package com.example.myphamacy

import com.google.gson.annotations.SerializedName
import okhttp3.Address
import okhttp3.Response
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import retrofit2.http.Field


@Root(name="response")
class SearchResponseWrapper @JvmOverloads constructor(
    @field:Element(name="header")
    var header:GetHeader? =null,
    @field:Element(name="body")
    var body:SearchResponse? = null

)
@Root(name="header")
class GetHeader @JvmOverloads constructor(
    @field:Element(name="resultCode")
    var resultCode:String ="",
    @field:Element(name="resultMsg")
    var resultMsg:String = ""
)

@Root(name= "body")
class SearchResponse @JvmOverloads constructor(
    @field:Element(name="items")
    var items:ResponseItme? =null,
    @field:Element(name="numOfRows")
    var numOfRows:String? =null,
    @field:Element(name="pageNo")
    var pageNo:String? = null,
    @field:Element(name = "totalCount")
    var totalCount:String? = null
)
@Root(name="items")
class ResponseItme @JvmOverloads constructor(
    @field:ElementList(inline = true)
    var item:List<Item>?=null
)
@Root(name="item")
class Item @JvmOverloads constructor(
    @field: Element(name = "cnt")
    var cnt:String ="",
    @field: Element(name = "distance")
    var distance:String ="",
    @field: Element(name = "dutyAddr")
    var dutyAddr:String ="",
    @field: Element(name = "dutyDiv")
    var dutyDiv:String ="",
    @field: Element(name = "dutyDivName")
    var dutyDivName:String ="",
    @field: Element(name = "dutyFax",required = false)
    var dutyFax:String ="",

    @field: Element(name = "dutyName")
    var dutyName:String= "",
    @field: Element(name = "dutyTel1")
    var dutyTel1:String = "",

    @field: Element(name = "endTime")
    var endTime:String ="",

    @field: Element(name = "hpid")
    var hpid:String ="",
    @field: Element(name = "latitude")
    var latitude:String ="",
    @field: Element(name = "longitude")
    var longitude:String ="",
    @field: Element(name = "rnum")
    var rnum:String ="",
    @field: Element(name = "startTime")
    var startTime:String =""


    )
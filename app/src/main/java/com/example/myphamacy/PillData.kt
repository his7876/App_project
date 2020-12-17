package com.example.myphamacy

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


@Root(name="response")
class SearchResponseWrapperPill @JvmOverloads constructor(
    @field:Element(name="header")
    var header:GetHeaderPill? =null,
    @field:Element(name="body")
    var body:SearchResponsePill? = null

)
@Root(name="header")
class GetHeaderPill @JvmOverloads constructor(
    @field:Element(name="resultCode")
    var resultCode:String ="",
    @field:Element(name="resultMsg")
    var resultMsg:String = ""
)

@Root(name= "body")
class SearchResponsePill @JvmOverloads constructor(

    @field:Element(name="numOfRows")
    var numOfRows:String? =null,
    @field:Element(name="pageNo")
    var pageNo:String? = null,
    @field:Element(name = "totalCount")
    var totalCount:String? = null,
    @field:Element(name="items")
    var items:ResponseItmePill? =null
)

@Root(name="items")
class ResponseItmePill @JvmOverloads constructor(
    @field:ElementList(inline = true)
    var item:List<ItemList>?=null
)
@Root(name="item")
class ItemList @JvmOverloads constructor(
    @field: Element(name = "ITEM_SEQ",required = false)
    var ITEM_SEQ:String ="",
    @field: Element(name = "ITEM_NAME",required = false)
    var ITEM_NAME:String ="",
    @field: Element(name = "ENTP_SEQ",required = false)
    var ENTP_SEQ:String ="",
    @field: Element(name = "ENTP_NAME",required = false)
    var ENTP_NAME:String ="",
    @field: Element(name = "CHART",required = false)
    var CHART:String ="",
    @field: Element(name = "ITEM_IMAGE",required = false)
    var ITEM_IMAGE:String ="",

    @field: Element(name = "PRINT_FRONT",required = false)
    var PRINT_FRONT:String= "",
    @field: Element(name = "PRINT_BACK",required = false)
    var PRINT_BACK:String = "",

    @field: Element(name = "DRUG_SHAPE",required = false)
    var DRUG_SHAPE:String ="",

    @field: Element(name = "COLOR_CLASS1",required = false)
    var COLOR_CLASS1:String ="",
    @field: Element(name = "COLOR_CLASS2",required = false)
    var COLOR_CLASS2:String ="",
    @field: Element(name = "LINE_FRONT",required = false)
    var LINE_FRONT:String ="",
    @field: Element(name = "LINE_BACK",required = false)
    var LINE_BACK:String ="",
    @field: Element(name = "LENG_LONG",required = false)
    var LENG_LONG:String ="",

    @field: Element(name = "LENG_SHORT",required = false)
    var LENG_SHORT:String ="",
    @field: Element(name = "THICK",required = false)
    var THICK:String ="",
    @field: Element(name = "IMG_REGIST_TS",required = false)
    var IMG_REGIST_TS:String ="",
    @field: Element(name = "CLASS_NO",required = false)
    var CLASS_NO:String ="",
    @field: Element(name = "CLASS_NAME",required = false)
    var CLASS_NAME:String ="",
    @field: Element(name = "ETC_OTC_NAME",required = false)
    var ETC_OTC_NAME:String ="",
    @field: Element(name = "ITEM_PERMIT_DATE",required = false)
    var ITEM_PERMIT_DATE:String ="",
    @field: Element(name = "FORM_CODE_NAME",required = false)
    var FORM_CODE_NAME:String ="",
    @field: Element(name = "MARK_CODE_FRONT_ANAL",required = false)
    var MARK_CODE_FRONT_ANAL:String ="",
    @field: Element(name = "MARK_CODE_BACK_ANAL",required = false)
    var MARK_CODE_BACK_ANAL:String ="",

    @field: Element(name = "MARK_CODE_FRONT_IMG",required = false)
    var MARK_CODE_FRONT_IMG:String ="",
    @field: Element(name = "MARK_CODE_BACK_IMG",required = false)
    var MARK_CODE_BACK_IMG:String ="",
    @field: Element(name = "ITEM_ENG_NAME",required = false)
    var ITEM_ENG_NAME:String ="",
    @field: Element(name = "CHANGE_DATE",required = false)
    var CHANGE_DATE:String ="",
    @field: Element(name = "MARK_CODE_FRONT",required = false)
    var MARK_CODE_FRONT:String ="",
    @field: Element(name = "MARK_CODE_BACK",required = false)
    var MARK_CODE_BACK:String ="",
    @field: Element(name = "EDI_CODE",required = false)
    var EDI_CODE:String =""


    )
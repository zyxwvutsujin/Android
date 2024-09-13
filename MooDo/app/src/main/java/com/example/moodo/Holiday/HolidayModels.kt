package com.example.moodo.Holiday

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "response", strict = false)
data class HolidayResponse(
    @field:Element(name = "header", required = false)
    var header: HolidayHeader? = null,

    @field:Element(name = "body", required = false)
    var body: HolidayBodyItems? = null
)

@Root(name = "header", strict = false)
data class HolidayHeader(
    @field:Element(name = "resultCode", required = false)
    var resultCode: String? = null,

    @field:Element(name = "resultMsg", required = false)
    var resultMsg: String? = null
)

@Root(name = "body", strict = false)
data class HolidayBodyItems(
    @field:Element(name = "items", required = false)
    var items: HolidayItems? = null
)

@Root(name = "items", strict = false)
data class HolidayItems(
    @field:ElementList(name = "item", inline = true, required = false)
    var item: List<HolidayItem>? = null
)

@Root(name = "item", strict = false)
data class HolidayItem(
    @field:Element(name = "dateKind", required = false)
    var dateKind: String? = null,

    @field:Element(name = "dateName", required = false)
    var dateName: String? = null,

    @field:Element(name = "isHoliday", required = false)
    var isHoliday: String? = null,

    @field:Element(name = "locdate", required = false)
    var locdate: String? = null,

    @field:Element(name = "seq", required = false)
    var seq: Int? = null
)

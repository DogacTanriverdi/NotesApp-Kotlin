package com.dogactnrvrdi.notesapp.common

sealed class OrderType {
    data object Ascending : OrderType()
    data object Descending : OrderType()
}
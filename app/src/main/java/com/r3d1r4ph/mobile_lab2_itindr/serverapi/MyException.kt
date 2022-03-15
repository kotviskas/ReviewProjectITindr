package com.r3d1r4ph.mobile_lab2_itindr.serverapi

import java.lang.Exception

class MyException : Exception() {

    override val message: String?
        get() = super.message
}
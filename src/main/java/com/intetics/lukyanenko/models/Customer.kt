package com.intetics.lukyanenko.models

class Customer() : Common() {
    var id: Int? = null
    var appUserName: String? = null
    var fullName: String? = null
    var email: String? = null
    var phone: String? = null
    var shippingAddress: String? = null
    var anonymousSessionID: String? = null

    fun isAnonymous() = anonymousSessionID != null

    constructor(id: Int) : this() {
        this.id = id
    }
}

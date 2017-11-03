package com.intetics.lukyanenko.models

class AppUser : Common {
    lateinit var name: String
    lateinit var password: String
    var isCustomer: Boolean = false
    var isNew: Boolean = false
    var roles: Set<String> = HashSet<String>()

    constructor() : super() {
        isNew = true
    }

    constructor(isNew: Boolean) {
        this.isNew = isNew
    }

    constructor(name: String) : this(false) {
        this.name = name
    }
}

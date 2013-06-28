package com.grails

class User {

    String name, password

    static constraints = {
    }

    def static someMethod(){
        return "Object validate"
    }
}

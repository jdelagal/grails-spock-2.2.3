package com.grails

class User {

    String name, password

    static constraints = {
    }

    def methodWithNoArguments(){
        return "Object: methodWithNoArguments"
    }

    def methodWithArguments(String x, Integer y) {
        return "Object: methodWithArguments(String,Integer)"
    }
    def methodWithArguments(String x) {
        return "Object: methodWithArguments(String)"
    }
}

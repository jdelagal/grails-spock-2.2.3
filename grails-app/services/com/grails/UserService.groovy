package com.grails

class UserService {

    def createUser( User user ) {
        user.save(flush: true)
    }

    def deleteUser( User user ) {
        user.delete()
    }
}

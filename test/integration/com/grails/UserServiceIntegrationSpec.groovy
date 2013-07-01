package com.grails

import grails.plugin.spock.IntegrationSpec


class UserServiceIntegrationSpec extends IntegrationSpec {

    def userService

    def setup() {

    }

    def 'create User Domain'() {
        given:
        User user = new User(name: 'abc',password: 'xyz')

        when:
        userService.createUser(user)

        then:
        User.findAll().size() == 1
    }

    // works in grails-2.0.0 spock 0.6
    def 'create User Mock using spock'() {
        given:
        User user = Mock(User)

        when:
        userService.createUser(user)

        then:
        1 * user.save(_)
    }
}

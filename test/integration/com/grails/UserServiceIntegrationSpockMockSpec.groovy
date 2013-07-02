package com.grails

import grails.plugin.spock.IntegrationSpec

class UserServiceIntegrationSpockMockSpec extends IntegrationSpec {

    def userService

    def setup() {

    }

    // DOES NOT work in 2.2.3 spock 0.7,
    // WORKS in grails-2.0.0 spock 0.6
    def 'create User Mock using spock'() {
        given:
        User user = Mock(User)

        when:
        userService.createUser(user)

        then:
        1 * user.save(_)
        // since this is mocked, things are not persisted into database
        // User.findAll().size() == 1
    }
}

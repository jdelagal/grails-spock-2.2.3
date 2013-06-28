package com.grails

import spock.lang.*
import grails.test.mixin.TestFor
import grails.test.mixin.Mock
import grails.test.GrailsMock

/**
 * Created with IntelliJ IDEA.
 * User: raghuramn
 * Date: 6/28/13
 * Time: 6:48 PM
 * To change this template use File | Settings | File Templates.
 */
@TestFor(UserController)
@Mock([User])
class UserControllerSpec extends  Specification{

    def user = new User()



    def setup(){

        user.id = 1
        user.name = "test"
        user.password = "1234"
        user.save(flush: true)

    }

    void 'show action '() {
        given: 'user'
        when: 'we hit the show action'
        def model = controller.show(new Long(1))

        then: 'user is saved'
        model.userInstance.id == 1
    }


    void 'show action with error'() {
        given: 'user'
        user.id = 2
        user.name = 'TestTwo'
        user.save(flush: true)
        when: 'we hit the show action'
        def model = controller.show(new Long(2))


        then: 'user is saved'
        model.userInstance.id == 2
        model.userInstance.name == 'TestTwo'
    }
}

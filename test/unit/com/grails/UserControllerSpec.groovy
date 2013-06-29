package com.grails

import spock.lang.*
import grails.test.mixin.TestFor
import grails.test.mixin.Mock
import grails.test.GrailsMock

@TestFor(UserController)  // testing for
@Mock([User])             // collaborators
class UserControllerSpec extends  Specification {

    // def user = new User(name:'global',passwordl:'1234')
    def service = new UserService()

    def setup(){
    }

    def 'show action '() {
        given: 'user'

        User user = new User(name:'test',password:'1234').save(flush:true)

        when: 'we hit the show action'
        def model = controller.show(user.id)

        then: 'user is saved'
        model.userInstance.id == user.id
    }


    // Works
    def 'mock a method on collaborator using metaClass'() {
        given: 'user'

        User user = new User(name:'anotherTest',password:'1234').save(flush:true)

        User.metaClass.methodWithNoArguments = { "Mock: (1) methodWithNoArguments" }
        User.metaClass.methodWithArguments   = { String x, Integer y -> "Mock: (1) methodWithArguments(String,Integer)" }
        User.metaClass.methodWithArguments   = { String x -> "Mock: (1) methodWithArguments(String)" }

        when: 'we call the methods'
        def val1 = user.methodWithNoArguments()
        def val2 = user.methodWithArguments("x",1)
        def val3 = user.methodWithArguments("x")

        then: 'mocked methods should be used.'
        val1 == "Mock: (1) methodWithNoArguments"
        val2 == "Mock: (1) methodWithArguments(String,Integer)"
        val3 == "Mock: (1) methodWithArguments(String)"
    }

    // Works
    def 'using a collaborator without mocking'() {
        given:'user'
        User user = new User(name:'anotherTest',password:'1234').save(flush:true)

        when: 'we call the methods'
        def val1 = user.methodWithNoArguments()
        def val2 = user.methodWithArguments("x",1)
        def val3 = user.methodWithArguments("x")

        then: 'mocked methods should be used.'
        val1 == "Object: methodWithNoArguments"
        val2 == "Object: methodWithArguments(String,Integer)"
        val3 == "Object: methodWithArguments(String)"
    }

    // DOES NOT work in 2.2.3,
    // WORKS in grails-2.0.0
    def 'create a Domain collaborator using Mock'() {
        given: 'user'
        // can't mock this anymore, causes classcast exception.
        User user = Mock(User)

        user.methodWithNoArguments()  >> { "Mock: (3) methodWithNoArguments" }
        user.methodWithArguments(_,_) >> { "Mock: (3) methodWithArguments(String,Integer)" }
        user.methodWithArguments(_)   >> { "Mock: (3) methodWithArguments(String)" }

        when: 'we call the method'
        def val1 = user.methodWithNoArguments()
        def val2 = user.methodWithArguments("x",1)
        def val3 = user.methodWithArguments("x")

        then: 'value should be from the mocked method'
        val1 == "Mock: (3) methodWithNoArguments"
        val2 == "Mock: (3) methodWithArguments(String,Integer)"
        val3 == "Mock: (3) methodWithArguments(String)"
    }

    // WORKS
    def 'create Service collaborator using Mock'() {
        given:'mocked service'

        service = Mock(UserService)
        service.createUser(_) >> { null }

        controller.userService = service

        when:
        controller.save()

        then:
        // verify that mocked method is called.
        1 * service.createUser(_)
        view == '/user/create'
    }

}

package com.grails

import org.springframework.dao.DataIntegrityViolationException

class UserController {

    def userService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [userInstanceList: User.list(params), userInstanceTotal: User.count()]
    }

    def create() {
        [userInstance: new User(params)]
    }

    def save() {
        def userInstance = new User(params)
        if (!userService.createUser(userInstance)) {
            render(view: "create", model: [userInstance: userInstance])
            return
        }

        flashMessage('default.created.message', ['User', userInstance.id])
        redirect(action: "show", id: userInstance.id)
    }

    def show(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flashMessage('default.not.found.message', ['User', id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }

    def edit(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flashMessage('default.not.found.message', ['User', id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }

    def update(Long id, Long version) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flashMessage('default.not.found.message',['User', id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (userInstance.version > version) {
                userInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          ['User'] as Object[],
                          "Another user has updated this User while you were editing")
                render(view: "edit", model: [userInstance: userInstance])
                return
            }
        }

        userInstance.properties = params

        if (!userInstance.save(flush: true)) {
            render(view: "edit", model: [userInstance: userInstance])
            return
        }

        flashMessage('default.updated.message', ['User', userInstance.id])
        redirect(action: "show", id: userInstance.id)
    }

    def delete(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flashMessage('default.not.found.message', ['User', id])
            redirect(action: "list")
            return
        }

        try {
            userInstance.delete(flush: true)
            flashMessage('default.deleted.message', ['User', id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flashMessage('default.not.deleted.message'['User', id])
            redirect(action: "show", id: id)
        }
    }

    private flashMessage(code, args) {
        flash.message=message(code:code,args:args)
    }
}

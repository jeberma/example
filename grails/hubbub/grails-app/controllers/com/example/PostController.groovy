package com.example

class PostController {

	def postService
	
	def scaffold = true

	def index = {
		params.id = params.id ?: 'admin'
		redirect(action:'timeline',params:params)
	}
	
    def timeline = {
		def user = User.findByUserId(params.id)
		[user : user]
	}

	def addPost = {
		try {
			def newPost = postService.createPost(params.id, params.content)
			flash.message = "Added new post ${newPost.content}"
		} catch(PostException e) {
			flash.message = e.message
		}
		redirect(action:'timeline', id: params.id)
	}
}

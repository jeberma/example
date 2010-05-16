package com.example

class ImageController {

    def upload = {PhotoUploadCommand cmd -> 
    	def user = User.findByUserId(cmd.userId)
    	user.profile.photo = cmd.photo
    	redirect(action:'view', id:cmd.userId)
    }

    def form = {
    	[userList: User.list()]
    }

    def view = {
    	
    }
}

class PhotoUploadCommand {
	byte[] photo
	String userId
}

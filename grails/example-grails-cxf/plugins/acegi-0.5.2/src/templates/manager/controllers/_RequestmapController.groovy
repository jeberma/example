${requestmapClassImport}

import org.springframework.util.StringUtils

/**
 * Requestmap controller.
 */
class ${requestmapClassName}Controller {

	def authenticateService

	// the delete, save and update actions only accept POST requests
	static Map allowedMethods = [delete: 'POST', save: 'POST', update: 'POST']

	def index = {
		redirect action: list, params: params
	}

	def list = {
		if (!params.max) {
			params.max = 10
		}
		[requestmapList: ${requestmapClassName}.list(params)]
	}

	def show = {
		def requestmap = ${requestmapClassName}.get(params.id)
		if (!requestmap) {
			flash.message = "${requestmapClassName} not found with id \$params.id"
			redirect action:list
			return
		}
		[requestmap: requestmap]
	}

	def delete = {
		def requestmap = ${requestmapClassName}.get(params.id)
		if (!requestmap) {
			flash.message = "${requestmapClassName} not found with id \$params.id"
			redirect action:list
			return
		}

		requestmap.delete()

		authenticateService.clearCachedRequestmaps()

		flash.message = "${requestmapClassName} \$params.id deleted."
		redirect(action: list)
	}

	def edit = {
		def requestmap = ${requestmapClassName}.get(params.id)
		if (!requestmap) {
			flash.message = "${requestmapClassName} not found with id \$params.id"
			redirect(action: list)
			return
		}

		[requestmap: requestmap]
	}

	/**
	 * Update action, called when an existing Requestmap is updated.
	 */
	def update = {

		def requestmap = ${requestmapClassName}.get(params.id)
		if (!requestmap) {
			flash.message = "${requestmapClassName} not found with id \$params.id"
			redirect(action: edit, id :params.id)
			return
		}

		long version = params.version.toLong()
		if (requestmap.version > version) {
			requestmap.errors.rejectValue 'version', "requestmap.optimistic.locking.failure",
				"Another user has updated this ${requestmapClassName} while you were editing."
			render view: 'edit', model: [requestmap: requestmap]
			return
		}

		requestmap.properties = params
		if (requestmap.save()) {
			authenticateService.clearCachedRequestmaps()
			redirect action: show, id: requestmap.id
		}
		else {
			render view: 'edit', model: [requestmap: requestmap]
		}
	}

	def create = {
		[requestmap: new ${requestmapClassName}(params)]
	}

	/**
	 * Save action, called when a new Requestmap is created.
	 */
	def save = {
		def requestmap = new ${requestmapClassName}(params)
		if (requestmap.save()) {
			authenticateService.clearCachedRequestmaps()
			redirect action: show, id: requestmap.id
		}
		else {
			render view: 'create', model: [requestmap: requestmap]
		}
	}
}

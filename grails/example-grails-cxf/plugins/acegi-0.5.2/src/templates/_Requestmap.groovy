${requestmapClassPackage}

/**
 * Request Map domain class.
 */
class ${requestmapClassName} {

	String url
	String configAttribute

	static constraints = {
		url(blank: false, unique: true)
		configAttribute(blank: false)
	}
}

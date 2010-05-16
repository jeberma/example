${authorityClassPackage}

${personClassImport}

/**
 * Authority domain class.
 */
class ${authorityClassName} {

	static hasMany = [people: ${personClassName}]

	/** description */
	String description
	/** ROLE String */
	String authority

	static constraints = {
		authority(blank: false, unique: true)
		description()
	}
}

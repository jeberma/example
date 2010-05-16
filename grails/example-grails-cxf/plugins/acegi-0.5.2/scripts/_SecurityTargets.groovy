/* Copyright 2006-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Shared methods/closures and initialization.
 *
 * @author <a href='mailto:beckwithb@studentsonly.com'>Burt Beckwith</a>
 */

import groovy.text.SimpleTemplateEngine

grailsHome = Ant.project.properties.'environment.GRAILS_HOME'
includeTargets << new File("$grailsHome/scripts/Init.groovy")

personClassName = 'Person'
personClassPackage = ''
authorityClassName = 'Authority'
authorityClassPackage = ''
requestmapClassName = 'Requestmap'
requestmapClassPackage = ''
templateDir = "$acegiPluginDir/src/templates"
appDir = "$basedir/grails-app"

overwrite = true

generateFile = { String templatePath, String outputPath ->

	File templateFile = new File(templatePath)
	if (!templateFile.exists()) {
		println "$templatePath doesn't exist"
		return
	}

	File outFile = new File(outputPath)
	if (outFile.exists() && !overwrite) {
	    println "file *not* generated: $outFile.absolutePath"
		return
	}

	// in case it's in a package, create dirs
	Ant.mkdir dir: outFile.parentFile

	def binding = [personClassName: personClassName,
	               personClassPackage: '',
	               personClass: personClassName, personClassImport: '',
	               authorityClassName: authorityClassName,
	               authorityClassPackage: '',
	               authorityClass: authorityClassName, authorityClassImport: '',
	               requestmapClassName: requestmapClassName,
	               requestmapClassPackage: '',
	               requestmapClass: requestmapClassName, requestmapClassImport: '']

	if (personClassPackage) {
		binding.personClass = "${personClassPackage}.$personClassName"
		binding.personClassImport = "import $binding.personClass"
		binding.personClassPackage = "package $personClassPackage"
	}
	if (authorityClassPackage) {
		binding.authorityClass = "${authorityClassPackage}.$authorityClassName"
		binding.authorityClassImport = "import $binding.authorityClass"
		binding.authorityClassPackage = "package $authorityClassPackage"
	}
	if (requestmapClassPackage) {
		binding.requestmapClass = "${requestmapClassPackage}.$requestmapClassName"
		binding.requestmapClassImport = "import $binding.requestmapClass"
		binding.requestmapClassPackage = "package $requestmapClassPackage"
	}

	outFile.withWriter { writer ->
		def template = new SimpleTemplateEngine().createTemplate(templateFile.text)
		template.make(binding).writeTo(writer)
	}

	println "file generated at $outFile.absolutePath"
}

copyFile = { String from, String to ->
	Ant.copy(file: from, tofile: to, overwrite: overwrite)
}

loadConfig = {
	GroovyClassLoader loader = new GroovyClassLoader(getClass().getClassLoader())
	Class clazz = loader.parseClass(new File("$basedir/grails-app/conf/SecurityConfig.groovy"))
	def securityConfig = new ConfigSlurper().parse(clazz)
	splitPersonClassName securityConfig.security.loginUserDomainClass
	splitAuthorityClassName securityConfig.security.authorityDomainClass
	splitRequestmapClassName securityConfig.security.requestMapClass
	println "Login user domain class: $securityConfig.security.loginUserDomainClass"
	println "Authority domain class: $securityConfig.security.authorityDomainClass"
	println "Request Map domain class: $securityConfig.security.requestMapClass"
}

splitClassName = { name ->
	int index = name.lastIndexOf('.')
	return index == -1 ? [name, ''] : [name.substring(index + 1), name.substring(0, index)]
}

splitPersonClassName = { name ->
	def packageAndClass = splitClassName(name)
	personClassName = packageAndClass[0]
	personClassPackage = packageAndClass[1]
}

splitAuthorityClassName = { name ->
	def packageAndClass = splitClassName(name)
	authorityClassName = packageAndClass[0]
	authorityClassPackage = packageAndClass[1]
}

splitRequestmapClassName = { name ->
	def packageAndClass = splitClassName(name)
	requestmapClassName = packageAndClass[0]
	requestmapClassPackage = packageAndClass[1]
}

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
 * Create/Copy Domains, auth.gsp, Controllers for security plugin.
 *
 * @author Tsuyoshi Yamamoto
 * @author <a href='mailto:beckwithb@studentsonly.com'>Burt Beckwith</a>
 */

includeTargets << new File("$acegiPluginDir/scripts/_SecurityTargets.groovy")

target('default': 'Creates Domain classes for Spring Security plugin') {
	parseArgs()
	createDomains()
	copyViewAndControllers()
}

private void parseArgs() {
	args = args ? args.split('\n') : []
	switch (args.size()) {
		case 0:
			println 'Creating domain classes with default names'
			break
		case 3:
			splitPersonClassName args[0]
			splitAuthorityClassName args[1]
			splitRequestmapClassName args[2]
			println "Login user domain class: ${args[0]}"
			println "Authority domain class: ${args[1]}"
			println "Requestmap domain class: ${args[2]}"
			break
		default:
			usage()
			break
	}
}

private void usage() {
	println 'usage: grails create-auth-domains <person class name> <authority class name> <request map class name>'
	System.exit(1)
}

private void createDomains() {

	// create Person domain class
	generateFile "$templateDir/_Person.groovy",
		"$appDir/domain/${packageToDir(personClassPackage)}${personClassName}.groovy"

	// create Authority domain class
	generateFile "$templateDir/_Authority.groovy",
		"$appDir/domain/${packageToDir(authorityClassPackage)}${authorityClassName}.groovy"

	// create Requestmap domain class
	generateFile "$templateDir/_Requestmap.groovy",
		"$appDir/domain/${packageToDir(requestmapClassPackage)}${requestmapClassName}.groovy"

	// create SecurityConfig
	generateFile "$templateDir/_SecurityConfig.groovy", "$appDir/conf/SecurityConfig.groovy"
}

private String packageToDir(pkg) {
	String dir = ''
	if (pkg) {
		dir = pkg.replaceAll('\\.', '/') + '/'
	}

	return dir
}

private void copyViewAndControllers() {

	// copy login.gsp and Login/Logout Controller example.
	println 'copying login.gsp and Login/Logout Controller example. '
	Ant.mkdir dir: "$appDir/views/login"
	copyFile "$templateDir/views/login/auth.gsp", "$appDir/views/login/auth.gsp"
	copyFile "$templateDir/views/login/openIdAuth.gsp", "$appDir/views/login/openIdAuth.gsp"
	copyFile "$templateDir/views/login/denied.gsp", "$appDir/views/login/denied.gsp"
	copyFile "$templateDir/controllers/LoginController.groovy", "$appDir/controllers/LoginController.groovy"
	copyFile "$templateDir/controllers/LogoutController.groovy", "$appDir/controllers/LogoutController.groovy"

	// log4j.logger.org.springframework.security='off,stdout'
	def configFile = new File("$appDir/conf/Config.groovy")
	if (configFile.exists()) {
		configFile.append("\n\n//log4j.logger.org.springframework.security='off,stdout'")
	}
}

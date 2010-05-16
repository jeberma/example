import grails.util.GrailsUtil

import org.codehaus.groovy.grails.commons.ControllerArtefactHandler

import org.codehaus.groovy.grails.plugins.springsecurity.AnnotationFilterInvocationDefinition
import org.codehaus.groovy.grails.plugins.springsecurity.AuthenticatedVetoableDecisionManager
import org.codehaus.groovy.grails.plugins.springsecurity.AuthorizeTools
import org.codehaus.groovy.grails.plugins.springsecurity.GrailsAccessDeniedHandlerImpl
import org.codehaus.groovy.grails.plugins.springsecurity.GrailsAuthenticationProcessingFilter
import org.codehaus.groovy.grails.plugins.springsecurity.GrailsDaoAuthenticationProvider
import org.codehaus.groovy.grails.plugins.springsecurity.GrailsDaoImpl
import org.codehaus.groovy.grails.plugins.springsecurity.IpAddressFilter
import org.codehaus.groovy.grails.plugins.springsecurity.LogoutFilterFactoryBean
import org.codehaus.groovy.grails.plugins.springsecurity.QuietMethodSecurityInterceptor
import org.codehaus.groovy.grails.plugins.springsecurity.RequestmapFilterInvocationDefinition
import org.codehaus.groovy.grails.plugins.springsecurity.Secured as SecuredController
import org.codehaus.groovy.grails.plugins.springsecurity.SecurityEventListener
import org.codehaus.groovy.grails.plugins.springsecurity.WithAjaxAuthenticationProcessingFilterEntryPoint

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
import org.springframework.beans.factory.config.RuntimeBeanReference
import org.springframework.cache.ehcache.EhCacheFactoryBean
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean
import org.springframework.security.annotation.Secured as SecuredService
import org.springframework.security.context.HttpSessionContextIntegrationFilter
import org.springframework.security.context.SecurityContextHolder as SCH
import org.springframework.security.event.authentication.LoggerListener
import org.springframework.security.intercept.method.MethodDefinitionAttributes
import org.springframework.security.intercept.web.FilterSecurityInterceptor
import org.springframework.security.ui.ExceptionTranslationFilter
import org.springframework.security.ui.basicauth.BasicProcessingFilter
import org.springframework.security.ui.basicauth.BasicProcessingFilterEntryPoint
import org.springframework.security.ui.logout.LogoutHandler
import org.springframework.security.ui.logout.SecurityContextLogoutHandler
import org.springframework.security.ui.rememberme.NullRememberMeServices
import org.springframework.security.ui.rememberme.RememberMeProcessingFilter
import org.springframework.security.ui.rememberme.TokenBasedRememberMeServices
import org.springframework.security.ui.session.HttpSessionEventPublisher
import org.springframework.security.ui.switchuser.SwitchUserProcessingFilter
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter
import org.springframework.security.util.AntUrlPathMatcher
import org.springframework.security.util.PortMapperImpl
import org.springframework.security.util.PortResolverImpl
import org.springframework.security.util.RegexUrlPathMatcher
import org.springframework.security.providers.ProviderManager
import org.springframework.security.providers.anonymous.AnonymousAuthenticationProvider
import org.springframework.security.providers.anonymous.AnonymousProcessingFilter
import org.springframework.security.providers.dao.cache.EhCacheBasedUserCache
import org.springframework.security.providers.dao.cache.NullUserCache
import org.springframework.security.providers.encoding.MessageDigestPasswordEncoder
import org.springframework.security.providers.rememberme.RememberMeAuthenticationProvider
import org.springframework.security.securechannel.ChannelDecisionManagerImpl
import org.springframework.security.securechannel.ChannelProcessingFilter
import org.springframework.security.securechannel.InsecureChannelProcessor
import org.springframework.security.securechannel.RetryWithHttpEntryPoint
import org.springframework.security.securechannel.RetryWithHttpsEntryPoint
import org.springframework.security.securechannel.SecureChannelProcessor
import org.springframework.security.util.FilterChainProxy
import org.springframework.security.util.FilterToBeanProxy
import org.springframework.security.vote.AuthenticatedVoter
import org.springframework.security.vote.RoleVoter
import org.springframework.security.wrapper.SecurityContextHolderAwareRequestFilter
import org.springframework.web.filter.DelegatingFilterProxy

/**
 * Grails Spring Security 2.0 Plugin.
 *
 * @author T.Yamamoto
 * @author Haotian Sun
 * @author <a href='mailto:beckwithb@studentsonly.com'>Burt Beckwith</a>
 */
class AcegiGrailsPlugin {

	private static final String DEFINITION_SOURCE_PREFIX =
		'CONVERT_URL_TO_LOWERCASE_BEFORE_COMPARISON\n' +
		'PATTERN_TYPE_APACHE_ANT\n'

	String version = '0.5.2'
	String author = 'Tsuyoshi Yamamoto'
	String authorEmail = 'tyama@xmldo.jp'
	String title = 'Grails Spring Security 2.0 Plugin'
	String description = 'Plugin to use Grails domain class and secure your applications with Spring Security filters.'
	String documentation = 'http://grails.org/plugin/acegi'
	List observe = ['controllers']
	List loadAfter = ['controllers']
	List watchedResources = [
		'file:./grails-app/controllers/**/*Controller.groovy',
		'file:./plugins/*/grails-app/controllers/**/*Controller.groovy',
		'file:./grails-app/conf/SecurityConfig.groovy'
	]
	Map dependsOn = [:]

	def doWithSpring = {

		def conf = AuthorizeTools.securityConfig.security
		if (!conf || !conf.active) {
			println '[active=false] Spring Security not loaded'
			return
		}

		println 'loading security config ...'

		createRefList.delegate = delegate

		/** springSecurityFilterChain */
		configureFilterChain.delegate = delegate
		configureFilterChain conf

		// OpenID
		if (conf.useOpenId) {
			configureOpenId.delegate = delegate
			configureOpenId conf
		}

		// Facebook Connect
		if (conf.useFacebook) {
			configureFacebook.delegate = delegate
			configureFacebook conf
		}

		// logout
		configureLogout.delegate = delegate
		configureLogout conf

		// Basic Auth
		configureBasicAuth.delegate = delegate
		configureBasicAuth conf

		/** httpSessionContextIntegrationFilter */
		httpSessionContextIntegrationFilter(HttpSessionContextIntegrationFilter)

		/** authenticationProcessingFilter */
		authenticationProcessingFilter(GrailsAuthenticationProcessingFilter) {
			authenticationManager = ref('authenticationManager')
			authenticationFailureUrl = conf.authenticationFailureUrl //'/login/authfail?login_error=1'
			ajaxAuthenticationFailureUrl = conf.ajaxAuthenticationFailureUrl // /login/authfail?ajax=true
			defaultTargetUrl = conf.defaultTargetUrl // '/'
			alwaysUseDefaultTargetUrl = conf.alwaysUseDefaultTargetUrl // false
			filterProcessesUrl = conf.filterProcessesUrl // '/j_spring_security_check'
			rememberMeServices = ref('rememberMeServices')
			authenticateService = ref('authenticateService')
		}

		/** securityContextHolderAwareRequestFilter */
		securityContextHolderAwareRequestFilter(SecurityContextHolderAwareRequestFilter) {
			portResolver = ref('portResolver')
		}

		/** rememberMeProcessingFilter */
		rememberMeProcessingFilter(RememberMeProcessingFilter) {
			authenticationManager = ref('authenticationManager')
			rememberMeServices = ref('rememberMeServices')
		}

		/** rememberMeServices */
		if (conf.useOpenId || conf.useFacebook) {
			// auth is external, so no password, so cookie isn't possible
			rememberMeServices(NullRememberMeServices)
		}
		else {
			rememberMeServices(TokenBasedRememberMeServices) {
				userDetailsService = ref('userDetailsService')
				key = conf.rememberMeKey
				cookieName = conf.cookieName
				alwaysRemember = conf.alwaysRemember
				tokenValiditySeconds = conf.tokenValiditySeconds
				parameter = conf.parameter
			}
		}

		/** anonymousProcessingFilter */
		anonymousProcessingFilter(AnonymousProcessingFilter) {
			key = conf.key // 'foo'
			userAttribute = conf.userAttribute //'anonymousUser,ROLE_ANONYMOUS'
		}

		/** exceptionTranslationFilter */
		exceptionTranslationFilter(ExceptionTranslationFilter) {
			authenticationEntryPoint = ref('authenticationEntryPoint')
			accessDeniedHandler = ref('accessDeniedHandler')
			portResolver = ref('portResolver')
		}
		accessDeniedHandler(GrailsAccessDeniedHandlerImpl) {
			errorPage = conf.errorPage == 'null' ? null : conf.errorPage // '/login/denied' or 403
			ajaxErrorPage = conf.ajaxErrorPage
			portResolver = ref('portResolver')
			if (conf.ajaxHeader) {
				ajaxHeader = conf.ajaxHeader //default: X-Requested-With
			}
		}

		if (!conf.useNtlm) {
			authenticationEntryPoint(WithAjaxAuthenticationProcessingFilterEntryPoint) {
				loginFormUrl = conf.loginFormUrl // '/login/auth'
				forceHttps = conf.forceHttps // 'false'
				ajaxLoginFormUrl = conf.ajaxLoginFormUrl // '/login/authAjax'
				if (conf.ajaxHeader) {
					ajaxHeader = conf.ajaxHeader //default: X-Requested-With
				}
				portMapper = ref('portMapper')
				portResolver = ref('portResolver')
			}
		}

		// voters
		configureVoters.delegate = delegate
		configureVoters conf

		/** filterInvocationInterceptor */
		filterInvocationInterceptor(FilterSecurityInterceptor) {
			authenticationManager = ref('authenticationManager')
			accessDecisionManager = ref('accessDecisionManager')
			if (conf.useControllerAnnotations || conf.useRequestMapDomainClass) {
				objectDefinitionSource = ref('objectDefinitionSource')
			}
			else {
				objectDefinitionSource = conf.requestMapString
			}
		}
		if (conf.useControllerAnnotations) {
			objectDefinitionSource(AnnotationFilterInvocationDefinition) {
				boolean lowercase = conf.controllerAnnotationsMatchesLowercase
				if ('ant'.equals(conf.controllerAnnotationsMatcher)) {
					urlMatcher = new AntUrlPathMatcher(lowercase)
				}
				else {
					urlMatcher = new RegexUrlPathMatcher(lowercase)
				}
				if (conf.controllerAnnotationsRejectIfNoRule instanceof Boolean) {
					rejectIfNoRule = conf.controllerAnnotationsRejectIfNoRule
				}
			}
		}
		else if (conf.useRequestMapDomainClass) {
			objectDefinitionSource(RequestmapFilterInvocationDefinition) {
				requestMapClass = conf.requestMapClass
				requestMapPathFieldName = conf.requestMapPathField
				requestMapConfigAttributeField = conf.requestMapConfigAttributeField
				urlMatcher = new AntUrlPathMatcher(true)
			}
		}

		/** anonymousAuthenticationProvider */
		anonymousAuthenticationProvider(AnonymousAuthenticationProvider) {
			key = conf.key // 'foo'
		}
		/** rememberMeAuthenticationProvider */
		rememberMeAuthenticationProvider(RememberMeAuthenticationProvider) {
			key = conf.rememberMeKey
		}

		// authenticationManager
		configureAuthenticationManager.delegate = delegate
		configureAuthenticationManager conf

		/** daoAuthenticationProvider */
		daoAuthenticationProvider(GrailsDaoAuthenticationProvider) {
			userDetailsService = ref('userDetailsService')
			passwordEncoder = ref('passwordEncoder')
			userCache = ref('userCache')
		}

		/** passwordEncoder */
		passwordEncoder(MessageDigestPasswordEncoder, conf.algorithm) {
			if (conf.encodeHashAsBase64) {
				encodeHashAsBase64 = true
			}
		}

		// user details cache
		if (conf.cacheUsers) {
			userCache(EhCacheBasedUserCache) {
				cache = ref('securityUserCache')
			}
			securityUserCache(EhCacheFactoryBean) {
				cacheManager = ref('cacheManager')
				cacheName = 'userCache'
			}
			cacheManager(EhCacheManagerFactoryBean)
		}
		else {
			userCache(NullUserCache)
		}

		/** userDetailsService */
		userDetailsService(GrailsDaoImpl) {
			usernameFieldName = conf.userName
			passwordFieldName = conf.password
			enabledFieldName = conf.enabled
			authorityFieldName = conf.authorityField
			loginUserDomainClass = conf.loginUserDomainClass
			relationalAuthoritiesField = conf.relationalAuthorities
			authoritiesMethodName = conf.getAuthoritiesMethod
			sessionFactory = ref('sessionFactory')
			authenticateService = ref('authenticateService')
		}

		/** loggerListener ( log4j.logger.org.springframework.security=info,stdout ) */
		if (conf.useLogger) {
			loggerListener(LoggerListener)
		}

		// port mappings for channel security, etc.
		portMapper(PortMapperImpl) {
			portMappings = [(conf.httpPort.toString()) : conf.httpsPort.toString()]
		}
		portResolver(PortResolverImpl) {
			portMapper = portMapper
		}

		daacc(DefaultAdvisorAutoProxyCreator)

		// simple email service
		configureMail.delegate = delegate
		configureMail conf

		// Switch User
		if (conf.switchUserProcessingFilter) {
			switchUserProcessingFilter(SwitchUserProcessingFilter) {
				userDetailsService = ref('userDetailsService')
				switchUserUrl = conf.swswitchUserUrl
				exitUserUrl = conf.swexitUserUrl
				targetUrl = conf.swtargetUrl
			}
		}

		// LDAP
		if (conf.useLdap) {
			configureLdap.delegate = delegate
			configureLdap conf
		}

		// SecurityEventListener
		securityEventListener(SecurityEventListener) {
			authenticateService = ref('authenticateService')
		}

		// Kerberos
		if (conf.useKerberos) {
			configureKerberos.delegate = delegate
			configureKerberos conf
		}

		// NTLM
		if (conf.useNtlm) {
			configureNtlm.delegate = delegate
			configureNtlm conf
		}

		// CAS
		if (conf.useCAS) {
			configureCAS.delegate = delegate
			configureCAS conf
		}

		// channel (http/https) security
		if (useSecureChannel(conf)) {
			configureChannelProcessingFilter.delegate = delegate
			configureChannelProcessingFilter conf
		}

		// IP filter
		if (conf.ipRestrictions) {
			configureIpFilter.delegate = delegate
			configureIpFilter conf
		}
	}

	private boolean useSecureChannel(conf) {
		return conf.secureChannelDefinitionSource || conf.channelConfig.secure || conf.channelConfig.insecure
	}

	// OpenID
	private def configureOpenId = { conf ->
		openIDAuthProvider(org.codehaus.groovy.grails.plugins.springsecurity.openid.GrailsOpenIdAuthenticationProvider) {
			userDetailsService = ref('userDetailsService')
		}
		openIDStore(org.openid4java.consumer.InMemoryConsumerAssociationStore)
		openIDNonceVerifier(org.openid4java.consumer.InMemoryNonceVerifier, conf.openIdNonceMaxSeconds) // 300 seconds
		openIDConsumerManager(org.openid4java.consumer.ConsumerManager) {
			nonceVerifier = openIDNonceVerifier
		}
		openIDConsumer(org.springframework.security.ui.openid.consumers.OpenID4JavaConsumer, openIDConsumerManager)
		openIDAuthenticationProcessingFilter(org.springframework.security.ui.openid.OpenIDAuthenticationProcessingFilter) {
			authenticationManager = ref('authenticationManager')
			authenticationFailureUrl = conf.authenticationFailureUrl //'/login/authfail?login_error=1' // /spring_security_login?login_error
			defaultTargetUrl = conf.defaultTargetUrl // '/'
			filterProcessesUrl = '/j_spring_openid_security_check' // not configurable
			rememberMeServices = ref('rememberMeServices')
			consumer = openIDConsumer
		}
	}

	// Facebook
	private def configureFacebook = { conf ->
		facebookAuthProvider(org.codehaus.groovy.grails.plugins.springsecurity.facebook.FacebookAuthenticationProvider) {
			userDetailsService = ref('userDetailsService')
		}
		facebookAuthenticationProcessingFilter(org.codehaus.groovy.grails.plugins.springsecurity.facebook.FacebookAuthenticationProcessingFilter) {
			authenticationManager = ref('authenticationManager')
			authenticationFailureUrl = conf.authenticationFailureUrl //'/login/authfail?login_error=1' // /spring_security_login?login_error
			defaultTargetUrl = conf.defaultTargetUrl // '/'
			filterProcessesUrl = conf.facebook.filterProcessesUrl // '/j_spring_facebook_security_check'
			apiKey = conf.facebook.apiKey
			secretKey = conf.facebook.secretKey
			authenticationUrlRoot = conf.facebook.authenticationUrlRoot // http://www.facebook.com/login.php?v=1.0&api_key=
			rememberMeServices = ref('rememberMeServices')
		}
		facebookLogoutHandler(org.codehaus.groovy.grails.plugins.springsecurity.facebook.FacebookLogoutHandler) {
			apiKey = conf.facebook.apiKey
		}
	}

	private def configureCAS = { conf ->
		String casHost = conf.cas.casServer ?: 'localhost'
		int casPort = (conf.cas.casServerPort ?: '443').toInteger()
		String casFilterProcessesUrl = conf.cas.filterProcessesUrl ?: '/j_spring_cas_security_check'
		boolean sendRenew = Boolean.valueOf(conf.cas.sendRenew ?: false)
		String proxyReceptorUrl = conf.cas.proxyReceptorUrl ?: '/secure/receptor'
		String applicationHost = System.getProperty('server.host') ?: 'localhost'
		int applicationPort = (System.getProperty('server.port') ?: 8080).toInteger()
		String appName = application.metadata['app.name']
		String casHttp = conf.cas.casServerSecure ? 'https' : 'http'
		String localHttp = conf.cas.localhostSecure ? 'https' : 'http'

		proxyGrantingTicketStorage(org.jasig.cas.client.proxy.ProxyGrantingTicketStorageImpl)

		casProcessingFilter(org.springframework.security.ui.cas.CasProcessingFilter) {
			authenticationManager = ref('authenticationManager')
			authenticationFailureUrl = conf.cas.failureURL ?: '/denied.jsp'
			defaultTargetUrl = conf.cas.defaultTargetURL ?: '/'
			filterProcessesUrl = casFilterProcessesUrl
			proxyGrantingTicketStorage = proxyGrantingTicketStorage
			proxyReceptorUrl = proxyReceptorUrl
		}

		casServiceProperties(org.springframework.security.ui.cas.ServiceProperties) {
			service = "$localHttp://$applicationHost:$applicationPort/$appName$casFilterProcessesUrl"
			sendRenew = sendRenew
		}

		String casLoginURL = conf.cas.fullLoginURL ?: "$casHttp://$casHost:$casPort/cas/login"
		authenticationEntryPoint(org.springframework.security.ui.cas.CasProcessingFilterEntryPoint) {
			loginUrl = casLoginURL
			serviceProperties = casServiceProperties
		}

		String casServiceURL = conf.cas.fullServiceURL ?: "$casHttp://$casHost:$casPort/cas"
		cas20ServiceTicketValidator(org.jasig.cas.client.validation.Cas20ServiceTicketValidator, casServiceURL) {
			proxyGrantingTicketStorage = proxyGrantingTicketStorage
			proxyCallbackUrl = "$localHttp://$applicationHost:$applicationPort/$appName$proxyReceptorUrl"
		}

		// the CAS authentication provider key doesn't need to be anything special, it just identifies individual providers
		// so that they can identify tokens it previously authenticated
		String casAuthenticationProviderKey = conf.cas.authenticationProviderKey ?: appName + System.currentTimeMillis()
		casAuthenticationProvider(org.springframework.security.providers.cas.CasAuthenticationProvider) {
			userDetailsService = ref(conf.cas.userDetailsService ?: 'userDetailsService')
			serviceProperties = casServiceProperties
			ticketValidator = cas20ServiceTicketValidator
			key = casAuthenticationProviderKey
		}
	}

	private def configureLogout = { conf ->

		securityContextLogoutHandler(SecurityContextLogoutHandler)
		def logoutHandlerNames = conf.logoutHandlerNames
		if (!logoutHandlerNames) {
			logoutHandlerNames = []
			if (conf.useFacebook) {
				logoutHandlerNames << 'facebookLogoutHandler'
			}
			else if (!conf.useOpenId) {
				logoutHandlerNames << 'rememberMeServices'
			}
			logoutHandlerNames << 'securityContextLogoutHandler'
		}

		def logoutHandlers = createRefList(logoutHandlerNames)
		def afterLogoutUrl = conf.afterLogoutUrl // '/'

		/** logoutFilter */
		logoutFilter(LogoutFilterFactoryBean) {
			logoutSuccessUrl = afterLogoutUrl
			handlers = logoutHandlers
		}
	}

	private def configureBasicAuth = { conf ->

		basicProcessingFilterEntryPoint(BasicProcessingFilterEntryPoint) {
			realmName = conf.realmName // 'Grails Realm'
		}
		basicProcessingFilter(BasicProcessingFilter) {
			authenticationManager = ref('authenticationManager')
			authenticationEntryPoint = basicProcessingFilterEntryPoint
		}
	}

	private def configureVoters = { conf ->

		roleVoter(RoleVoter)

		authenticatedVoter(AuthenticatedVoter)

		def decisionVoterNames = conf.decisionVoterNames
		if (!decisionVoterNames) {
			decisionVoterNames = ['authenticatedVoter', 'roleVoter']
		}
		def decisionVoterList = createRefList(decisionVoterNames)
		/** accessDecisionManager */
		accessDecisionManager(AuthenticatedVetoableDecisionManager) {
			allowIfAllAbstainDecisions = false
			decisionVoters = decisionVoterList
		}
	}

	private def configureAuthenticationManager = { conf ->

		def providerNames = conf.providerNames
		if (!providerNames) {
			providerNames = []
			if (conf.useKerberos) {
				providerNames << 'kerberosAuthProvider'
			}
			if (conf.useCAS) {
				providerNames << 'casAuthenticationProvider'
			}
			if (conf.useLdap) {
				providerNames << 'ldapAuthProvider'
			}
			if (conf.useFacebook) {
				providerNames << 'facebookAuthProvider'
			}

			if (providerNames.empty) {
				providerNames << 'daoAuthenticationProvider'
				if (conf.useOpenId) {
					providerNames << 'openIDAuthProvider'
				}
			}

			providerNames << 'anonymousAuthenticationProvider'
			providerNames << 'rememberMeAuthenticationProvider'
		}

		def providerList = createRefList(providerNames)
		/** authenticationManager */
		authenticationManager(ProviderManager) {
			providers = providerList
		}
	}

	private def configureMail = { conf ->

		if (conf.useMail) {
			mailSender(org.springframework.mail.javamail.JavaMailSenderImpl) {
				host = conf.mailHost
				username = conf.mailUsername
				password = conf.mailPassword
				protocol = conf.mailProtocol
				port = conf.mailPort
				if (conf.javaMailProperties) {
					javaMailProperties = conf.javaMailProperties as Properties
				}
			}

			mailMessage(org.springframework.mail.SimpleMailMessage) {
				from = conf.mailFrom
			}
		}
	}

	private def configureLdap = { conf ->

		contextSource(org.springframework.security.ldap.DefaultSpringSecurityContextSource, conf.ldapServer) {
			userDn = conf.ldapManagerDn
			password = conf.ldapManagerPassword
		}

		ldapUserSearch(org.springframework.security.ldap.search.FilterBasedLdapUserSearch,
			           conf.ldapSearchBase, conf.ldapSearchFilter, contextSource) {
			searchSubtree = conf.ldapSearchSubtree
		}

		ldapAuthenticator(org.springframework.security.providers.ldap.authenticator.BindAuthenticator,
			              contextSource) {
			userSearch = ldapUserSearch
		}

		ldapUserDetailsMapper(org.codehaus.groovy.grails.plugins.springsecurity.ldap.GrailsLdapUserDetailsMapper) {
			userDetailsService = ref('userDetailsService')
			passwordAttributeName = conf.ldapPasswordAttributeName // 'userPassword'
			usePassword = conf.ldapUsePassword // true
			retrieveDatabaseRoles = conf.ldapRetrieveDatabaseRoles // false
		}

		if (conf.ldapRetrieveGroupRoles) {
			ldapAuthoritiesPopulator(org.springframework.security.ldap.populator.DefaultLdapAuthoritiesPopulator,
				                     contextSource, conf.ldapGroupSearchBase) {
				groupRoleAttribute = conf.ldapGroupRoleAttribute
				groupSearchFilter = conf.ldapGroupSearchFilter
				searchSubtree = conf.ldapSearchSubtree
			}
			ldapAuthProvider(org.springframework.security.providers.ldap.LdapAuthenticationProvider,
				             ldapAuthenticator, ldapAuthoritiesPopulator) {
				userDetailsContextMapper = ldapUserDetailsMapper
			}
		}
		else {
			// use the NullAuthoritiesPopulator
			ldapAuthProvider(org.springframework.security.providers.ldap.LdapAuthenticationProvider,
				             ldapAuthenticator) {
				userDetailsContextMapper = ldapUserDetailsMapper
			}
		}
	}

	private def configureKerberos = { conf ->

		jaasNameCallbackHandler(org.springframework.security.providers.jaas.JaasNameCallbackHandler)

		jaasPasswordCallbackHandler(org.springframework.security.providers.jaas.JaasPasswordCallbackHandler)

		kerberosAuthProvider(org.codehaus.groovy.grails.plugins.springsecurity.kerberos.GrailsKerberosAuthenticationProvider) {
			authenticateService = ref('authenticateService')
			userDetailsService = ref('userDetailsService')
			loginConfig = conf.kerberosLoginConfigFile
			loginContextName = 'KrbAuthentication'
			callbackHandlers = [jaasNameCallbackHandler, jaasPasswordCallbackHandler]
			authorityGranters = []
		}

		//TODO: Improve
		System.setProperty('java.security.krb5.realm', conf.kerberosRealm)
		System.setProperty('java.security.krb5.kdc', conf.kerberosKdc)
	}

	private def configureNtlm = { conf ->

		ntlmFilter(org.springframework.security.ui.ntlm.NtlmProcessingFilter) {
			stripDomain = conf.ntlm.stripDomain // true
			retryOnAuthFailure = conf.ntlm.retryOnAuthFailure // true
			defaultDomain = conf.ntlm.defaultDomain
			netbiosWINS = conf.ntlm.netbiosWINS
			forceIdentification = conf.ntlm.forceIdentification // false
			authenticationManager = ref('authenticationManager')
		}

		authenticationEntryPoint(org.codehaus.groovy.grails.plugins.springsecurity.GrailsNtlmProcessingFilterEntryPoint) {
			authenticationFailureUrl = conf.authenticationFailureUrl
		}
	}

	private def configureFilterChain = { conf ->

		def filterNames = conf.filterNames
		if (!filterNames) {
			filterNames = []

			if (useSecureChannel(conf)) {
				filterNames << 'channelProcessingFilter' // CHANNEL_FILTER
			}

			// CONCURRENT_SESSION_FILTER

			filterNames << 'httpSessionContextIntegrationFilter' // HTTP_SESSION_CONTEXT_FILTER

			filterNames << 'logoutFilter' // LOGOUT_FILTER

			if (conf.ipRestrictions) {
				filterNames << 'ipAddressFilter'
			}

			// X509_FILTER

			// PRE_AUTH_FILTER

			if (conf.useCAS) {
				filterNames << 'casProcessingFilter' // CAS_PROCESSING_FILTER
			}

			filterNames << 'authenticationProcessingFilter' // AUTHENTICATION_PROCESSING_FILTER

			if (conf.useOpenId) {
				filterNames << 'openIDAuthenticationProcessingFilter' // OPENID_PROCESSING_FILTER
			}

			if (conf.useFacebook) {
				filterNames << 'facebookAuthenticationProcessingFilter'
			}

			// LOGIN_PAGE_FILTER

			if (conf.basicProcessingFilter) {
				filterNames << 'basicProcessingFilter' // BASIC_PROCESSING_FILTER
			}

			if (!conf.useNtlm) {
				// seems to remove NTLM authentication tokens
				filterNames << 'securityContextHolderAwareRequestFilter' // SERVLET_API_SUPPORT_FILTER
			}

			filterNames << 'rememberMeProcessingFilter' // REMEMBER_ME_FILTER

			filterNames << 'anonymousProcessingFilter' // ANONYMOUS_FILTER

			filterNames << 'exceptionTranslationFilter' // EXCEPTION_TRANSLATION_FILTER

			if (conf.useNtlm) {
				filterNames << 'ntlmFilter' // NTLM_FILTER
			}

			// SESSION_FIXATION_FILTER

			filterNames << 'filterInvocationInterceptor' // FILTER_SECURITY_INTERCEPTOR

			if (conf.switchUserProcessingFilter) {
				filterNames << 'switchUserProcessingFilter' // SWITCH_USER_FILTER
			}
		}
		String joinedFilters = filterNames.join(',')

		String definitionSource
		if (conf.filterInvocationDefinitionSource) {
			// if the entire string is set in the config, use that
			definitionSource = conf.filterInvocationDefinitionSource
		}
		else if (conf.filterInvocationDefinitionSourceMap) {
			// otherwise if there's a map of configs, use those
			definitionSource = DEFINITION_SOURCE_PREFIX
			conf.filterInvocationDefinitionSourceMap.each { key, value ->
				if (value == 'JOINED_FILTERS') {
					// special case to use either the filters defined by
					// conf.filterNames or the filters defined by config settings
					value = joinedFilters
				}
				definitionSource += "$key=$value\n"
			}
		}
		else {
			// otherwise build the default string - all urls guarded by all filters
			definitionSource = "$DEFINITION_SOURCE_PREFIX\n/**=$joinedFilters"
		}
		springSecurityFilterChain(FilterChainProxy) {
			filterInvocationDefinitionSource = definitionSource
		}
	}

	private def configureChannelProcessingFilter = { conf ->

		retryWithHttpEntryPoint(RetryWithHttpEntryPoint) {
			portMapper = ref('portMapper')
			portResolver = ref('portResolver')
		}

		retryWithHttpsEntryPoint(RetryWithHttpsEntryPoint) {
			portMapper = ref('portMapper')
			portResolver = ref('portResolver')
		}

		secureChannelProcessor(SecureChannelProcessor) {
			entryPoint = retryWithHttpsEntryPoint
		}

		insecureChannelProcessor(InsecureChannelProcessor) {
			entryPoint = retryWithHttpEntryPoint
		}

		channelDecisionManager(ChannelDecisionManagerImpl) {
			channelProcessors = [insecureChannelProcessor, secureChannelProcessor]
		}

		String definitionSource
		if (conf.secureChannelDefinitionSource) {
			// if the entire string is set in the config, use that
			definitionSource = conf.secureChannelDefinitionSource
		}
		else {
			definitionSource = DEFINITION_SOURCE_PREFIX
			conf.channelConfig.secure.each { pattern ->
				definitionSource += "$pattern=REQUIRES_SECURE_CHANNEL\n"
			}
			conf.channelConfig.insecure.each { pattern ->
				definitionSource += "$pattern=REQUIRES_INSECURE_CHANNEL\n"
			}
		}
		channelProcessingFilter(ChannelProcessingFilter) {
			channelDecisionManager = channelDecisionManager
			filterInvocationDefinitionSource = definitionSource
		}
	}

	private def configureIpFilter = { conf ->
		ipAddressFilter(IpAddressFilter) {
			ipRestrictions = conf.ipRestrictions
		}
	}

	def doWithApplicationContext = { ctx ->
		// nothing to do
	}

	def doWithWebDescriptor = { xml ->

		def conf = AuthorizeTools.securityConfig.security
		if (!conf || !conf.active) {
			return
		}

		// we add the filter(s) right after the last context-param
		def contextParam = xml.'context-param'

		// the name of the filter matches the name of the Spring bean that it delegates to
		contextParam[contextParam.size() - 1] + {
			'filter' {
				'filter-name'('springSecurityFilterChain')
				'filter-class'(DelegatingFilterProxy.name)
			}
		}

		// add the filter-mapping after the Spring character encoding filter
		findMappingLocation.delegate = delegate
		def mappingLocation = findMappingLocation(xml)
		mappingLocation + {
			'filter-mapping'{
				'filter-name'('springSecurityFilterChain')
				'url-pattern'('/*')
			}
		}

		if (conf.useHttpSessionEventPublisher) {
			def filterMapping = xml.'filter-mapping'
			filterMapping[filterMapping.size() - 1] + {
				'listener' {
					'listener-class'(HttpSessionEventPublisher.name)
				}
			}
		}
	}

	private def findMappingLocation = { xml ->

		// find the location to insert the filter-mapping; needs to be after the 'charEncodingFilter'
		// which may not exist. should also be before the sitemesh filter.
		// thanks to the JSecurity plugin for the logic.

		def mappingLocation = xml.'filter-mapping'.find { it.'filter-name'.text() == 'charEncodingFilter' }
		if (mappingLocation) {
			return mappingLocation
		}

		// no 'charEncodingFilter'; try to put it before sitemesh
		int i = 0
		int siteMeshIndex = -1
		xml.'filter-mapping'.each {
			if (it.'filter-name'.text().equalsIgnoreCase('sitemesh')) {
				siteMeshIndex = i
			}
			i++
		}
		if (siteMeshIndex > 0) {
			return xml.'filter-mapping'[siteMeshIndex - 1]
		}

		if (siteMeshIndex == 0 || xml.'filter-mapping'.size() == 0) {
			def filters = xml.'filter'
			return filters[filters.size() - 1]
		}

		// neither filter found
		def filters = xml.'filter'
		return filters[filters.size() - 1]
	}

	def doWithDynamicMethods = { ctx ->

		def conf = AuthorizeTools.securityConfig.security
		if (!conf || !conf.active) {
			return
		}

		for (controllerClass in application.controllerClasses) {
			addControllerMethods controllerClass.metaClass
		}

		if (conf.useControllerAnnotations) {
			ctx.objectDefinitionSource.initialize conf.controllerAnnotationStaticRules,
				ctx.grailsUrlMappingsHolder, application.controllerClasses
		}
	}

	def onChange = { event ->

		def conf = AuthorizeTools.securityConfig.security
		if (!conf || !conf.active) {
			return
		}

		def ctx = event.ctx
		if (event.source && ctx && event.application) {
			boolean isControllerClass = application.isControllerClass(event.source)
			boolean configChanged = 'SecurityConfig'.equals(event.source.name)
			if (configChanged || isControllerClass) {
				if (conf.useControllerAnnotations) {
					ctx.objectDefinitionSource.initialize conf.controllerAnnotationStaticRules,
						ctx.grailsUrlMappingsHolder, application.controllerClasses
				}
				if (isControllerClass) {
					addControllerMethods application.getControllerClass(event.source.name).metaClass
				}
			}
		}
	}

	def onApplicationChange = { event ->
		// nothing to do
	}

	private void addControllerMethods(MetaClass mc) {
		mc.getAuthUserDomain = {
			def principal = SCH.context?.authentication?.principal
			if (principal != null && principal != 'anonymousUser') {
				return principal?.domainClass
			}

			return null
		}

		mc.getPrincipalInfo = {
			return SCH.context?.authentication?.principal
		}

		mc.isUserLogon = {
			def principal = SCH.context?.authentication?.principal
			return principal != null && principal != 'anonymousUser'
		}
	}

	private boolean hasAnnotation(serviceClass) {
		for (method in serviceClass.methods) {
			if (method.isAnnotationPresent(SecuredService)) {
				return true
			}
		}

		return false
	}

	private def createRefList = { names ->
		names.collect { name -> ref(name) }
	}
}

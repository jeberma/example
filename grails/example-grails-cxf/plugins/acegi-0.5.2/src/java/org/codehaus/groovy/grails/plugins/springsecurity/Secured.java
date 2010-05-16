package org.codehaus.groovy.grails.plugins.springsecurity;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for Controllers at the class level or per-action, defining what roles
 * are required for the entire controller or action.
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Secured {

	/**
	 * Defines the security configuration attributes (e.g. ROLE_USER, ROLE_ADMIN, etc.)
	 * @return  the names of the roles
	 */
   String[] value();
}

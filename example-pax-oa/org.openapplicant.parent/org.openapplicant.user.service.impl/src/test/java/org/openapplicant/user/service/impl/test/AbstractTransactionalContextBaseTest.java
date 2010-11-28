package org.openapplicant.user.service.impl.test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration({"classpath:/META-INF/spring/spring.xml"})
public abstract class AbstractTransactionalContextBaseTest extends AbstractTransactionalJUnit4SpringContextTests{

}

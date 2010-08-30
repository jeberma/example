package com.example.gateway;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;

import com.example.domain.UserCredentials;
import com.example.domain.UserCredentialsBuilder;
import com.example.test.AbstractContextTest;
import com.example.test.NamingContext;

public class RepositoryGatewayTest extends AbstractContextTest {
	
	@Resource
	private RepositoryGateway gateway;
	
	private UserCredentials adminUser = new UserCredentialsBuilder()
												.username("admin")
												.password("admin")
												.build();
	
	@AfterClass
	public static void tearDownClass() throws Exception {
		NamingContext.getRepository().shutdown();
	}
	
	@Test
	public void isAuthenticated_should_return_true_if_a_user_exists_with_the_given_credentials() {
		boolean result = gateway.isAuthenticated(adminUser);
		assertTrue(result);
	}
	
	@Test
	public void isAuthenticated_should_return_false_if_a_user_does_not_exist_with_the_given_credentials() {
		boolean result = gateway.isAuthenticated(new UserCredentialsBuilder().build());
		assertFalse(result);
	}
	
	@Test
	public void createUser_should_create_a_user() {
		UserCredentials credentials = new UserCredentialsBuilder().build();
		gateway.createUser(adminUser, credentials);
		assertTrue(gateway.isAuthenticated(credentials));
	}
	
	@Test
	public void deleteUser_should_delete_a_user() {
		UserCredentials credentials = new UserCredentialsBuilder().build();
		gateway.createUser(adminUser, credentials);
		gateway.deleteUser(adminUser, credentials);
		assertFalse(gateway.isAuthenticated(credentials));
	}
	
	//@Ignore
	@Test
	public void createFolder_should_create_folder_node() throws Exception {
		UserCredentials credentials = new UserCredentialsBuilder().build();
		String path = "/foo"+credentials.getUsername();
		
		gateway.createUser(adminUser, credentials);
		gateway.createFolder(adminUser, path);
		gateway.makePrivate(adminUser, path);
		//gateway.addWriteAccess(adminUser, credentials, path);
		gateway.printNodes(credentials);
		
		UserCredentials user2 = new UserCredentialsBuilder().build();
		gateway.createUser(adminUser, user2);
		gateway.printNodes(user2);
	}
	
	@Ignore
	@Test
	public void printNodes() {
		UserCredentials credentials = new UserCredentialsBuilder().username("admin").password("admin").build();
		gateway.createUser(adminUser, credentials);
		gateway.printNodes(credentials);
	}

}

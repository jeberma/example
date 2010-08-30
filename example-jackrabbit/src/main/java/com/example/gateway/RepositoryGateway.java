package com.example.gateway;

import java.security.Principal;

import javax.annotation.Resource;
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.nodetype.NodeType;
import javax.jcr.security.AccessControlEntry;
import javax.jcr.security.AccessControlList;
import javax.jcr.security.AccessControlPolicy;
import javax.jcr.security.AccessControlPolicyIterator;
import javax.jcr.security.Privilege;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jackrabbit.api.JackrabbitSession;
import org.apache.jackrabbit.api.security.JackrabbitAccessControlList;
import org.apache.jackrabbit.api.security.principal.PrincipalManager;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.jackrabbit.core.security.SecurityConstants;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Component;

import com.example.domain.UserCredentials;

@Component
public class RepositoryGateway {
	
	private static final Log log = LogFactory.getLog(RepositoryGateway.class);
	
	private Repository repository;
	
	@Resource
	@Required
	public void setRepository (Repository value) {
		repository = value;
	}
	
	public boolean isAuthenticated(UserCredentials credentials) {
		Session session = null;
		try {
			login(credentials);
			return true;
		} catch(LoginException e) {
			return false;
		} catch(RepositoryException e) {
			throw convertException(e);
		} finally {
			logout(session);
		}
	}
	
	public void createUser(UserCredentials adminUser, UserCredentials credentials) {
		Session session = null;
		try {
			session = login(adminUser);
			UserManager userManager = getUserManager(session);
			Authorizable authorizable = userManager.getAuthorizable(credentials.getUsername());
			if(authorizable != null) {
				throw new DataIntegrityViolationException("user " + credentials.getUsername() + " already exists");
			} else {
				userManager.createUser(credentials.getUsername(), credentials.getPassword());
				session.save();
			}
		} catch(RepositoryException e) {
			throw convertException(e);
		} finally {
			logout(session);
		}
	}
	
	public void deleteUser(UserCredentials adminUser, UserCredentials credentials) {
		Session session = null;
		try {
			session = login(adminUser);
			UserManager userManager = getUserManager(session);
			Authorizable authorizable = userManager.getAuthorizable(credentials.getUsername());
			if(authorizable != null) {
				authorizable.remove();
				session.save();
			}
		} catch(RepositoryException e) {
			throw convertException(e);
		} finally {
			logout(session);
		}
	}
	
	public void createFolder(UserCredentials credentials, String absolutePath) {
		String path = relativize(absolutePath);
		Session session = null;
		try {
			session = login(credentials);
			if(session.getRootNode().hasNode(path)) {
				throw new DataIntegrityViolationException("node already exists at " + absolutePath);
			}
			session.getRootNode().addNode(path,NodeType.NT_FOLDER);
			session.save();
		} catch(RepositoryException e) {
			throw convertException(e);
		} finally {
			logout(session);
		}
	}
	
	public void makePrivate(UserCredentials credentials, String absolutePath) {
		Session session = null;
		try {
			session = login(credentials);
			Privilege[] readPrivs = new Privilege[]{session.getAccessControlManager().privilegeFromName(Privilege.JCR_READ)};
			JackrabbitAccessControlList acl = getJackrabbitAccessControlList(session, absolutePath);
			
			acl.addEntry(getPrincipalManager(session).getPrincipal(SecurityConstants.ANONYMOUS_ID), readPrivs, false);
			acl.addEntry(getPrincipalManager(session).getPrincipal(SecurityConstants.ADMIN_ID), readPrivs, false);
			acl.addEntry(getPrincipalManager(session).getEveryone(), readPrivs, false);
			//acl.addEntry(getPrincipalManager(session).getPrincipal(credentials.getUsername()), new Privilege[]{session.getAccessControlManager().privilegeFromName(Privilege.JCR_ALL)}, true);
			session.save();
		} catch(RepositoryException e) {
			throw convertException(e);
		} finally {
			logout(session);
		}
	}
	
	public void addWriteAccess(UserCredentials admin, UserCredentials target, String absolutePath) {
		Session session = null;
		try {
			session = login(admin);
			AccessControlList acl = getAccessControlList(session, absolutePath);
			Principal principal = getPrincipalManager(session).getPrincipal(target.getUsername());
			acl.addAccessControlEntry(
					principal,
					new Privilege[]{session.getAccessControlManager().privilegeFromName(Privilege.JCR_ALL)}
			);
			session.save();
		} catch(RepositoryException e) {
			throw convertException(e);
		} finally {
			logout(session);
		}
	}
	
	public void printNodes(UserCredentials credentials) {
		Session session = null;
		try {
			session = login(credentials);
			for(NodeIterator it = session.getRootNode().getNodes(); it.hasNext(); ) {
				Node node = it.nextNode();
				System.out.println(node);
			}
		} catch(RepositoryException e) {
			throw convertException(e);
		}
		finally {
			logout(session);
		}
	}
	
	private String relativize(String absolutePath) {
		return StringUtils.removeStart(absolutePath, "/");
	}
	
	private UserManager getUserManager(Session session) {
		try {
			return getJackrabbitSession(session).getUserManager();
		} catch(RepositoryException e) {
			throw convertException(e);
		}
	}
	
	private PrincipalManager getPrincipalManager(Session session) {
		try {
			return getJackrabbitSession(session).getPrincipalManager();
		} catch(RepositoryException e) {
			throw convertException(e);
		}
	}
	
	private JackrabbitSession getJackrabbitSession(Session session) {
		return (JackrabbitSession)session;
	}
	
	private JackrabbitAccessControlList getJackrabbitAccessControlList(Session session, String absolutePath) {
		return (JackrabbitAccessControlList)getAccessControlList(session, absolutePath);
	}
	
	private AccessControlList getAccessControlList(Session session, String absolutePath) {
		try {
			AccessControlPolicyIterator it = session.getAccessControlManager().getApplicablePolicies(absolutePath);
			while(it.hasNext()) {
				AccessControlPolicy policy = it.nextAccessControlPolicy();
				if(policy instanceof AccessControlList) {
					return (AccessControlList)policy;
				}
			}
			throw new DataRetrievalFailureException("Could not get AccessControlPolicy for path=" + absolutePath);
		} catch(RepositoryException e) {
			throw convertException(e);
		}
	}
	
	private Session login(UserCredentials cred) throws LoginException, RepositoryException {
		return repository.login(
				new SimpleCredentials(
						cred.getUsername(),
						StringUtils.defaultString(cred.getPassword()).toCharArray()
				)
		);
	}
		
	private DataAccessException convertException(Exception e) {
		return new DataRetrievalFailureException(e.getMessage(),e);
	}
	
	private void logout(Session session) {
		if(session != null) {
			session.logout();
		}
	}

}

package org.openapplicant.user.service.impl.persistence;

import org.openapplicant.common.persistence.hbm.HibernateGenericDao;
import org.openapplicant.user.service.impl.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends HibernateGenericDao<User, Long> {

	public UserDao() {
		super(User.class);
	}
}

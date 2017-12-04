package com.service.daoService.serviseDaoImpl;

import com.model.User;
import com.service.daoService.UserDao;
import com.service.daoService.generalDaoService.HibernateDao;
import org.springframework.stereotype.Repository;

@Repository("userDao")

public class UserDaoImpl extends HibernateDao<User,Long> implements UserDao{

}

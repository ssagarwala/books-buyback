package com.capstone.booksbuyback.model.data;

import com.capstone.booksbuyback.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserDao extends CrudRepository<User,Integer> {
}

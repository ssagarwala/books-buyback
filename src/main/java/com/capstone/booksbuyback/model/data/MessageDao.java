package com.capstone.booksbuyback.model.data;

import com.capstone.booksbuyback.model.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Repository
@Transactional
public interface MessageDao extends CrudRepository<Message, Integer> {
}

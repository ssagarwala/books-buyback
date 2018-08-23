package com.capstone.booksbuyback.model.data;

import com.capstone.booksbuyback.model.Zip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ZipDao extends CrudRepository<Zip,Integer> {
}


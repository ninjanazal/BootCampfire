package com.basedeveloper.jellylinkserver.account.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(value = "accountTransactionManager")
public interface GenderRepository extends GenderRepositoryInterface {

}

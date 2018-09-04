package com.itsfolarin.starter.springflowbase.backend.repo;

import com.itsfolarin.starter.springflowbase.backend.domain.PersistableRememberMeToken;
import com.itsfolarin.starter.springflowbase.backend.security.RememberMeToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RememberMeTokenRepository extends MongoRepository<PersistableRememberMeToken, Long> {

    RememberMeToken findBySeries(String series);

    void removeAllByUserName(String username);
}

package com.example.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Account;
import com.example.entity.Message;



@Repository
public interface AccountRepository extends JpaRepository<Account,Integer>{
    boolean existsByUsername(String username);
    Optional<Account> findByUsername(String username);
    List <Message> findByAccountId(Integer postedBy); 
    

}

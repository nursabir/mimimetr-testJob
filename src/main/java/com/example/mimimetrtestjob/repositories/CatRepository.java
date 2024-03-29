package com.example.mimimetrtestjob.repositories;

import com.example.mimimetrtestjob.model.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Cat c SET c.numberOfVotes = :numberOfVotes WHERE c.id = :catId")
    void updateNumberOfVotesById(Long catId, Integer numberOfVotes);


    @Query("SELECT c FROM Cat c ORDER BY c.numberOfVotes DESC")
    List<Cat> findTop10ByOrderByNumberOfVotesDesc();
}

package com.example.mimimetrtestjob.repositories;

import com.example.mimimetrtestjob.enums.VoteStatus;
import com.example.mimimetrtestjob.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Vote v SET v.voteStatus = :voteStatus WHERE v.id = :id")
    void updateVoteStatusById(Long id, VoteStatus voteStatus);
}

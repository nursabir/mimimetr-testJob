package com.example.mimimetrtestjob.service;



import com.example.mimimetrtestjob.dto.CatForVotingDTO;
import com.example.mimimetrtestjob.form.VoteForm;
import com.example.mimimetrtestjob.model.Cat;
import com.example.mimimetrtestjob.model.Vote;
import com.example.mimimetrtestjob.repositories.CatRepository;
import com.example.mimimetrtestjob.repositories.VoteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoteService {

    @Autowired
    CatRepository catRepository;

    @Autowired
    VoteRepository voteRepository;

    public List<CatForVotingDTO> getListOfCatForVoting(Long voteId) {
        Vote vote = voteRepository.findById(voteId).orElseThrow(
                () -> new EntityNotFoundException("Голосование не найдено: " + voteId));

        List<Cat> cats = vote.getCats();

        return cats.stream()
                .map(CatForVotingDTO::to)
                .collect(Collectors.toList());
    }

    public boolean registerVote(List<CatForVotingDTO> catForVotingDTOs) {
        try {
            for (CatForVotingDTO catForVotingDTO : catForVotingDTOs) {
                catRepository.updateNumberOfVotesById(catForVotingDTO.getCatId(), catForVotingDTO.getNumberOfVotes());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}

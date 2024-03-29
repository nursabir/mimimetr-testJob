package com.example.mimimetrtestjob.controller;

import com.example.mimimetrtestjob.dto.CatForVotingDTO;
import com.example.mimimetrtestjob.service.CatService;
import com.example.mimimetrtestjob.service.OrganizeVoteService;
import com.example.mimimetrtestjob.service.UserService;
import com.example.mimimetrtestjob.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@org.springframework.stereotype.Controller
public class VoterController {

    @Autowired
    private OrganizeVoteService organizeVoteService;

    @Autowired
    private VoteService voteService;
    @Autowired
    private UserService userService;

    @Autowired
    private CatService catService;


    @GetMapping("/voter")
    public String mainPage(Model model) {
        model.addAttribute("votingTitlesMap", organizeVoteService.allVotingTitlesMap());
        return "voter-main";
    }

    @GetMapping("/voter/{voter_id}")
    public String startVote(@PathVariable("voter_id") Long voterId, Model model) {
        model.addAttribute("cats_form", voteService.getListOfCatForVoting(voterId));
        return "do_voter";
    }

    @PostMapping("/register_vote/ofUser/{email}")
    public String register_vote(@PathVariable String email, @RequestBody List<CatForVotingDTO> catForVotingDTOs) {
        System.out.println("dfdf");
        voteService.registerVote(catForVotingDTOs);
        userService.save(email);
        return "do_voter";
    }

    @GetMapping("/top10_cats")
    public String getTop10Cats(Model model) {
        catService.getCatsTop().forEach(System.out::println);
        model.addAttribute("top10", catService.getCatsTop());
        return "do_voter";
    }


}

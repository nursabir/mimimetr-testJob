package com.example.mimimetrtestjob.controller;

import com.example.mimimetrtestjob.dto.VoteResponseDTO;
import com.example.mimimetrtestjob.form.SaveVotedCandidatesForm;
import com.example.mimimetrtestjob.service.OrganizeVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class OrganizerController {

    @Autowired
    private OrganizeVoteService organizeVoteService;


    @GetMapping("/organizeVoting")
    public String getMainAdminPage(Model model) {
        model.addAttribute("voteForms", organizeVoteService.getAllVotesDTO());
        return "organizer";
    }

    @PostMapping("/save-voting")
    public String saveVoting(SaveVotedCandidatesForm formData) {
        organizeVoteService.organizeVote(formData);
        return "organizer";
    }

    @PatchMapping("/complete-voting/{votingId}")
    public String complete(@PathVariable Long votingId){
        organizeVoteService.completeVoting(votingId);
        return "organizer";
    }

}

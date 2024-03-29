package com.example.mimimetrtestjob.form;

import com.example.mimimetrtestjob.dto.CatDTO;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteForm {

    private String username;
    private List<CatDTO> listOfVotedCats;
}

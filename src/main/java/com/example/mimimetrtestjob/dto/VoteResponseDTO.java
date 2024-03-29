package com.example.mimimetrtestjob.dto;


import com.example.mimimetrtestjob.enums.VoteStatus;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteResponseDTO {

    private Long voteId;
    private String votingTitle;
    private List<CatFormData> cats;
    private VoteStatus voteStatus;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CatFormData {
        private String catName;
        private String catPhotoPath;
        private Integer numberOfVotes;
    }
}
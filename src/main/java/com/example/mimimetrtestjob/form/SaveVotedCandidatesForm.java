package com.example.mimimetrtestjob.form;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveVotedCandidatesForm {

    private String votingTitle;
    private List<CatFormData> cats;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CatFormData {
        private String catName;
        private MultipartFile catPhoto;
    }
}

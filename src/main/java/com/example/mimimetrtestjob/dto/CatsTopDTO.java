package com.example.mimimetrtestjob.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CatsTopDTO {

    private String catName;
    private String captPhotoPath;
    private Integer numberOfVotes;

}

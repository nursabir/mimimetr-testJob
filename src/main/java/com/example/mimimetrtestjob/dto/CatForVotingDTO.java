package com.example.mimimetrtestjob.dto;

import com.example.mimimetrtestjob.model.Cat;
import lombok.*;
import org.springframework.stereotype.Repository;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter@Getter
public class CatForVotingDTO {
    private Long catId;
    private String catName;

    private String catPhotoPath;// Путь к файлу на диске
    private Integer numberOfVotes;

    public static CatForVotingDTO to(Cat cat) {
        CatForVotingDTO catForVotingDTO = new CatForVotingDTO();
        catForVotingDTO.setCatName(cat.getName());
        catForVotingDTO.setCatId(cat.getId());
        catForVotingDTO.setCatPhotoPath(cat.getCatFileInfo().getName());
        catForVotingDTO.setNumberOfVotes(cat.getNumberOfVotes());
        return catForVotingDTO;
    }
}

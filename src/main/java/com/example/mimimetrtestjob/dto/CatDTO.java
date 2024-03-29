package com.example.mimimetrtestjob.dto;

import com.example.mimimetrtestjob.model.Cat;
import com.example.mimimetrtestjob.model.CatFileInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CatDTO {
    private Long id;
    private String name;

}

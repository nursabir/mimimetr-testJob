package com.example.mimimetrtestjob.service;

import com.example.mimimetrtestjob.dto.CatsTopDTO;
import com.example.mimimetrtestjob.model.Cat;
import com.example.mimimetrtestjob.repositories.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatService {

    @Autowired
    private CatRepository catRepository;


    public List<CatsTopDTO> getCatsTop() {
        List<Cat> cats = catRepository.findTop10ByOrderByNumberOfVotesDesc();
        cats.forEach(System.out::println);
        return cats.stream()
                .map(cat -> CatsTopDTO.builder()
                        .catName(cat.getName())
                        .captPhotoPath(cat.getCatFileInfo().getName())
                        .numberOfVotes(cat.getNumberOfVotes())
                        .build())
                .collect(Collectors.toList());
    }


}

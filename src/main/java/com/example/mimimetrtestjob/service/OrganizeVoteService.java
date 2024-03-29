package com.example.mimimetrtestjob.service;

import com.example.mimimetrtestjob.dto.VoteResponseDTO;
import com.example.mimimetrtestjob.enums.VoteStatus;
import com.example.mimimetrtestjob.form.SaveVotedCandidatesForm;
import com.example.mimimetrtestjob.model.Cat;
import com.example.mimimetrtestjob.model.CatFileInfo;
import com.example.mimimetrtestjob.model.Vote;
import com.example.mimimetrtestjob.repositories.CatFileInfoRepository;
import com.example.mimimetrtestjob.repositories.CatRepository;
import com.example.mimimetrtestjob.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class OrganizeVoteService {

    @Value("${cat.photo.upload-dir}")
    private String catPhotoUploadDir;

    @Autowired
    private CatRepository catRepository;

    @Autowired
    private CatFileInfoRepository catFileInfoRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Transactional
    public void organizeVote(SaveVotedCandidatesForm form) {
        Vote vote = new Vote();
        vote.setName(form.getVotingTitle());

        // 2) Сохранение котиков и фотографий
        List<Cat> catikis = new ArrayList<>();
        for (SaveVotedCandidatesForm.CatFormData catFormData : form.getCats()) {
            Cat catiki = new Cat();
            catiki.setName(catFormData.getCatName());
            MultipartFile catPhoto = catFormData.getCatPhoto();

            // Сохранение информации о файле котика
            CatFileInfo catFileInfo = saveCatPhoto(catPhoto);

            // Сохранение котика с информацией о файле
            catiki.setCatFileInfo(catFileInfo);
            catiki.setNumberOfVotes(0);
            catiki.setVote(vote); // Устанавливаем голосование для кота
            catikis.add(catiki);
        }

        catRepository.saveAll(catikis);

        vote.setCats(catikis);
        vote.setVoteStatus(VoteStatus.ACTIVE);
        voteRepository.save(vote);
    }


    public List<VoteResponseDTO> getAllVotesDTO() {
        List<Vote> votes = voteRepository.findAll();
        List<VoteResponseDTO> voteResponseDTOList = new ArrayList<>();

        for (Vote vote : votes) {
            VoteResponseDTO voteResponseDTO = new VoteResponseDTO();
            voteResponseDTO.setVotingTitle(vote.getName());
            voteResponseDTO.setVoteId(vote.getId());
            voteResponseDTO.setVoteStatus(vote.getVoteStatus());

            List<Cat> cats = vote.getCats();
            List<VoteResponseDTO.CatFormData> catFormDataList = new ArrayList<>();

            for (Cat cat : cats) {
                VoteResponseDTO.CatFormData catFormData = new VoteResponseDTO.CatFormData();
                catFormData.setCatName(cat.getName());
                catFormData.setNumberOfVotes(cat.getNumberOfVotes());
                catFormData.setCatPhotoPath(cat.getCatFileInfo().getName());

                catFormDataList.add(catFormData);
            }

            voteResponseDTO.setCats(catFormDataList);
            voteResponseDTOList.add(voteResponseDTO);
        }

        return voteResponseDTOList;
    }

    public Map<Long, String> allVotingTitlesMap(){
        Map<Long, String> votingTitlesMap = new HashMap<>();
        List<VoteResponseDTO> voteForms = getAllVotesDTO();
        for (VoteResponseDTO voteForm : voteForms) {
            votingTitlesMap.put(voteForm.getVoteId(), voteForm.getVotingTitle());
        }
        return votingTitlesMap;
    }

    public void completeVoting(Long id){
        voteRepository.updateVoteStatusById(id, VoteStatus.FINISHED);
    }


    private CatFileInfo saveCatPhoto(MultipartFile catPhoto) {
        String fileName = generateUniqueFileName(catPhoto);
        Path uploadPath = Paths.get(catPhotoUploadDir);
        try {

            Files.createDirectories(uploadPath);

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(catPhoto.getInputStream(), filePath);

            CatFileInfo catFileInfo = new CatFileInfo();
            catFileInfo.setName(fileName);
            catFileInfo.setSize(catPhoto.getSize());
            catFileInfo.setKey(UUID.randomUUID().toString());
            return catFileInfo;
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

    private String generateUniqueFileName(MultipartFile file) {
        return UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
    }
}

package com.example.mimimetrtestjob.repositories;

import com.example.mimimetrtestjob.model.CatFileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatFileInfoRepository extends JpaRepository<CatFileInfo, Long> {
}

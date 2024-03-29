package com.example.mimimetrtestjob.model;

import com.example.mimimetrtestjob.enums.VoteStatus;
import com.example.mimimetrtestjob.model.Cat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Setter
public class Vote {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vote_name")
    private String name;

    @OneToMany(mappedBy = "vote", cascade = CascadeType.ALL)
    private List<Cat> cats; // Список котов, участвующих в голосовании

    @Enumerated
    private VoteStatus voteStatus;


}

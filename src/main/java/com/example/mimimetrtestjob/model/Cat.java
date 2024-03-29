package com.example.mimimetrtestjob.model;

import com.example.mimimetrtestjob.model.CatFileInfo;
import com.example.mimimetrtestjob.model.Vote;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cat_name")
    private String name;

    @Column(name = "number_votes")
    private Integer numberOfVotes;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vote_id")
    private Vote vote;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cat_file_info_id")
    private CatFileInfo catFileInfo;

}

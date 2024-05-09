package com.AbdoHalim.JobPortal.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    private String title;
    private String description;
    private String qualifications;
    private String country;
    private String city;
    private String benefits;
    private String responsibilities;
    private boolean pause;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(
            name = "job_resume",
            joinColumns = @JoinColumn(name = "jobId"),
            inverseJoinColumns = @JoinColumn(name = "resumeId")
    )
    private List<Resume> resumeList = new ArrayList<>();
    @ManyToMany(mappedBy = "savedJobs")
    @JsonIgnore
    private List<User> users = new ArrayList<>();
}


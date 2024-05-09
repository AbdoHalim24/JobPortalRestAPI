package com.AbdoHalim.JobPortal.Service;

import com.AbdoHalim.JobPortal.Entity.Job;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApplicantService {


    ResponseEntity<String> savejob(long id, String bearerToken);

    ResponseEntity<String> ApplyForJob(Long id, String token);

    List<Job> RetriveSavedJobs(String token);

    String deleteSavedJob(Long id, String token);
}

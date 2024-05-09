package com.AbdoHalim.JobPortal.Service;

import com.AbdoHalim.JobPortal.Entity.Job;
import com.AbdoHalim.JobPortal.Entity.Resume;
import com.AbdoHalim.JobPortal.Entity.User;
import com.AbdoHalim.JobPortal.Model.JobModel;
import com.AbdoHalim.JobPortal.Model.SearchModel;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface CompanyService {
    ResponseEntity<String> addJob(JobModel jobModel, String token);

    ResponseEntity<List<Resume>> retriveResumes(int id, String token);

    ResponseEntity<Resource> downloadResume(String file);

    List<Job> RetriveAllJobs(String token);

    ResponseEntity<String> DeleteJob(Long id, String token);

    ResponseEntity<String> EditeJob(Long id, JobModel jobModel, String token);

    ResponseEntity<String> PauseJob(Long id, String token);

    ResponseEntity<User> ViewProfile(Long userid, String token);

    List<Job> searchInCompany(SearchModel searchModel, String token);
}

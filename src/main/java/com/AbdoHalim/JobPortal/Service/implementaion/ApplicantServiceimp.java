package com.AbdoHalim.JobPortal.Service.implementaion;


import com.AbdoHalim.JobPortal.Entity.Job;
import com.AbdoHalim.JobPortal.Entity.Resume;
import com.AbdoHalim.JobPortal.Repository.JobRepo;
import com.AbdoHalim.JobPortal.Repository.ResumeRepo;
import com.AbdoHalim.JobPortal.Repository.UserRepo;
import com.AbdoHalim.JobPortal.Service.ApplicantService;
import com.AbdoHalim.JobPortal.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicantServiceimp implements ApplicantService {
    private UserService userService;
    private final UserRepo userRepo;
    private JobRepo jobRepo;
    private ResumeRepo resumeRepo;

    public ApplicantServiceimp(JobRepo jobRepo
                    , ResumeRepo resumeRepo, UserService userService, UserRepo userRepo) {
        this.jobRepo = jobRepo;
        this.resumeRepo=resumeRepo;
        this.userService=userService;
        this.userRepo = userRepo;
    }

    @Override
    public ResponseEntity<String> savejob(long id, String token) {
        Job job=jobRepo.GetJob((long) id);
        if(job==null){
            return ResponseEntity.badRequest().body("Job Dosen't Exist");
        }

        if (userService.CurrantUser(token).getSavedJobs().contains(job)){
            return ResponseEntity.ok("Job Saved");
        }
        userService.CurrantUser(token).getSavedJobs().add(job);
        userRepo.save(userService.CurrantUser(token));
        return ResponseEntity.ok("Job Saved");
    }

    @Override
    public ResponseEntity<String> ApplyForJob(Long id, String token) {
        Job job = jobRepo.GetJob(id);
        if (job == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Job Not Found");
        }
        if (userService.CurrantUser(token).getResume() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You don't have a resume");
        }
        Resume userResume = userService.CurrantUser(token).getResume();
        List<Resume> resumeList = job.getResumeList();
        if (resumeList.contains(userResume)) {
            return ResponseEntity.ok("You have already applied for this job");
        }
        resumeList.add(userResume);
        jobRepo.save(job);
        return ResponseEntity.ok("Applied Successfully");
    }
    @Override
    public List<Job>RetriveSavedJobs(String token) {
        List<Job> jobList=userService.CurrantUser(token).getSavedJobs();
        return jobList;
    }

    @Override
    public String deleteSavedJob(Long id, String token) {
        List<Job> jobList=userService.CurrantUser(token).getSavedJobs();
        Job job=jobRepo.GetJob(id);
        if (jobList.contains(job)){
            jobList.remove(job);
            userService.CurrantUser(token).setSavedJobs(jobList);;
            userRepo.save(userService.CurrantUser(token));
            return "Job Remove From Your List";
        }
        return "Job is Not in Your List";

    }


}

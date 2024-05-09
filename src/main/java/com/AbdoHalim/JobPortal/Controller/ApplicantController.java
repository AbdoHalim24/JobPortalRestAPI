package com.AbdoHalim.JobPortal.Controller;

import com.AbdoHalim.JobPortal.Entity.Job;
import com.AbdoHalim.JobPortal.Model.SearchModel;
import com.AbdoHalim.JobPortal.Service.ApplicantService;
import com.AbdoHalim.JobPortal.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applicant")
public class ApplicantController {
    private ApplicantService applicantService;
    private UserService userService;
    public ApplicantController(ApplicantService applicantService,UserService userService) {
        this.applicantService = applicantService;
        this.userService=userService;
    }
    @GetMapping("/job/{id}")
    public Job showjob(@PathVariable int id  ,@RequestHeader("Authorization") String token){
        return userService.FindJob((long) id);
    }
    //save job for future refrence
    @GetMapping("/savejob/{id}")
    public ResponseEntity<String> futureRefrenace(@PathVariable int id
            ,@RequestHeader("Authorization") String token){
         return applicantService.savejob((long) id,token);
    }
    @GetMapping("/apply/job/{id}")
    public ResponseEntity<String> applyForJob(@PathVariable Long id,@RequestHeader("Authorization") String token){
       return applicantService.ApplyForJob(id,token);

    }
    @GetMapping("/savedjobs")
    public List<Job> retrieveSavedJobs( @RequestHeader("Authorization") String token) {
        return applicantService.RetriveSavedJobs(token);
    }

    @GetMapping("/delete/saved/job/{id}")
    public String deleteSavedJob(@PathVariable Long id,@RequestHeader("Authorization") String token) {
        return applicantService.deleteSavedJob(id,token);

    }
    @PostMapping("/search")
    public ResponseEntity<List<Job>> searchForJob(@RequestBody SearchModel searchModel,@RequestHeader("Authorization") String token){
       return   userService.retriveJobs(searchModel,false);
    }

    @GetMapping("/jobs")
    public List<Job> RetriveAllJobs( @RequestHeader("Authorization") String token){
        return userService.retriveAllJobs();
    }
    @GetMapping("/company/jobs/{id}")
    public List<Job> RetriveAllCompanyJob(@PathVariable Long id,@RequestHeader("Authorization") String token){
       return userService.RetriveAllCompanyJobs(id);

    }


}

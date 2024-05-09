package com.AbdoHalim.JobPortal.Controller;

import com.AbdoHalim.JobPortal.Entity.Job;
import com.AbdoHalim.JobPortal.Entity.Resume;
import com.AbdoHalim.JobPortal.Entity.User;
import com.AbdoHalim.JobPortal.Model.JobModel;
import com.AbdoHalim.JobPortal.Model.SearchModel;
import com.AbdoHalim.JobPortal.Service.CompanyService;
import com.AbdoHalim.JobPortal.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {
    private  final CompanyService companyService;
    private final UserService userService;

    public CompanyController(CompanyService companyService, UserService userService) {
        this.companyService = companyService;
        this.userService = userService;
    }
    @GetMapping("/job/{id}")
    public Job ShowJob(@PathVariable long id){
        return userService.FindJob(id);
    }
    @PostMapping("/job")
    public ResponseEntity<String> addNewJob(@RequestBody JobModel jobModel  ,@RequestHeader("Authorization") String token){
        return companyService.addJob(jobModel,token);

    }
    @GetMapping("/jobs")
    public List<Job> RetriveAllCompanyJob(@RequestHeader("Authorization") String token ){
        return companyService.RetriveAllJobs(token);
    }
    @GetMapping("/delete/job/{id}")
    public ResponseEntity<String> DeleteJob(@PathVariable Long id,@RequestHeader("Authorization") String token){
        return  companyService.DeleteJob(id,token);
    }

    @PutMapping("/upadate/job/{id}")
    public ResponseEntity<String> EditeJob(@PathVariable Long id,@RequestHeader("Authorization") String token, @RequestBody JobModel jobModel){
      return companyService.EditeJob(id, jobModel,token);
    }
    // uses for pause and un pause a job
    @GetMapping("/pause/job/{id}")
    public ResponseEntity<String> pause(@PathVariable Long id,@RequestHeader("Authorization") String token){
      return companyService.PauseJob(id,token);
    }
    @GetMapping("/resumes/job/{id}")
    public ResponseEntity<List<Resume>> retriveallJobresume(@PathVariable int id,@RequestHeader("Authorization") String token ) {
       return companyService.retriveResumes(id,token);
    }
    @GetMapping("/profile/{userid}")
    public ResponseEntity<User> ViewUserProfile(@PathVariable Long userid,@RequestHeader("Authorization") String token){
        return companyService.ViewProfile(userid,token);
    }
    @PostMapping("/search")
    public List<Job> retriveCompanyJob(@ModelAttribute SearchModel searchModel,@RequestHeader("Authorization") String token){
     return companyService.searchInCompany(searchModel,token);
    }

}

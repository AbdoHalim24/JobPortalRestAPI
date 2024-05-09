package com.AbdoHalim.JobPortal.Service.implementaion;

import com.AbdoHalim.JobPortal.Entity.Job;
import com.AbdoHalim.JobPortal.Entity.Resume;
import com.AbdoHalim.JobPortal.Entity.User;
import com.AbdoHalim.JobPortal.Model.JobModel;
import com.AbdoHalim.JobPortal.Model.SearchModel;
import com.AbdoHalim.JobPortal.Repository.JobRepo;
import com.AbdoHalim.JobPortal.Repository.ResumeRepo;
import com.AbdoHalim.JobPortal.Repository.UserRepo;
import com.AbdoHalim.JobPortal.Service.ApplicantService;
import com.AbdoHalim.JobPortal.Service.CompanyService;
import com.AbdoHalim.JobPortal.Service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceimp implements CompanyService {
    public final JobRepo jobRepo;
    private final UserRepo userRepo;
    private final ResumeRepo resumeRepo;
    private  final UserService userService;
    private final ApplicantService applicantService;

    public CompanyServiceimp(JobRepo jobRepo, UserRepo userRepo, ResumeRepo resumeRepo, UserService userService, ApplicantService applicantService) {
        this.jobRepo = jobRepo;
        this.userRepo=userRepo;
        this.resumeRepo = resumeRepo;
        this.userService = userService;
        this.applicantService = applicantService;
    }

    @Override
    public ResponseEntity<String> addJob(JobModel jobModel, String token) {
        if (jobModel.getDescription().isEmpty()||jobModel.getCountry().isEmpty()||
            jobModel.getTitle().isEmpty()||jobModel.getCity().isEmpty()||
            jobModel.getQualifications().isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid credentials");

        Job job=new Job();

        job.setDescription(jobModel.getDescription());
        job.setTitle(jobModel.getTitle());
        job.setQualifications(jobModel.getQualifications());
        job.setCountry(jobModel.getCountry());
        job.setCity(jobModel.getCity());
        job.setUser(userService.CurrantUser(token));
        job.setBenefits(jobModel.getBenefits());
        job.setResponsibilities(jobModel.getResponsibilities());
        job.setPause(false);
        jobRepo.save(job);
        return ResponseEntity.status(HttpStatus.OK).body("Job Added");
    }

    @Override
    public ResponseEntity<List<Resume>> retriveResumes(int id, String token) {
        Optional<Job> job=jobRepo.findById((long) id);
        if (job.isPresent() && job.get().getUser()==userService.CurrantUser(token)) {
            List<Resume> resumes = resumeRepo.findAllbyJobId((long) id);
            return ResponseEntity.ok(resumes);
        }
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    @Value("${file.path}")
    private String filePath;
    @Override

    public ResponseEntity<jakarta.annotation.Resource> downloadResume(String file) {
        String dir = System.getProperty("user.dir") + "/" + filePath + "/" + file;
        Path path = Paths.get(dir);
        try {
            UrlResource resource = new UrlResource(path.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok().body((jakarta.annotation.Resource) resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public List<Job>RetriveAllJobs(String token) {
        List<Job> jobList=jobRepo.findAllJobsByUserId(userService.CurrantUser(token).getUserId());
        return jobList;
    }
    @Override
    public ResponseEntity<String> DeleteJob(Long id, String token) {
        Job job=jobRepo.GetJob(id);
        if (userService.CurrantUser(token).getCompanyJobs().contains(job)){
            List<Resume> resumeList=job.getResumeList();
            for (Resume resume:resumeList){
                resumeRepo.delete(resume);
            }
            jobRepo.deletejobById(id);
            return ResponseEntity.ok("Job Deleted Successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This Job Not Exist");
    }
    @Override
    public ResponseEntity<String> EditeJob(Long id, JobModel jobModel, String token) {
        if (jobModel.getDescription().isEmpty()||jobModel.getCountry().isEmpty()||
                jobModel.getTitle().isEmpty()||jobModel.getCity().isEmpty()||
                jobModel.getQualifications().isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid credentials");

        Job job=jobRepo.findByJobIdCompany(id);
        if (job.getUser().getUserId()!=userService.CurrantUser(token).getUserId())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not Found or Not Yours");

        job.setDescription(jobModel.getDescription());
        job.setTitle(jobModel.getTitle());
        job.setQualifications(jobModel.getQualifications());
        job.setCountry(jobModel.getCountry());
        job.setCity(jobModel.getCity());
        job.setUser(userService.CurrantUser(token));
        job.setBenefits(jobModel.getBenefits());
        job.setResponsibilities(jobModel.getResponsibilities());
        job.setPause(false);
        jobRepo.save(job);
        return ResponseEntity.status(HttpStatus.OK).body("Job Edited");
    }
    @Override
    public ResponseEntity<String> PauseJob(Long id, String token) {
        Optional<Job> job=jobRepo.findById(id);
        if (job.isPresent() && job.get().getUser()==userService.CurrantUser(token)){
            if (job.get().isPause()){
                job.get().setPause(false);
                jobRepo.save(job.get());
                return ResponseEntity.ok("Job Activated");
            }
            job.get().setPause(true);
            jobRepo.save(job.get());
            return ResponseEntity.ok("Pause the Job");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Job dosen't exist");

    }

    @Override
    public ResponseEntity<User> ViewProfile(Long userid, String token) {
        return ResponseEntity.ok(userRepo.findById(userid).get());
    }

    @Override
    public List<Job> searchInCompany(SearchModel searchModel, String token) {
       List<Job> jobList=jobRepo.Search(searchModel.getCountry(),searchModel.getCity(),searchModel.getTitle(),
               null,userService.CurrantUser(token).getUserId());


        return jobList;
    }
}

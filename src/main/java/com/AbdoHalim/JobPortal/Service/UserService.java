package com.AbdoHalim.JobPortal.Service;

import com.AbdoHalim.JobPortal.Entity.Job;
import com.AbdoHalim.JobPortal.Entity.User;
import com.AbdoHalim.JobPortal.Model.ChangePasswordModel;
import com.AbdoHalim.JobPortal.Model.LoginModel;
import com.AbdoHalim.JobPortal.Model.SearchModel;
import com.AbdoHalim.JobPortal.Model.UserModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
      ResponseEntity<String> uploadFile(MultipartFile file, String token) ;


    ResponseEntity<String> saveNewUser(UserModel userModel);

    User retriveUserByEmail(String email);
    public User CurrantUser(String jwt);

//    ResponseEntity<List<Job>> retriveJobs(SearchModel searchModel);


    ResponseEntity<List<Job>> retriveJobs(SearchModel searchModel, boolean b);

    ResponseEntity<String> UpdateInfo(UserModel userModel, String token);

    ResponseEntity<String> ChangePassword(ChangePasswordModel changePasswordModel, String token);

    List<Job>retriveAllJobs();

    Job FindJob(long id);

    User retriveUserById(Long id);

    List<Job> RetriveAllCompanyJobs(Long id);

    ResponseEntity<String> Login(LoginModel loginModel);
}

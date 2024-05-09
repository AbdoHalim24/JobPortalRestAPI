package com.AbdoHalim.JobPortal.Controller;

import com.AbdoHalim.JobPortal.Entity.User;
import com.AbdoHalim.JobPortal.Model.ChangePasswordModel;
import com.AbdoHalim.JobPortal.Model.UserModel;
import com.AbdoHalim.JobPortal.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile/{id}")
    public User showAccount(@PathVariable Long id){
       return userService.retriveUserById(id);

    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadResume(@RequestParam("newResume") MultipartFile file
            ,@RequestHeader("Authorization") String token) {
        return userService.uploadFile(file,token);
    }
    @PostMapping("/changepassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordModel changePasswordModel
            ,@RequestHeader("Authorization") String token) {
      return userService.ChangePassword(changePasswordModel,token);
    }
    @PutMapping("/updateinfo")
    public ResponseEntity<String> UpdateUserInfo(@RequestBody UserModel userModel
            ,@RequestHeader("Authorization") String token){
        return userService.UpdateInfo(userModel,token);
    }
}

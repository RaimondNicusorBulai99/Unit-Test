package Test.Unit.Test.controller;

import Test.Unit.Test.entities.User;
import Test.Unit.Test.repository.UserRepository;
import Test.Unit.Test.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("")
    public ResponseEntity createUser(@RequestBody User user){
        try {
            User newUser = userRepository.saveAndFlush(user);
            logger.info("Create an User");
            return ResponseEntity.status(HttpStatus.OK).body(newUser);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }
    }

    @GetMapping("/")
    public @ResponseBody List<User> getAllUser(){
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> userById(@PathVariable Long id){
        return userRepository.findById(id);
    }

    @PutMapping("/{id}")
    public void editUser(@PathVariable Long id, @RequestBody User user){
        userRepository.saveAndFlush(user);
    }
    @PutMapping("/{id}/activation")
    public @ResponseBody User setUserActive(@PathVariable long id, @RequestParam("activated") boolean activated){
        return userService.setUserActivationStatus(id,activated);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        userRepository.deleteById(id);
    }
}

package com.example.sudokuapp.controller;

import com.example.sudokuapp.entity.User;
import com.example.sudokuapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //新規登録画面の表示
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    //登録処理
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                                @RequestParam String password,
                                Model model) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            model.addAttribute("error", "このユーザー名はすでに使われています。");
            return "register";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return "redirect:/login";
    }
}
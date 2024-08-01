package com.vanlang.webbanxe.controller;

import com.vanlang.webbanxe.model.Account;
import com.vanlang.webbanxe.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    @GetMapping("/login")
    public String showLogin(){
        return "auth/login";
    }
    @GetMapping("/register")
    public String showRegister(){
        return "auth/register";
    }
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") Account account, // Validate đối tượng User
                           @NotNull BindingResult bindingResult, // Kết quả của quá trình validate
                           Model model) {
        if (bindingResult.hasErrors()) { // Kiểm tra nếu có lỗi validate
            var errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "auth/register"; // Trả về lại view "register" nếu có lỗi
        }
        accountService.save(account); // Lưu người dùng vào cơ sở dữ liệu
        accountService.setDefaultRole(account.getUsername()); // Gán vai trò mặc định cho người dùng
        return "redirect:/login"; // Chuyển hướng người dùng tới trang "login"
    }
}

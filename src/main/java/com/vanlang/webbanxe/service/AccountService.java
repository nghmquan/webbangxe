package com.vanlang.webbanxe.service;

import com.vanlang.webbanxe.Role;
import com.vanlang.webbanxe.model.Account;
import com.vanlang.webbanxe.repository.IAccountRepository;
import com.vanlang.webbanxe.repository.IRoleRepository;
import jakarta.validation.constraints.NotNull;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional
public class AccountService implements UserDetailsService {
        @Autowired
        private IAccountRepository userRepository;
        @Autowired
        private IRoleRepository roleRepository; // Lưu người dùng mới vào cơ sở dữ liệu sau khi mã hóa mật khẩu.

        public void save(@NotNull Account account) {
            account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));
            userRepository.save(account);
        }

        // Gán vai trò mặc định cho người dùng dựa trên tên người dùng.
        public void setDefaultRole(String username) {
            userRepository.findByUsername(username).ifPresentOrElse(user -> {
                user.getRoles().add(roleRepository.findRoleById(Role.USER.value));
                userRepository.save(user);
            }, () -> {
                throw new UsernameNotFoundException("User not found");
            });
        } // Tải thông tin chi tiết người dùng để xác thực.

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            var user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return org.springframework.security.core.userdetails.User.withUsername(user.getUsername()).password(user.getPassword()).authorities(user.getAuthorities()).accountExpired(!user.isAccountNonExpired()).accountLocked(!user.isAccountNonLocked()).credentialsExpired(!user.isCredentialsNonExpired()).disabled(!user.isEnabled()).build();
        }

        // Tìm kiếm người dùng dựa trên tên đăng nhập.
        public Optional<Account> findByUsername(String username) throws UsernameNotFoundException {
            return userRepository.findByUsername(username);
        }
    }



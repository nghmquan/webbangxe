package com.vanlang.webbanxe.controller;

import com.vanlang.webbanxe.model.Category;
import com.vanlang.webbanxe.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String getCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/categories/category-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/categories/add-category";
    }

    @PostMapping("/save")
    public String saveCategory(@ModelAttribute Category category, @RequestParam("image") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String imagePath = "images/"+file.getOriginalFilename();
            Path path = Paths.get("src/main/resources/static/" + imagePath);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            category.setImageName(imagePath);
        }

        categoryService.saveCategory(category);
        return "redirect:/categories";
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("category", categoryService.getCategoryById(id));
        return "admin/categories/add-category";
    }
    @PostMapping("/update")
    public String updateCategory(@ModelAttribute Category category, @RequestParam("image") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String imagePath = "images/" + file.getOriginalFilename();
            Path path = Paths.get("src/main/resources/static/" + imagePath);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            category.setImageName(imagePath);
        }
        categoryService.saveCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }
}
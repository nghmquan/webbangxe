package com.vanlang.webbanxe;

import com.vanlang.webbanxe.model.Category;
import com.vanlang.webbanxe.model.Product;
import com.vanlang.webbanxe.service.CategoryService;
import com.vanlang.webbanxe.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @GetMapping("/")
    public String hello(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        List<Product> products =productService.getAllProducts();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        return "layout";
    }
}

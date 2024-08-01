package com.vanlang.webbanxe.controller;
import com.vanlang.webbanxe.model.Category;
import com.vanlang.webbanxe.model.Product;
import com.vanlang.webbanxe.repository.OrderDetailRepository;
import com.vanlang.webbanxe.service.CategoryService;
import com.vanlang.webbanxe.service.ProductService;
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
import java.time.LocalDateTime;
import java.util.List;
@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @GetMapping
    public String getProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/products/product-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categories);
        return "admin/products/add-product";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            // Đặt đường dẫn lưu trữ tệp
            String imagePath = "uploads/" + file.getOriginalFilename();
            Path path = Paths.get("src/main/resources/static/" + imagePath);

            // Kiểm tra nếu thư mục chưa tồn tại thì tạo mới
            Files.createDirectories(path.getParent());

            // Sao chép tệp vào đường dẫn
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // Cập nhật đường dẫn hình ảnh vào đối tượng sản phẩm
            product.setImage(imagePath);

        }
        product.setUpdatedAt(LocalDateTime.now());
        productService.saveProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "admin/products/add-product";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute("product") Product product, @RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String imagePath = "uploads/" + file.getOriginalFilename();
            Path path = Paths.get("src/main/resources/static/" + imagePath);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            product.setImage(imagePath);
        }
        product.setUpdatedAt(LocalDateTime.now());
        productService.saveProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {

        // Xóa liên kết giữa order_details và sản phẩm trước khi xóa sản phẩm
        orderDetailRepository.removeProductId(id);

        // Xóa sản phẩm từ bảng products
        productService.deleteProduct(id);
        return "redirect:/products";
    }
    @GetMapping("/{productId}")
    public String getProductDetail(@PathVariable("productId") Long productId, Model model) {
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);
        return "products/product-detail"; // Tên view sẽ được hiển thị
    }
}

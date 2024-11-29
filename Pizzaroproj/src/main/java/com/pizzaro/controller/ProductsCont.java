package com.pizzaro.controller;

import com.pizzaro.controller.main.Main;
import com.pizzaro.model.Products;
import com.pizzaro.model.enums.Category;
import com.pizzaro.repo.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Controller
@RequestMapping("/products")
public class ProductsCont extends Main {

    @Autowired
    private ProductsRepo productsRepo;

    @GetMapping("/{category}")
    public String products(Model model, @PathVariable Category category) {
        AddAttributes(model);
        model.addAttribute("products", productsRepo.findAllByCategory(category));
        model.addAttribute("categories", Category.values());
        model.addAttribute("category", category);
        return "products";
    }

    @GetMapping("/add")
    public String productAdd(Model model) {
        AddAttributes(model);
        model.addAttribute("categories", Category.values());
        return "product_add";
    }

    @GetMapping("/edit/{id}")
    public String productEdit(Model model, @PathVariable Long id) {
        AddAttributes(model);
        model.addAttribute("product", productsRepo.getReferenceById(id));
        model.addAttribute("categories", Category.values());
        return "product_edit";
    }

    @GetMapping("/delete/{id}")
    public String productDelete(@PathVariable Long id) {
        productsRepo.deleteById(id);
        return "redirect:/products/PIZZA";
    }

    @PostMapping("/add")
    public String productAdd(Model model, @RequestParam String name, @RequestParam Category category, @RequestParam String structure, @RequestParam MultipartFile photo, @RequestParam int weight, @RequestParam float price) {
        String res = "";
        if (photo != null && !Objects.requireNonNull(photo.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = uuidFile + "_" + photo.getOriginalFilename();
                    photo.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (Exception e) {
                model.addAttribute("message", "Некорректный данные!");
                AddAttributes(model);
                model.addAttribute("categories", Category.values());
                return "product_add";
            }
        }

        productsRepo.save(new Products(name, res, weight, price, structure, category));
        return "redirect:/products/PIZZA";
    }

    @PostMapping("/edit/{id}")
    public String productEdit(Model model, @RequestParam String name, @RequestParam Category category, @RequestParam String structure, @RequestParam MultipartFile photo, @RequestParam int weight, @RequestParam float price, @PathVariable Long id) {
        Products product = productsRepo.getReferenceById(id);

        if (photo != null && !Objects.requireNonNull(photo.getOriginalFilename()).isEmpty()) {
            String res = "";
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = uuidFile + "_" + photo.getOriginalFilename();
                    photo.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (Exception e) {
                model.addAttribute("message", "Некорректный данные!");
                AddAttributes(model);
                model.addAttribute("product", productsRepo.getReferenceById(id));
                model.addAttribute("categories", Category.values());
                return "product_edit";
            }
            product.setPhoto(res);
        }

        product.set(name, weight, price, structure, category);
        productsRepo.save(product);
        return "redirect:/products/PIZZA";
    }

    @GetMapping("/{category}/filter")
    public String productFilter(
            Model model,
            @RequestParam(defaultValue = "0") float priceMin,
            @RequestParam(defaultValue = "0") float priceMax,
            @RequestParam(defaultValue = "0") int weightMin,
            @RequestParam(defaultValue = "0") int weightMax,
            @PathVariable Category category) {


        AddAttributes(model);
        model.addAttribute("category", category);


        List<Products> products = productsRepo.findAllByCategory(category);


        if (priceMin != 0 || priceMax != 0) {
            if (priceMin != 0) {
                products = products.stream()
                        .filter(product -> product.getPrice() >= priceMin)
                        .toList();
            }
            if (priceMax != 0) {
                products = products.stream()
                        .filter(product -> product.getPrice() <= priceMax)
                        .toList();
            }
        }

        if (weightMin != 0 || weightMax != 0) {
            if (weightMin != 0) {
                products = products.stream()
                        .filter(product -> product.getWeight() >= weightMin)
                        .toList();
            }
            if (weightMax != 0) {
                products = products.stream()
                        .filter(product -> product.getWeight() <= weightMax)
                        .toList();
            }
        }

        boolean hasResults = !products.isEmpty();

        model.addAttribute("hasResults", hasResults); // Флаг наличия результатов
        model.addAttribute("priceMin", priceMin);
        model.addAttribute("priceMax", priceMax);
        model.addAttribute("weightMin", weightMin);
        model.addAttribute("weightMax", weightMax);
        model.addAttribute("categorySelected", category);
        model.addAttribute("products", products);

        return "products";
    }


}
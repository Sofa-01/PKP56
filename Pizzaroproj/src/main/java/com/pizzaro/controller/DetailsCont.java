package com.pizzaro.controller;

import com.pizzaro.controller.main.Main;
import com.pizzaro.model.Details;
import com.pizzaro.model.Products;
import com.pizzaro.repo.DetailsRepo;
import com.pizzaro.repo.OrderingsRepo;
import com.pizzaro.repo.ProductsRepo;
import com.pizzaro.repo.StatsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/details")
public class DetailsCont extends Main {

    @Autowired
    private OrderingsRepo orderingsRepo;
    @Autowired
    private ProductsRepo productsRepo;
    @Autowired
    private DetailsRepo detailsRepo;

    @GetMapping("/{id}")
    public String details(Model model, @PathVariable Long id) {
        AddAttributes(model);
        model.addAttribute("ordering", orderingsRepo.getReferenceById(id));
        return "details";
    }

    @PostMapping("/add/{id}")
    public String detailAdd(@RequestParam int quantity, @PathVariable Long id) {
        Products product = productsRepo.getReferenceById(id);

        for (Details i : getUser().getDetails()) {
            if (i.getProduct() == product) {
                i.setQuantity(i.getQuantity() + quantity);
                i.setPrice(i.getPrice() + (quantity * product.getPrice()));
                detailsRepo.save(i);
                return "redirect:/products/PIZZA";
            }
        }

        Details detail = new Details(quantity, product.getPrice() * quantity);
        detail.setProduct(product);
        detail.setOwner(getUser());
        detailsRepo.save(detail);
        return "redirect:/products/PIZZA";
    }

    @GetMapping("/delete/{id}")
    public String detailDelete(@PathVariable Long id) {
        detailsRepo.deleteById(id);
        return "redirect:/orderings";
    }


}

package com.lab.mvc.springbootlist.controller;

import com.lab.mvc.springbootlist.dto.ProductRequestDTO;
import com.lab.mvc.springbootlist.dto.ProductResponseDTO;
import com.lab.mvc.springbootlist.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // 1. Listagem de Todos os Produtos (GET /products)
    @GetMapping
    public String listProducts(Model model) {
        List<ProductResponseDTO> products = service.listAll();
        model.addAttribute("listProducts", products);
        return "index";
    }

    // 2. Exibição do Formulário de Cadastro (GET /products/new)
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        // Inicializa o formulário com campos vazios
        model.addAttribute("product", new ProductRequestDTO(null, "", "", "", null));
        model.addAttribute("isEdit", false);
        return "new_product";
    }

    // 3. Salvar Registro - Inclusão ou Alteração (POST /products/save)
    @PostMapping("/save")
    public String saveProduct(
            @Valid @ModelAttribute("product") ProductRequestDTO product,
            BindingResult result,
            RedirectAttributes attr) {

        if (result.hasErrors()) {
            // Se tiver id, o erro ocorreu na tela de edição; senão, na tela de cadastro
            return (product.id() == null) ? "new_product" : "edit_product";
        }

        service.save(product);
        attr.addFlashAttribute("success", "Registro salvo com sucesso!");
        return "redirect:/products";
    }

    // 4. Exibição do Formulário de Edição (GET /products/edit/{id})
    @GetMapping("/edit/{id}")
    public String showEditProductPage(@PathVariable("id") Long id, Model model) {
        ProductResponseDTO response = service.get(id);

        // Preenche o DTO de formulário com os dados existentes para edição
        ProductRequestDTO product = new ProductRequestDTO(
                response.id(),
                response.nome(),
                response.marca(),
                response.paisFabricacao(),
                response.preco()
        );

        model.addAttribute("product", product);
        return "edit_product";
    }

    // 5. Exclusão de Registro (GET /products/delete/{id})
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes attr) {
        service.delete(id);
        attr.addFlashAttribute("success", "Registro excluído com sucesso!");
        return "redirect:/products";
    }
}

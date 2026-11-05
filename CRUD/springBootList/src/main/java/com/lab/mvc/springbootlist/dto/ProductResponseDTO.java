package com.lab.mvc.springbootlist.dto;

import com.lab.mvc.springbootlist.model.Product;

import java.math.BigDecimal;

public record ProductResponseDTO(
        Long id,
        String nome,
        String marca,
        String paisFabricacao,
        BigDecimal preco
) {
    // Construtor de conveniência a partir da Entidade JPA
    public ProductResponseDTO(Product product) {
        this(
                product.getId(),
                product.getNome(),
                product.getMarca(),
                product.getPaisFabricacao(),
                product.getPreco()
        );
    }
}

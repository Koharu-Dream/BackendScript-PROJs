package com.lab.mvc.springbootlist.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequestDTO(
        Long id,

        @NotBlank(message = "O nome do produto é obrigatório.")
        @Size(max = 60, message = "O nome do produto deve ter no máximo 60 caracteres.")
        String nome,

        @NotBlank(message = "A marca é obrigatória.")
        @Size(min = 2, max = 45, message = "A marca deve ter entre 2 e 45 caracteres.")
        String marca,

        @NotBlank(message = "O país de fabricação é obrigatório.")
        @Size(min = 3, max = 60, message = "O país deve ter entre 3 e 60 caracteres.")
        String paisFabricacao,

        @NotNull(message = "O preço é obrigatório.")
        @DecimalMin(value = "0.01", message = "O preço deve ser superior a zero.")
        BigDecimal preco
) {
}

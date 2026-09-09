package com.lab.jpa.sisbiblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "autores")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "nome do autor nao pode ser vazio")
    @Column(nullable = false, length = 100)
    private String nome;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch =
            FetchType.LAZY)
    @ToString.Exclude
    private List<Livro> livros = new ArrayList<>();
    public Autor(String nome) {
        this.nome = nome;
    }
}
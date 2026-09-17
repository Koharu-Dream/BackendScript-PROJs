package com.universidade.catalogo.domain.repository;
import com.universidade.catalogo.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
* Camada de acesso a dados e persistência para a entidade Produto.
* Herda operações fundamentais de CRUD, ordenação e paginação.
*/
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
// Métodos utilitários adicionais e Derived Queries podem ser declarados aqui:
// boolean existsByDescricao(String descricao);
}
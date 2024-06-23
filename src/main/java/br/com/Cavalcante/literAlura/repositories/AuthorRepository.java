package br.com.Cavalcante.literAlura.repositories;

import br.com.Cavalcante.literAlura.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long>{

    //derived query

    Boolean existsByNome(String nome);

    Author findByNome(String nome);


    //JPQL
    @Query("SELECT a FROM Author a WHERE a.dataDeFalecimento >= :ano AND :ano >= a.dataDeNascimento")
    List<Author> buscarPorAnoDeFalecimento(int ano);

    @Query("SELECT a FROM Author a WHERE a.nome ILIKE %:pesquisa%")
    List<Author> encontrarPorNome(String pesquisa);

    @Query("SELECT DISTINCT a.nome FROM Author a ORDER BY a.nome")
    List<String> pesquisaAutores();
}

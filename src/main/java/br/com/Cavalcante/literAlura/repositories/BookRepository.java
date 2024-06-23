package br.com.Cavalcante.literAlura.repositories;

import br.com.Cavalcante.literAlura.models.Author;
import br.com.Cavalcante.literAlura.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {

    //derived query
    boolean existsByNome(String nome);

    Book findByNome(String nome);

    List<Book> findTop10ByOrderByQuantidadeDeDownloadsDesc();


    //JPQL
    @Query("SELECT DISTINCT b.idioma FROM Book b ORDER BY b.idioma")
    List<String> pesquisarIdiomas();

    @Query("SELECT b FROM Book b WHERE idioma = :idioma")
    List<Book> buscarPorIdioma(String idioma);

    @Query("SELECT b FROM Book b WHERE b.autor.nome ILIKE %:pesquisa%")
    List<Book> encontrarLivrosPorAutor(String pesquisa);



}

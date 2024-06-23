package br.com.Cavalcante.literAlura.models;

import jakarta.persistence.*;

//Anotação informa que os objetivos serão persistidos no banco
@Entity

//Nome da tabela que será criada no banco
@Table(name= "livros")
public class Book {

    //atribuidos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    private String idioma;

    private Integer quantidadeDeDownloads;

    @ManyToOne
    private Author autor;

    //construidor padrao
    public Book(){}

    //Construidor Personalizado para serializar
    public Book(BookDados dados) {
        System.out.println(dados);
        System.out.println(":::Inserido No Banco de Dados Local.");
        this.nome = dados.nomeDoLivro();
        this.idioma = String.join(",",dados.idiomas());
        this.quantidadeDeDownloads = dados.quantidadeDeDownloads();
    }

    //Metodos getter/setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getQuantidadeDeDownloads() {
        return quantidadeDeDownloads;
    }

    public void setQuantidadeDeDownloads(Integer quantidadeDeDownloads) {
        this.quantidadeDeDownloads = quantidadeDeDownloads;
    }

    public Author getAutor() {
        return autor;
    }

    public void setAutor(Author autor) {
        this.autor = autor;
    }


    @Override
    public String toString() {
        return
                "\nNome: " + nome +
                "\nIdioma: " + idioma +
                "\nAutor: " + autor.getNome() +
                "\nQuantidade De Downloads: " + quantidadeDeDownloads +
                "\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=";

    }
}

package br.com.Cavalcante.literAlura.models;

import jakarta.persistence.*;
import org.hibernate.boot.jaxb.SourceType;

import java.util.ArrayList;
import java.util.List;

//Anotação informa que os objetivos serão persistidos no banco
@Entity

//Nome da tabela que será criada no banco
@Table(name = "autores")
public class Author {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    private int dataDeNascimento;

    private int dataDeFalecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> livros = new ArrayList<>();

    //construtor padrão
    public Author(){}

    //construtor personalisado para serializar
    public Author(AuthorDados dados) {
        System.out.println("Encontrado:::");
        System.out.println(dados);
        this.nome = dados.nomeDoAutor();
        this.dataDeNascimento = dados.anoDeNascimento();
        this.dataDeFalecimento = dados.anoDeFalecimento();
    }

    //metodos getter/setter
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

    public int getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(int dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public int getDataDeFalecimento() {
        return dataDeFalecimento;
    }

    public void setDataDeFalecimento(int dataDeFalecimento) {
        this.dataDeFalecimento = dataDeFalecimento;
    }

    public List<Book> getLivros() {
        return livros;
    }

    public void setLivros(List<Book> livros) {
        livros.forEach(l -> l.setAutor(this));
        this.livros = livros;
    }




    @Override
    public String toString() {
        return
                "\nNome: " + nome +
                "\nData De Nascimento: " + dataDeNascimento +
                "\nData De Falecimento: " + dataDeFalecimento+
                "\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=";
    }
}

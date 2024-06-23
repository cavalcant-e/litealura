package br.com.Cavalcante.literAlura.main;

import br.com.Cavalcante.literAlura.models.Author;
import br.com.Cavalcante.literAlura.models.AuthorDados;
import br.com.Cavalcante.literAlura.models.Book;
import br.com.Cavalcante.literAlura.models.BookDados;
import br.com.Cavalcante.literAlura.repositories.BookRepository;
import br.com.Cavalcante.literAlura.services.ApiRequest;
import br.com.Cavalcante.literAlura.services.DataConverter;
import br.com.Cavalcante.literAlura.repositories.AuthorRepository;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private Scanner leitura = new Scanner(System.in);
   
    private AuthorRepository repositorioAutor;
    private BookRepository repositorioLivro;
    private List<Book> books = new ArrayList<>();
    private DataConverter conversor = new DataConverter();
   

    public Main(AuthorRepository repositorioAutor, BookRepository repositorioLivro) {
        this.repositorioAutor = repositorioAutor;
        this.repositorioLivro = repositorioLivro;
    }

    public void principal(){
        String menu = """
                =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
               :::::::: Menu LiteAlura ::::::::
                -----------------------------------------------------------------------
                1- Buscar livro pelo titulo;
                2- Livros pesquisados e registrados;
                3- Autores registrados;
                4- Autores que estavam vivos no ano informado;
                5- Livros registros por idioma;
                6- Top 10 livros mais baixados;
                7- Buscar autores por nome;
                8- Media de downloads por autor;
                
                0 - Sair
                =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                
                Digite o numero da opção desejada:
                """;
        var opcao = -1;
        while (opcao != 0){
            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao){
                case 1:
                    pesquisarNovoLivro();
                    break;
                case 2:
                    pesquisarLivrosRegistrados();
                    break;
                case 3:
                    pesquisarAutoresRegistrados();
                    break;
                case 4:
                    pesquisarAutoresVivosPorAno();
                    break;
                case 5:
                    pesquisarLivrosPorIdioma();
                    break;
                case 6:
                    pesquisarTop10();
                    break;
                case 7:
                    pesquisarAutorPorNome();
                    break;
                case 8:
                    mediaDeDownlaodsPorAutor();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("\n\n***Opção Inválida***\n\n");
            }
        }

    }

    private void pesquisarNovoLivro() {
        System.out.println("\nQual livro deseja buscar?");
        var buscar = leitura.nextLine();
        ApiRequest conexao = new ApiRequest();
        var dados = conexao.consumoApi("https://gutendex.com/books?search="+ buscar.replace(" ","%20"));
        salvarNoDb(dados);
    }

    private void salvarNoDb(String dados){
        try{
            //Converter os dados no Jackson e ja carrega os atribuidos da classe Author
            Author autor = new Author(conversor.pegarDados(dados, AuthorDados.class));
            Author autorDados = null;

            //verificar se ja existe no banco
            if (!repositorioAutor.existsByNome(autor.getNome())){
               repositorioAutor.save(autor);
              autorDados = autor;
            }else{
                autorDados = repositorioAutor.findByNome(autor.getNome());
            }

            //Converter os dados no Jackson e ja ja carrega os atribuidos da classe Book
            Book livro = new Book(conversor.pegarDados(dados, BookDados.class));
            Book bookDados = null;

            //verificar se ja existe no banco
            if (!repositorioLivro.existsByNome(livro.getNome())){
                livro.setAutor(autorDados);
                repositorioLivro.save(livro);
                bookDados = livro;
            }else{
                bookDados = repositorioLivro.findByNome(livro.getNome());
                System.out.println("\nLivro ja registrado no Banco de Dados");
            }
            System.out.println(bookDados);
        }catch (NullPointerException e){
            System.out.println("\n*** Livro não encontrado ***\n");
        }

    }


    private void pesquisarLivrosRegistrados() {
        //buscar tudo
        var bucaslivros = repositorioLivro.findAll();
        //verificar se a busca está vazia
        if(!bucaslivros.isEmpty()){
            bucaslivros.forEach(System.out::println);
        }else{
            System.out.println("Banco de dados vazio, realize a busca na opção 1");
        }

    }

    private void pesquisarAutoresRegistrados() {
        //buscar tudo
        var buscaBanco = repositorioAutor.findAll();
        //verificar se a busca está vazia
        if(!buscaBanco.isEmpty()){
            exibirNomeAutores();

        }else{
            System.out.println("Banco de dados vazio, realize a busca na opção 1");
        }
    }

    private void pesquisarAutoresVivosPorAno() {
        System.out.println("\nDigite ano deseja pesquisar?");
        var ano = leitura.nextInt();
        //Busca autor vivo no ano informado
        var pesquisaNoBanco = repositorioAutor.buscarPorAnoDeFalecimento(ano);

        //verificar se a busca está vazia
        if(!pesquisaNoBanco.isEmpty()){
            pesquisaNoBanco.forEach(System.out::println);
        }else {
            System.out.println("\nNenhum autor vivo no ano informado.");
        }
    }

    private void pesquisarLivrosPorIdioma() {
        //busca se idomas que são diferentes
        var pesquisaIdioma = repositorioLivro.pesquisarIdiomas();
        System.out.println("\nIdiomas cadastrados no banco:");
        pesquisaIdioma.forEach(System.out::println);

        System.out.println("\nEsolha o idioma: ");
        var idioma = leitura.nextLine();
        repositorioLivro.buscarPorIdioma(idioma).forEach(System.out::println);
    }

    private void pesquisarTop10() {
        //busca TOP10 com base em download
        var top10 = repositorioLivro.findTop10ByOrderByQuantidadeDeDownloadsDesc();
        top10.forEach(System.out::println);
    }

    private void pesquisarAutorPorNome() {
        //Exbibe os autores
        exibirNomeAutores();
        System.out.println("\nInforme o nome do autor?");
        var pesquisa = leitura.nextLine();
        //Busca o autor com base na entrada do usuario
        var autor = repositorioAutor.encontrarPorNome(pesquisa);

        //verifica se está vazio
        if (!autor.isEmpty()){
            autor.forEach(System.out::println);
          String nome = autor.get(0).getNome();
            pesquisarLivroPorAutor(nome);
        }else{
            System.out.println("Não encontrado.");
        }
    }

    private void pesquisarLivroPorAutor(String autor) {
        //busca o livro pelo nome do autor
        var pesquisaAutor = repositorioLivro.encontrarLivrosPorAutor(autor);

        //realiza os filtros dos dados retornado da consulta
        var livros = pesquisaAutor.stream()
                .map(Book::getNome)
                .collect(Collectors.toList());

        //imprimi após os filtros realizados
                livros.forEach(l-> {
                Formatter formato = new Formatter(System.out);
                formato.format("Livro do autor: %s\n", l);}) ;

    }

    private void mediaDeDownlaodsPorAutor() {
        //Exbibe os autores
        exibirNomeAutores();
        System.out.println("\nInforme o autor deseja buscar?");
        var pesquisa = leitura.nextLine();

        //Busca pelo autor
        var pesquisaAutor = repositorioLivro.encontrarLivrosPorAutor(pesquisa);

        //filtros com os dados retornados da consulta
        DoubleSummaryStatistics media = pesquisaAutor.stream()
                .mapToDouble(Book::getQuantidadeDeDownloads)
                .summaryStatistics();
        System.out.println("Média de Downloads: "+ media.getAverage());
    }
    public void exibirNomeAutores(){

        //consultar nomes dos autores
         var listaAutores = repositorioAutor.pesquisaAutores();
         System.out.println("Autor(es) no Banco de Dados:\n");

         //imprimi as informações retornadas da consulta
         listaAutores.forEach(a-> {
            Formatter formato = new Formatter(System.out);
            formato.format("Autor: %s\n", a);
        }
        );
 }

}

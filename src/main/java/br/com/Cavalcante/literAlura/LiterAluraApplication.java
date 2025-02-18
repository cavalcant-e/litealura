package br.com.Cavalcante.literAlura;

import br.com.Cavalcante.literAlura.main.Main;
import br.com.Cavalcante.literAlura.repositories.BookRepository;
import br.com.Cavalcante.literAlura.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {
	@Autowired
	AuthorRepository autorRepository;
	@Autowired
	BookRepository bookRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(autorRepository,bookRepository);
		main.principal();
	}
}

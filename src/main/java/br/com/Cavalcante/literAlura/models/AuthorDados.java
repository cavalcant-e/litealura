package br.com.Cavalcante.literAlura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


//serializar/desserializar com Jackson
@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorDados(@JsonAlias("name")String nomeDoAutor,
                          @JsonAlias("birth_year") Integer anoDeNascimento,
                          @JsonAlias("death_year") Integer anoDeFalecimento)
{
}

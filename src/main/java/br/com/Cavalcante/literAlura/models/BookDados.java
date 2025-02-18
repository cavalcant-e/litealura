package br.com.Cavalcante.literAlura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

//serializar/desserializar com Jackson
@JsonIgnoreProperties(ignoreUnknown = true)
public record BookDados(@JsonAlias("title")String nomeDoLivro,
                        @JsonAlias("download_count") Integer quantidadeDeDownloads,
                        @JsonAlias("languages") List<String> idiomas) {
}

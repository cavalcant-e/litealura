package br.com.Cavalcante.literAlura.services;

import br.com.Cavalcante.literAlura.models.AuthorDados;
import br.com.Cavalcante.literAlura.models.BookDados;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConverter implements IDataConverter {
    private ObjectMapper mapper = new ObjectMapper();

//realizar a conversao dos dados retornados da consulta api para o formato definido nas classe AuthorDados e BookDados
    @Override
    public <T> T pegarDados(String json, Class<T> classe) {
        T resultado = null;
        try {
            if (classe == BookDados.class) {
                JsonNode node = mapper.readTree(json);
                var s = node.get("results").get(0);
                resultado = mapper.treeToValue(s, classe);

            }else if (classe == AuthorDados.class) {
                JsonNode node = mapper.readTree(json);
                var s = node.get("results").get(0).get("authors").get(0);
                resultado = mapper.treeToValue(s, classe);

            }else {
                resultado = mapper.readValue(json, classe);
            }
        }catch (JsonProcessingException e){
            e.getStackTrace();
            throw new RuntimeException(e);
        }
        return resultado;
    }
}

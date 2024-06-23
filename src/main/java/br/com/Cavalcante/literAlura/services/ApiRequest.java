package br.com.Cavalcante.literAlura.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

//
public class ApiRequest {
    public String consumoApi(String endereco){

        //requisições à API
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        //controle da requisicao
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();

        //resposta da api
        HttpResponse<String> response = null;
        try{
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        }catch (IOException|InterruptedException e){
            throw new RuntimeException(e);
        }
        var json = response.body();
        return json;
    }
}

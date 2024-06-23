package br.com.Cavalcante.literAlura.services;

public interface IDataConverter{
    <T> T pegarDados(String json, Class<T> classe);
}

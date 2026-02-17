package br.com.imobmatch.api.exceptions.favorite;

public class FavoriteNotFoundException extends RuntimeException {
    public FavoriteNotFoundException() {
        super("Favorite not found");
    }
}

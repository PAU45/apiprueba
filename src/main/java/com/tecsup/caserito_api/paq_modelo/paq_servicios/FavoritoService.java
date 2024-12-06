package com.tecsup.caserito_api.paq_modelo.paq_servicios;

import com.tecsup.caserito_api.paq_web.paq_dto.FavoritoRequest;
import com.tecsup.caserito_api.paq_web.paq_dto.RestaurantResponse;

import java.util.List;

public interface FavoritoService {
    boolean agregarFavorito(FavoritoRequest favoritoRequest);

    List<RestaurantResponse> getFavoritosDelUsuario();
}

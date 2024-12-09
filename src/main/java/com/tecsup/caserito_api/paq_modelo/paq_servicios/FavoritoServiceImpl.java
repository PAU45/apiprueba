package com.tecsup.caserito_api.paq_modelo.paq_servicios;

import com.tecsup.caserito_api.paq_modelo.paq_daos.FavoritoRepository;
import com.tecsup.caserito_api.paq_modelo.paq_daos.RestauranteRepository;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Favorito;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Restaurante;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Usuario;
import com.tecsup.caserito_api.paq_web.paq_dto.FavoritoRequest;
import com.tecsup.caserito_api.paq_web.paq_dto.RestaurantResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoritoServiceImpl implements FavoritoService{

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private GeocodingService geocodingService;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private AuthService authService;

    @Override
    public boolean agregarFavorito(FavoritoRequest favoritoRequest){
        Usuario usuario =authService.getAuthenticatedUser();
        Restaurante restaurante = restauranteRepository.findById(favoritoRequest.restauranteId()).orElseThrow(()->new RuntimeException("Restaurante no encontrado"));

        Favorito favorito = new Favorito();
        favorito.setUsuario(usuario);
        favorito.setRestaurante(restaurante);
        favoritoRepository.save(favorito);
        return true;
    }

    @Override
    public List<RestaurantResponse> getFavoritosDelUsuario() {
        // Obtener el usuario autenticado
        Usuario usuario = authService.getAuthenticatedUser();

        // Obtener los favoritos del usuario
        List<Favorito> favoritos = favoritoRepository.findByUsuario(usuario);

        // Convertir Favorito a RestaurantResponse
        return favoritos.stream().map(favorito -> {
            Restaurante restaurante = favorito.getRestaurante();

            // Obtener las coordenadas del usuario y del restaurante
            double latUsuario = usuario.getLatitud();
            double lngUsuario = usuario.getLongitud();

            double latRestaurante = restaurante.getLatitud();
            double lngRestaurante = restaurante.getLongitud();

            // Comprobar si las coordenadas del usuario son válidas
            if (latUsuario == 0.0 || lngUsuario == 0.0) {
                // Si las coordenadas no son válidas (0.0), asignamos valores nulos para la distancia y el tiempo
                return new RestaurantResponse(
                        restaurante.getPk_restaurante(),
                        restaurante.getNombre(),
                        restaurante.getDescripcion(),
                        restaurante.getUbicacion(),
                        restaurante.getTipo(),
                        restaurante.getImg(),
                        null,  // Distancia nula
                        null   // Tiempo nulo
                );
            }

            // Si las coordenadas son válidas, obtenemos la distancia y el tiempo
            String direccionInfo = geocodingService.getDirections(latUsuario, lngUsuario, latRestaurante, lngRestaurante);

            // Extraer la distancia y duración de la respuesta
            String[] info = direccionInfo.split(", ");
            String distancia = info[0].replace("Distancia: ", "");
            String duracion = info[1].replace("Duración: ", "");

            // Crear el objeto RestaurantResponse con los datos adicionales
            return new RestaurantResponse(
                    restaurante.getPk_restaurante(),
                    restaurante.getNombre(),
                    restaurante.getDescripcion(),
                    restaurante.getUbicacion(),
                    restaurante.getTipo(),
                    restaurante.getImg(),
                    distancia,  // Incluir distancia como String
                    duracion    // Incluir duración como String
            );
        }).toList();
    }



}

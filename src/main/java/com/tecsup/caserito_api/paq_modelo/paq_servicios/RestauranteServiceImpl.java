package com.tecsup.caserito_api.paq_modelo.paq_servicios;

import com.tecsup.caserito_api.paq_exception.ErrorResponse;
import com.tecsup.caserito_api.paq_exception.RestauranteExistenteException;
import com.tecsup.caserito_api.paq_modelo.paq_daos.RestauranteRepository;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Restaurante;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Usuario;
import com.tecsup.caserito_api.paq_web.paq_dto.RestaurantResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestauranteServiceImpl implements RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private GeocodingService geocodingService;

    @Autowired
    private AuthService authService;


    @Override
    public Restaurante createRestaurante(Restaurante restaurante) {

        Usuario usuario = authService.getAuthenticatedUser();

        // Verificar si ya existe un restaurante con el mismo nombre en general (no importa el usuario)
        Optional<Restaurante> existingRestaurante = restauranteRepository.findByNombre(restaurante.getNombre());
        if (existingRestaurante.isPresent()) {
            throw new RestauranteExistenteException("El restaurante con el nombre '" + restaurante.getNombre() + "' ya existe.");
        }

        // Asignar el usuario al restaurante
        restaurante.setUsuario(usuario);

        // Obtener las coordenadas en base a la ubicación proporcionada
        double[] coordinates = geocodingService.getCoordinates(restaurante.getUbicacion());
        restaurante.setLatitud(coordinates[0]);
        restaurante.setLongitud(coordinates[1]);

        // Guardar el restaurante
        return saveOrUpdateRestaurante(restaurante);
    }


    public List<RestaurantResponse> getAllRestaurantes() {
        // Obtener el usuario autenticado
        Usuario usuario = authService.getAuthenticatedUser();

        // Mapear la lista de Restaurantes a RestaurantResponse usando stream
        return restauranteRepository.findAll().stream()
                .map(restaurante -> {
                    // Obtener las coordenadas del usuario y del restaurante
                    double latUsuario = usuario.getLatitud();
                    double lngUsuario = usuario.getLongitud();

                    double latRestaurante = restaurante.getLatitud();
                    double lngRestaurante = restaurante.getLongitud();

                    // Verificar si las coordenadas del usuario son válidas
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

                    // Llamar al servicio getDirections para obtener la distancia y el tiempo estimado
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
                            distancia,      // Incluir distancia
                            duracion        // Incluir duración
                    );
                })
                .collect(Collectors.toList());
    }



    @Override
    public Restaurante getRestauranteById(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado"));
    }

    @Override
    public ResponseEntity<?> deleteRestaurante(Long id) {
        try {
            Usuario usuario = authService.getAuthenticatedUser();

            // Buscar el restaurante por ID
            Restaurante restaurante = restauranteRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Restaurante no encontrado con el id: " + id));

            // Verificar si el restaurante pertenece al usuario autenticado
            if (!restaurante.getUsuario().getPk_Usuario().equals(usuario.getPk_Usuario())) {
                throw new RuntimeException("No tienes permiso para eliminar este restaurante.");
            }

            // Eliminar el restaurante
            restauranteRepository.delete(restaurante);

            // Retornar mensaje de éxito
            return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse("Restaurante eliminado exitosamente"));

        } catch (Exception e) {
            // En caso de error, retornar un mensaje de error en formato JSON
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }




    private Restaurante saveOrUpdateRestaurante(Restaurante restaurante) {
        return restauranteRepository.save(restaurante);
    }


    @Override
    public List<Restaurante> getRestaurantesPorUsuario() {
        // Obtener el usuario autenticado usando AuthService
        Usuario usuario = authService.getAuthenticatedUser();

        // Retornar los restaurantes asociados al usuario autenticado
        return restauranteRepository.findByUsuario(usuario);
    }


    @Override
    public Restaurante updateRestaurante(Long id, Restaurante restauranteDetalles) {
        // Obtener el usuario autenticado usando AuthService
        Usuario usuario = authService.getAuthenticatedUser();

        // Buscar el restaurante por ID
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado"));

        // Verificar si el restaurante pertenece al usuario autenticado
        if (!restaurante.getUsuario().getPk_Usuario().equals(usuario.getPk_Usuario())) {
            throw new RuntimeException("No tienes permiso para actualizar este restaurante.");
        }

        // Verificar y actualizar campos solo si son diferentes
        if (restauranteDetalles.getNombre() != null && !restauranteDetalles.getNombre().equals(restaurante.getNombre())) {
            // Verificar si ya existe un restaurante con el mismo nombre
            restauranteRepository.findByNombre(restauranteDetalles.getNombre()).ifPresent(existingRestaurante -> {
                throw new RestauranteExistenteException("El restaurante con el nombre '" + restauranteDetalles.getNombre() + "' ya existe.");
            });
            restaurante.setNombre(restauranteDetalles.getNombre());
        }

        if(restauranteDetalles.getTipo() != null && !restauranteDetalles.getTipo().equals(restaurante.getTipo())) {
            restaurante.setTipo(restauranteDetalles.getTipo());
        }
        if(restauranteDetalles.getImg() != null && !restauranteDetalles.getImg().equals(restaurante.getImg())) {
            restaurante.setImg(restauranteDetalles.getImg());
        }
        if (restauranteDetalles.getDescripcion() != null && !restauranteDetalles.getDescripcion().equals(restaurante.getDescripcion())) {
            restaurante.setDescripcion(restauranteDetalles.getDescripcion());
        }

        if (restauranteDetalles.getUbicacion() != null && !restauranteDetalles.getUbicacion().equals(restaurante.getUbicacion())) {
            restaurante.setUbicacion(restauranteDetalles.getUbicacion());

            // Actualizar coordenadas si la ubicación cambió
            double[] coordinates = geocodingService.getCoordinates(restauranteDetalles.getUbicacion());
            restaurante.setLatitud(coordinates[0]);
            restaurante.setLongitud(coordinates[1]);
        }


        if (restauranteDetalles.getFk_menu() != null && !restauranteDetalles.getFk_menu().equals(restaurante.getFk_menu())) {
            restaurante.setFk_menu(restauranteDetalles.getFk_menu());
        }

        if (restauranteDetalles.getFk_detalle() != null && !restauranteDetalles.getFk_detalle().equals(restaurante.getFk_detalle())) {
            restaurante.setFk_detalle(restauranteDetalles.getFk_detalle());
        }

        // Guardar y retornar el restaurante actualizado
        return saveOrUpdateRestaurante(restaurante);
    }




}


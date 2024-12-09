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
    private CalificacionService calificacionService;

    @Autowired
    private AuthService authService;


    @Override
    public String createRestaurante(Restaurante restaurante) {

        Usuario usuario = authService.getAuthenticatedUser();

        Optional<Restaurante> existingRestaurante = restauranteRepository.findByNombre(restaurante.getNombre());
        if (existingRestaurante.isPresent()) {
            throw new RestauranteExistenteException("El restaurante con el nombre '" + restaurante.getNombre() + "' ya existe.");
        }

        restaurante.setUsuario(usuario);

        double[] coordinates = geocodingService.getCoordinates(restaurante.getUbicacion());
        restaurante.setLatitud(coordinates[0]);
        restaurante.setLongitud(coordinates[1]);

        saveOrUpdateRestaurante(restaurante);

        return "Restaurante creado exitosamente.";
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
                                restaurante.getHoraApertura(),
                                restaurante.getHoraCierre(),
                                null,  // Distancia nula
                                null,   // Tiempo nulo
                                0.0    // Calificación promedio 0.0 si no tiene calificaciones
                        );
                    }

                    // Llamar al servicio getDirections para obtener la distancia y el tiempo estimado
                    String direccionInfo = geocodingService.getDirections(latUsuario, lngUsuario, latRestaurante, lngRestaurante);

                    // Extraer la distancia y duración de la respuesta
                    String[] info = direccionInfo.split(", ");
                    String distancia = info[0].replace("Distancia: ", "");
                    String duracion = info[1].replace("Duración: ", "");

                    // Calcular el promedio de las calificaciones
                    double promedioCalificacion = calificacionService.calcularPromedioCalificaciones(restaurante.getPk_restaurante());

                    // Crear el objeto RestaurantResponse con los datos adicionales
                    return new RestaurantResponse(
                            restaurante.getPk_restaurante(),
                            restaurante.getNombre(),
                            restaurante.getDescripcion(),
                            restaurante.getUbicacion(),
                            restaurante.getTipo(),
                            restaurante.getImg(),
                            restaurante.getHoraApertura(),
                            restaurante.getHoraCierre(),
                            distancia,      // Incluir distancia
                            duracion,       // Incluir duración
                            promedioCalificacion  // Incluir promedio de calificación
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
    public List<RestaurantResponse> getRestaurantesPorUsuario() {
        // Obtener el usuario autenticado usando AuthService
        Usuario usuario = authService.getAuthenticatedUser();

        // Mapear los restaurantes asociados al usuario autenticado a objetos RestaurantResponse
        return restauranteRepository.findByUsuario(usuario).stream()
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
                                restaurante.getHoraApertura(),
                                restaurante.getHoraCierre(),
                                null,  // Distancia nula
                                null,   // Tiempo nulo
                                0.0    // Calificación promedio 0.0 si no tiene calificaciones
                        );
                    }

                    // Llamar al servicio getDirections para obtener la distancia y el tiempo estimado
                    String direccionInfo = geocodingService.getDirections(latUsuario, lngUsuario, latRestaurante, lngRestaurante);

                    // Extraer la distancia y duración de la respuesta
                    String[] info = direccionInfo.split(", ");
                    String distancia = info[0].replace("Distancia: ", "");
                    String duracion = info[1].replace("Duración: ", "");

                    // Calcular el promedio de las calificaciones
                    double promedioCalificacion = calificacionService.calcularPromedioCalificaciones(restaurante.getPk_restaurante());

                    // Crear el objeto RestaurantResponse con los datos adicionales
                    return new RestaurantResponse(
                            restaurante.getPk_restaurante(),
                            restaurante.getNombre(),
                            restaurante.getDescripcion(),
                            restaurante.getUbicacion(),
                            restaurante.getTipo(),
                            restaurante.getImg(),
                            restaurante.getHoraApertura(),
                            restaurante.getHoraCierre(),
                            distancia,      // Incluir distancia
                            duracion,       // Incluir duración
                            promedioCalificacion  // Incluir promedio de calificación
                    );
                })
                .collect(Collectors.toList());
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
        if(restauranteDetalles.getHoraApertura() != null && restauranteDetalles.getHoraApertura().equals(restaurante.getHoraApertura())) {
            restaurante.setHoraApertura(null);
        }
        if (restauranteDetalles.getHoraCierre() != null && restauranteDetalles.getHoraCierre().equals(restaurante.getHoraCierre())) {
            restaurante.setHoraCierre(null);
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

        // Guardar y retornar el restaurante actualizado
        return saveOrUpdateRestaurante(restaurante);
    }

    @Override
    public List<RestaurantResponse> getRestauranteByNombre(String nombre) {
        // Buscar restaurantes por nombre, considerando que la búsqueda no es sensible a mayúsculas y minúsculas
        List<Restaurante> restaurantes = restauranteRepository.findByNombreContainingIgnoreCase(nombre);

        // Mapear la lista de Restaurantes a RestaurantResponse usando stream
        return restaurantes.stream()
                .map(restaurante -> {
                    // Obtener el usuario autenticado
                    Usuario usuario = authService.getAuthenticatedUser();

                    // Obtener las coordenadas del usuario y del restaurante
                    double latUsuario = usuario.getLatitud();
                    double lngUsuario = usuario.getLongitud();

                    double latRestaurante = restaurante.getLatitud();
                    double lngRestaurante = restaurante.getLongitud();

                    // Verificar si las coordenadas del usuario son válidas
                    if (latUsuario == 0.0 || lngUsuario == 0.0) {
                        return new RestaurantResponse(
                                restaurante.getPk_restaurante(),
                                restaurante.getNombre(),
                                restaurante.getDescripcion(),
                                restaurante.getUbicacion(),
                                restaurante.getTipo(),
                                restaurante.getImg(),
                                restaurante.getHoraApertura(),
                                restaurante.getHoraCierre(),
                                null,  // Distancia nula
                                null,   // Tiempo nulo
                                0.0    // Calificación promedio 0.0 si no tiene calificaciones
                        );
                    }

                    // Llamar al servicio getDirections para obtener la distancia y el tiempo estimado
                    String direccionInfo = geocodingService.getDirections(latUsuario, lngUsuario, latRestaurante, lngRestaurante);

                    // Extraer la distancia y duración de la respuesta
                    String[] info = direccionInfo.split(", ");
                    String distancia = info[0].replace("Distancia: ", "");
                    String duracion = info[1].replace("Duración: ", "");

                    // Calcular el promedio de las calificaciones
                    double promedioCalificacion = calificacionService.calcularPromedioCalificaciones(restaurante.getPk_restaurante());

                    // Crear el objeto RestaurantResponse con los datos adicionales
                    return new RestaurantResponse(
                            restaurante.getPk_restaurante(),
                            restaurante.getNombre(),
                            restaurante.getDescripcion(),
                            restaurante.getUbicacion(),
                            restaurante.getTipo(),
                            restaurante.getImg(),
                            restaurante.getHoraApertura(),
                            restaurante.getHoraCierre(),
                            distancia,      // Incluir distancia
                            duracion,       // Incluir duración
                            promedioCalificacion  // Incluir promedio de calificación
                    );
                })
                .collect(Collectors.toList());
    }


}


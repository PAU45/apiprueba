# Guía para usar la API de CASERITO_API

### Instrucciones de uso

1. **Registrar usuario** usando el endpoint `sign-up`.
2. **Iniciar sesión** para obtener un token de acceso.
3. Probar autenticación.
4.  Usar el token para hacer solicitudes autenticadas.
5.  **Actualizar usuario** para cambiar información como email, teléfono y dirección. Recuerda que el token se invalida y necesitas obtener uno nuevo.
6.  **Convertirse en empresa** si el usuario no tiene ese rol, usando el endpoint de `create`.
7.  **Agregar un restaurante** proporcionando el nombre, ubicación y descripción.
8. Ver lista de restaurantes.
9. Ver mi lista de restaurantes.
10. Eliminar un restaurante.
11. Editar informacion de mi restaurante.
12. Ver mis datos.
13. Agregar un comentario.
14. Listar comentarios por restaurante.
15. Agregar calificacion.
16. Ver calificacion.
17. Agregar un favorito.
18. Lista de restaurantes favoritos.
19. Ruta para ubicacion de restaurante.
20. Agergar contactos(detalle).
21. Ver contactos(detalle).
22. Eliminar contacto(detalle).
23. Agregar menu.
24. Eliminar menu.
25. Ver menus.
26. Editar menu.

## 1. Registrar usuario
**Endpoint:**

POST http://localhost:8080/caserito_api/authentication/sign-up

**Cuerpo de la solicitud (JSON):**

```
{
    "username": "hans",
    "password": "12345678",
    "email": "hans@tecsup.edu.pe",
    "telefono": "128312832",
    "direccion": "askdjnas"
}
```


**Nota:**

*El correo debe seguir el formato @tecsup.edu.pe.
Respuesta esperada:
El sistema registrará al usuario y devolverá un token de acceso para futuras solicitudes.*

**-----------------------------------------------------------------------------**

## 2. Iniciar Sesión
**Endpoint:**

POST http://localhost:8080/caserito_api/authentication/log-in

*Cuerpo de la solicitud (JSON):*

```
{
    "username": "hans",
    "password": "12345678"
}

```
**Respuesta esperada:**

*Un token de acceso que será necesario para autenticar otras solicitudes.*

**-----------------------------------------------------------------------------**
## 3. Probar la Autenticación
**Endpoint:**

GET http://localhost:8080/caserito_api/restaurante/prueba

**Encabezados requeridos:**

*Copiar código*

``
    Authorization: Bearer <tu_token_de_acceso>
``

**Respuesta esperada:**

*Confirma si el token es válido.*

**-----------------------------------------------------------------------------**
## 4. Actualizar Usuario
**Endpoint:**

POST http://localhost:8080/caserito_api/user/update-user

*Cuerpo de la solicitud (JSON):*

```
{
    "email": "nuevoemail@tecsup.edu.pe",
    "telefono": "987654321",
    "direccion": "Nueva dirección 123"
}
```

**⚠ Advertencia:**

**Actualizar el token:**

*Después de realizar esta operación, el token anterior se invalida.
Obtén un nuevo token iniciando sesión nuevamente con tus credenciales actualizadas.*

**-----------------------------------------------------------------------------**


## 5. Convertirse en Empresa
Endpoint:


POST http://localhost:8080/caserito_api/user/update-user

*Cuerpo de la solicitud (JSON):*

```
{
    "rol": "EMPRESA"
}
```

**Notas:**

*Solo puedes tener un rol de empresa si no lo posees ya.
El token debe incluir los permisos de usuario para esta operación.*

**-----------------------------------------------------------------------------**

## 6. Agregar un Restaurante

**Endpoint:**


POST http://localhost:8080/caserito_api/restaurante/create

*Cuerpo de la solicitud (JSON):*

```
{
    "nombre": "Parada 22",
    "ubicacion": "Francisco de Orellana, Nueva Loja, Ecuador",
    "descripcion": "Comida china 2",
    "tipo": "Postres",
    "img": "url",
    "horaApertura": "08:00:00",
    "horaCierre": "22:00:00"
}

```

**-----------------------------------------------------------------------------**

## 8. Ver mi lista de restaurantes

**Endpoint:**

GET: http://localhost:8080/caserito_api/restaurante/mis-restaurantes

**Notas:**

*Con este Endpoint vera la lista de los restaurantes creados por el usario autenticado*


**-----------------------------------------------------------------------------**

## 9. Ver lista de restaurantes

**Endpoint:**

GET: http://localhost:8080/caserito_api/restaurante/all

**Notas:**

*Con este Endpoint vera la lista de los restaurantes*


**-----------------------------------------------------------------------------**
## 10. Editar informacion de mi restaurante

**Endpoint:**

PUT: http://localhost:8080/caserito_api/restaurante/update/{id}

*Cuerpo de la solicitud (JSON):*

```

{
    "nombre": "Parada 22"
}

```

**Notas:**

*Solo ingresar los campos que se requiere cambiar*

**-----------------------------------------------------------------------------**
## 11. Eliminar un restaurante

**Endpoint:**

DELETE:http://localhost:8080/caserito_api/restaurante/delete/{id}

*Posibles resultadhmos*

````
{
    "msg": "Restaurante eliminado exitosamente"
}

````

````
{
    "msg": "No tienes permiso para eliminar este restaurante."
}

````

````
{
    "msg": "Restaurante no encontrado con el id: 50"
}

````
**-----------------------------------------------------------------------------**
## 12. Obtener mis datos

**Endpoint:**
GET: http://localhost:8080/caserito_api/user/me

**-----------------------------------------------------------------------------**
## 13. Agregar un comentario.

**Endpoint:**

POST: http://localhost:8080/caserito_api/comentarios/agregar

*Cuerpo de la solicitud (JSON):*

````
{
    "restauranteId": "1",
    "comentario": "El mejor restaurante, la comida estaba muy rica 20/20"
}

````

*Posibles resultadhmos*

** Comentario agregado con éxito **

**-----------------------------------------------------------------------------**
## 14. Listar comentarios por restaurante
**Endpoint:**

GET: http://localhost:8080/caserito_api/comentarios/restaurante/{ID}

*Resultado:*

````
[
    {
        "restauranteId": 1,
        "comentario": "es un buen restaurante lo pase bonito",
        "username": "diego",
        "avatarUser": "https://www.movilzona.es/app/uploads-movilzona.es/2023/04/fto-perfil.jpg?x=480&y=375&quality=80"
    },
    {
        "restauranteId": 1,
        "comentario": "Lo pase mal es el peor restaurante",
        "username": "abel",
        "avatarUser": null
    }
]

````

**-----------------------------------------------------------------------------**
## 15. Agregar calificacion
**Endpoint:**

POST: http://localhost:8080/caserito_api/calificacion/agregar

*Body:*

````
{
    "restauranteId": "4",
    "calificacion": 4
}
````
*Resultado:*

````
    Calificación agregada con éxito

````
**-----------------------------------------------------------------------------**
## 16. Ver calificacion.
**Endpoint:**

GET: http://localhost:8080/caserito_api/calificacion/restaurante/{id}

*Resultado:*

````
[
    {
        "restauranteId": 1,
        "calificacion": 4,
        "username": "hans",
        "avatarUser": null
    }
]
````
**-----------------------------------------------------------------------------**
## 17. Agregar un favorito
**Endpoint:**

POST: http://localhost:8080/caserito_api/favorito/agregar

*Body:*

````
{
    "restauranteId": 3
}
````
**-----------------------------------------------------------------------------**
## 18. Lista de restaurantes favoritos
**Endpoint:**

GET: http://localhost:8080/caserito_api/favorito

**-----------------------------------------------------------------------------**
## 19. Ruta para ubicacion de restaurante
**Endpoint:**

GET: http://localhost:8080/caserito_api/restaurante/{id}/ruta

````
{
    "apiKey": "AIzaSyAKF0oCPCnn45d3tXsxUaYoJCB9rQZMl5s",
    "origin": {
        "lng": -77.0593631,
        "lat": -12.0653052
    },
    "destination": {
        "lng": -77.1256883,
        "lat": -12.0511717
    }
}
````
**-----------------------------------------------------------------------------**
**-----------------------------------------------------------------------------**
**-----------------------------------------------------------------------------**
**-----------------------------------------------------------------------------**
## 20. Agergar contactos(detalle)
**Endpoint:**

POST: http://localhost:8080/caserito_api/detalle/{id_restaurante}

**Body**

````
{
    "informacion": "930501210",
    "tipo": "NUMERO"
}
````
**-----------------------------------------------------------------------------**
## 21.Ver contactos(detalle)
**Endpoint:**

GET: http://localhost:8080/caserito_api/detalle/{id_restaurante}

**Tipos de datos admitidos**

````
    NUMERO,
    FACEBOOK,
    EMAIL,
    WHATSAPP,
````
**-----------------------------------------------------------------------------**
## 22.Eliminar contacto(detalle)
**Endpoint:**

DELETE: http://localhost:8080/caserito_api/detalle/{ID_detalle}


**-----------------------------------------------------------------------------**
## 23. Agregar menu

**Endpoint:**

POST: http://localhost:8080/caserito_api/menu/{Id_restaurante}

**Body**

````
{
    "nombre": "Tipakay",
    "descripcion": "Comida combina dulce con salado",
    "img": "https://img-global.cpcdn.com/recipes/819cb89210d39293/1200x630cq70/photo.jpg",
    "precio": 7.9
}
````
**-----------------------------------------------------------------------------**
## 24. Eliminar menu

**Endpoint:**

DELETE: http://localhost:8080/caserito_api/menu/{Id_restaurante}


**-----------------------------------------------------------------------------**
## 25. Ver menu

**Endpoint:**

GET: http://localhost:8080/caserito_api/menu/{Id_restaurante}

**-----------------------------------------------------------------------------**
## 25. Editar menu

**Endpoint:**

DELETE: http://localhost:8080/caserito_api/detalle/{ID_detalle}
**-----------------------------------------------------------------------------**
## 26. Busqueda

**Endpoint:**

GET: http://localhost:8080/caserito_api/restaurante/buscar?nombre=<Nomre del restaurante>

**Resultado**

````

[
    {
        "restaurantId": 1,
        "nombre": "Parada 22",
        "descripcion": "Comida china 2",
        "ubicacion": "Francisco de Orellana, Nueva Loja, Ecuador",
        "tipo": "Postres",
        "img": "https://dfmas.df.cl/dfmas/site/artic/20220922/imag/foto_0000002020220922191520/Copia_de_Hyatt_Centric_Lima-050407.jpg",
        "horaApertura": null,
        "horaCierre": null,
        "distancia": "2,036 km",
        "tiempo": "1 day 10 hours",
        "calificacion": 5.0
    }
]

````
**eJEMPLO**
````
http://localhost:8080/caserito_api/restaurante/buscar?nombre=Parada 22
````

**-----------------------------------------------------------------------------**
**-----------------------------------------------------------------------------**
**-----------------------------------------------------------------------------**
**-----------------------------------------------------------------------------**
**-----------------------------------------------------------------------------**


**⚠ Notas Finales**

*Asegúrate de incluir el token de autenticación en cada solicitud protegida, en los encabezados como:*

```

    Authorization: Bearer <tu_token_de_acceso>

```

*Actualiza tu token después de modificar datos sensibles del usuario como correo, contraseña o roles.
Los roles válidos actualmente son: USER y EMPRESA.*
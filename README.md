# Guía para usar la API de CASERITO_API

### Instrucciones de uso

1. **Registrar usuario** usando el endpoint `sign-up`.
2. **Iniciar sesión** para obtener un token de acceso.
3. Usar el token para hacer solicitudes autenticadas.
4. **Actualizar usuario** para cambiar información como email, teléfono y dirección. Recuerda que el token se invalida y necesitas obtener uno nuevo.
5. **Convertirse en empresa** si el usuario no tiene ese rol, usando el endpoint de `create`.
6. **Agregar un restaurante** proporcionando el nombre, ubicación y descripción.

## 1. Registrar Usuario

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

PUT http://localhost:8080/caserito_api/user/update-user

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

bash

POST http://localhost:8080/caserito_api/restaurante/create

*Cuerpo de la solicitud (JSON):*

```
        {
            "nombre": "Parada 22",
            "ubicacion": "Francisco de Orellana, Nueva Loja, Ecuador",
            "descripcion": "Comida china 2"
        }
```

Encabezados requeridos:

**-----------------------------------------------------------------------------**

**⚠ Notas Finales**

*Asegúrate de incluir el token de autenticación en cada solicitud protegida, en los encabezados como:*

```

    Authorization: Bearer <tu_token_de_acceso>

```

*Actualiza tu token después de modificar datos sensibles del usuario como correo, contraseña o roles.
Los roles válidos actualmente son: USER y EMPRESA.*

># TRANSFORMA

## Descripción del proyecto

Este proyecto tiene como finalidad de creación de tickets el cual se conecta a diferentes sistemas llamados legados, uno de estos legados es Remedy y se conectan a través de un servicio puente llamado BUS ESB (Informática TELCEL) que después se conecta a Remedy a través de Remedy Control.

- Así mismo este sistema tiene un flujo de vuelta el cual utiliza un WS puente de Remedy Control que usando el BUS ESB se conecta al servicio de Huawei.
- Todas las peticiones de creación de Transforma hacia Remedy son registradas utilizando una tabla en la base de datos.
- Las WO generadas son un incidente tipo CPD.
- Cuenta con dos funciones:
    - Generar una WO de tipo incidente.
    - Notifica a través del servicio web de BUS y BES que un incidente generado por su propio usuario fue pasado a estado RESUELTO o CANCELADO. 

## Estado del proyecto

Este proyecto actualmente se encuentra en uso y está desplegado en los 2 ambientes (desarrollo y producción MX 165 y 166).

## Requerimientos

Se recomienda encarecidamente respetar estos puntos para poder usar la aplicación de forma correcta:

-   Tener instalado Java 1.8
-   Hacer deploy en un servidor Tomcat 8 o superior

## Ejecutar

Divido que el programa se ejecuta por factores externos no se puede ejecutar de manera local.

## Despliegue en el servidor

Para desplegar la aplicación, se requiere colocar el archivo `rcTransformaBES.war` en el servidor de aplicaciones, siguiendo estos pasos:

1.  Accede al servidor de aplicaciones.
2.  Navega hasta la ubicación `/webapps8` del servidor.
3.  En la anterior ruta mencionada, coloca el arhcivo `rcTransformaBES.war`, ubicado en la ruta `/TRANSFORMA/dist/rcTransformaBES.war` del proyecto ya ejecutado.
4.  Una vez que hayas colocado el archivo en el servidor, el servidor de aplicaciones automáticamente desplegará el proyecto. Esto resultará en la creación de un directorio dedicado al proyecto en la siguiente ubicación: `/webapps8/rcTransformaBES`.


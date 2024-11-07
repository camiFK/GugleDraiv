# Para inicializar la aplicacion

1. Instalar paquetes

- Pararse en la carpeta donde esta el archivo pom.xml
- Abrir una terminal
- Ejecutar `mvn clean install`

Si fue exitoso muestra Build Success

2. Levantar la aplicacion

La primera vez, ir a application.properties y cambiar spring.sql.init.mode=embedded por spring.sql.init.mode=always. Esto es para que se ejecute data.sql y se llene la db.
Una vez que arranca y se pueden ver datos, volver a poner spring.sql.init.mode=embedded para que no se llene cada vez que se recarga spring.

- `mvn spring-boot:run`
 

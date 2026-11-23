
## Integrantes:
- Francisco y Miguel Jimenez

## Compilar el proyecto: (ambos juntos)
$javaFiles = Get-ChildItem -Path app,Framework,src -Filter *.java -Recurse | ForEach-Object FullName
javac -encoding UTF-8 -d out $javaFiles

## Iniciar El Servidor ya compilado:
java -cp out app.Main

## Entrar al puerto
java -cp out Cliente

## Nuestra aplicacion de ejemplo es una Biblioteca
Elegimos una biblioteca con gestión de catálogos de libros y préstamos. Cuenta con autenticación en sesión y 2 roles, Usuario y Admin (Admin hereda permisos de Usuario y puede dar de alta libros).

## Protocolo
Delimitación: Los mensajes van separados por: " / "
Solicitud: recurso/accion[/parametro1/parametro2]

recurso: Nombre del controlador (libro,usuario,prestamos) (no puede estar vacio).
accion: operación que realiza el controlador (no puede estar vacio).
parametro1, parametro2,... : se pueden requerir 1 o mas dependiendo de la accion.

## Ejemplo solicitudes:
Log in y log out:
- usuarios/login/<Tu Nombre De Usuario>/<Tu clave>
- usuarios/quien-soy
- usuarios/logout

Operaciones de Libros:
- libros/alta/<id>/<titulo>/<copias> (requiere ser Admin)
- libros/listar (no requiere Admin)
- libros/buscar/<consulta> (no requiere Admin)

Operaciones de prestamos:
- prestamos/pedir/<idLibro> (no requiere Admin)
- prestamos/devolver/<idLibro> (no requiere Admin)
- prestamos/listar (no requiere Admin)


## Respuestas esperadas:
- usuarios/login/<Usuario>/<clave> 	-> "Ok Sesion iniciada como: (Tu usuario)"
- usuarios/quien-soy		-> "Ok Sos (Tu usuario) "
- usuarios/logout 			-> "Ok Sesion cerrada"

- libros/alta/<id>/<titulo>/<copias>	-> "Ok Libro registrado: <id> <titulo> <copias>"
- libros/listar			-> "Ok (Tu usuario) y la lista" (los libros van separados por " | ")
- libros/buscar/<consulta>		-> "Ok "

- prestamos/pedir/<idLibro>		-> "Ok Prestamo confirmado para (tu usuario)"
- prestamos/devolver/<idLibro>	-> "Ok Devolucion registrada para el libro <id>"
- prestamos/listar			-> "Ok (tu usuario) -> (id del libro)" (varios libros van separados por " , ")

## Algunos Errores:
Si realizas una operacion sin logear	 -> "ERROR Inicie sesion"
Si no tenes permisos suficientes:	 -> "ERROR Permisos insuficientes"

Si intentas crear un libro con igual id	-> "ERROR El libro ya existe el libro con id: <id>"
Al tomar prestado un libro ya tomado	-> "ERROR El usuario (su usuario) ya tiene prestado el libro <id>"










# Visión General del Sistema

El propósito general de este artefacto de software es analizar un cierto documento (presumiblemente una fotocopia), verificar si corresponde a una identificación oficial (INE), para finalmente recuperar los datos valiosos tales como nombre, dirección domiciliaria, género, CURP, Clave de elector.

## Justificación General

Este software fue elaborado con la finalidad de recuperar de manera rápida los datos valiosos de una credencial de elector para así reducir los tiempos de captura de dichos datos.

## Justificación de los lenguajes y librerías de programación

Para este artefacto se decidió el uso del lenguaje de programación Java en su versión 21, esto debido a la fiabilidad que proporciona el lenguaje, así como su uso regular en la industria de desarrollo de software, lo que permite que pueda ser adaptado a sistemas existentes con una dificultad mínima.

Para la manipulación de imágenes se decidió por el uso de OpenCV, el cual es una librería de visión por computadora de tipo de código abierto, el cual trae implementadas funciones que permiten de manera “relativamente” sencilla la identificación de rectángulos, la identificación de caras, la alteración de imágenes según diversos filtros, así como recortar la imagen propiamente.

Para el proceso de OCR (Optical Character Recognition), se decidió por el uso de la librería Tesseract debido a su confiabilidad ya que fue diseñada e implementada por Google, así como por su fácil uso y el hecho que por defecto, ya contiene entrenamientos de caracteres predeterminados, por lo que no es estrictamente necesario realizar un entrenamiento desde 0, lo que ahorra bastante tiempo de desarrollo.

Para el manejo de los paquetes anteriormente mencionados se utilizó Maven el cual es un gestor de paquetes, que permite la coordinación y el manejo de dependencias para así evitar el manejo manual de los archivos .jar de las librerías correspondientes.

Como punto final a este apartado cabe resaltar que el proyecto fue elaborado en NetBeans, como un proyecto de NetBeans, esto se menciona porque puede ser posible que otros editores de código no detecten correctamente la estructura del proyecto.

## Diseño general del sistema

El sistema está separado en dos grandes secciones. La primera sección está relacionada con la identificación de la INE, así como el procesamiento necesario para la manipulación de la imagen y prepararla para la segunda fase, para términos prácticos se denominará esta fase como “FASE I”.

La fase siguiente corresponde al escaneado de la imagen mediante técnicas de OCR y la manipulación de los datos provenientes del OCR, para así presentar los datos de manera adecuada. Para términos prácticos denominaremos esta fase como “FASE II”.

**Contexto:** Cuando se realiza el proceso de OCR la función te retorna todo el texto que encuentra sin excepciones, no es posible saber exactamente qué pedazo de este texto corresponde al nombre (u otro dato deseado), por lo que se emplearán técnicas de manejo de cadenas (que se explicarán formalmente más adelante) para devolver exactamente los datos que se solicitan de manera estructurada.

### FASE I

Como se mencionó con anterioridad la fase I es la encargada de la manipulación y análisis de la imagen para determinar si corresponde a una credencial de elector INE, así como su preparación para la fase siguiente. A continuación, se hará mención de las clases que componen esta fase, para así posteriormente explicar método por método su funcionalidad, igualmente se justificarán ciertas decisiones de diseño que a simple vista pueden ser incoherentes.

- **ManejoImagenes.java** – Esta clase, como menciona su nombre, es encargada de todas las operaciones relacionadas con imágenes, tales como encontrar coordenadas, dibujar rectángulos, detectar caras entre otras operaciones que serán descritas próximamente.
- **Coordenadas.java** – Es la clase que sirve para guardar un objeto coordenada, el cual guarda las posiciones correspondientes a las 4 esquinas de una imagen. 
- **CoordenadasRojas.java** – Es la clase hija de Coordenadas, estas coordenadas hacen referencia explícita, a las coordenadas obtenidas cuando se encuentra un rectángulo con ciertas dimensiones. Se le llaman rojas debido a que cuando se dibujan sobre un archivo temporal el rectángulo encontrado, este es de color rojo.
- **Punto.java** – Este objeto es el elemento atómico de una coordenada (el par ordenado X,Y), existe una relación de agregación con la clase Coordenadas.java.
- **ExcepcionesPropias.java** – Una clase que hereda de la clase Exception y contiene un único método que recibe como argumento un mensaje de error, el cual lanza una excepción personalizada (proveniente de la clase padre) que únicamente contiene un mensaje.

### ManejoImágenes.java

Esta clase tiene un constructor únicamente inicializando un hash Set de Objetos Coordenada Roja, la razón de que se haya utilizado un hash set es que esta estructura no permite elementos duplicados, para esta funcionalidad de no permitir elementos duplicados se tuvo que sobrescribir los métodos equals() y hashcode() los cuales se encuentran explícitamente en la clase padre Coordenadas.java, de esta manera cualquier clase hija puede utilizar indirectamente estos métodos al entrar en el hash Set. Todas las funciones únicamente manipulan imágenes en formato PNG.

- **EncontrarContornos:** Esta función recibe como argumentos 2 cadenas, la primera cadena corresponde a la ruta absoluta donde se encuentra el archivo de entrada, véase la imagen de ejemplo (ImagenPrimerProceso.png). La segunda cadena corresponde a la ruta absoluta donde el usuario desea guardar la imagen de salida. La imagen de salida, es una copia de la imagen original, sin embargo, tiene “dibujado” de color rojo cualquier rectángulo con ciertas dimensiones correspondientes al tamaño de una INE, además internamente se guardan como un objeto Coordenadas Rojas, todas aquellas coordenadas correspondientes a los rectángulos encontrados. Cabe mencionar que cuando se dibujan los rectángulos, no se dibujan exactamente sobre la imagen rectangular, sino se deja cierto margen para garantizar que toda la credencial sea apreciable, debido a que puede estar ligeramente inclinada.
- **DividirImagenesPorContorno:** Esta función recibe por argumento la ruta absoluta correspondiente a la salida de EncontrarContornos, la función toma la imagen deseada, y según las coordenadas que analizó previamente las cuales siguen guardadas en Set<CoordenadasRojas> coordenadasSet, procede a recortar la imagen según los N rectángulos encontrados, devuelve como salida las imágenes recortadas, con la nomenclatura [nombre_que_se_le_proporciono_al_archivo_de_salida_del proceso_ EncontrarContornos]_recortada_[numero_de_iteracion_de_rectangulo_recortado].png. Para las funciones de recorte en esta función se utilizaron métodos pertenecientes a la librería OpenCV.
- **detectarRostros:** Como su nombre lo indica esta función es la encargada de devolver el número de caras que se encuentren en cierta imagen, recibe como argumento una cadena que corresponde a la ruta absoluta de una imagen que fue recortada previamente por DividirImagenesPorContorno.
  Consideraciones Importantes:
  - Esta función lanza una excepción si no encuentra suficientes caras.
  - Requiere que se modifique la ruta absoluta correspondiente al detector de caras el cual es haarcascade_frontalface_default.xml, anteriormente se proporcionó el link para descargar el archivo mencionado.
- **RecortarImagen:** Como su nombre lo indica procede a recortar una imagen de un archivo específico. Toma como argumento la ruta absoluta del archivo original, un objeto coordenadas y finalmente una ruta absoluta de salida.

  Aclaración: ¿Por qué hay 2 funciones de recorte de imágenes?
  La razón de que exista 2 funciones de recorte es debido a que cuando se aplicó la función EncontrarContornos, para que el proceso fuera exitoso se requirió aplicar ciertos filtros para que pudiera funcionar correctamente el procedimiento, sin embargo con cada proceso que le realizamos a la imagen, su calidad disminuyó, lo que provoca que los procesos de la FASE II no se puedan realizar adecuadamente, anudado a eso durante el desarrollo se detectó que la función incorporada de recorte de OpenCv, bajaba demasiado la calidad de la imagen. Aunque deterioraba la imagen se conservó la función ya que su ejecución es muy rápida por lo es idóneo cuando se requiere analizar muchas hojas. El proceso actual RecortarImagen utiliza una librería perteneciente a Apache el cual, aunque tarda ligeramente más en ejecutarse mantiene fiel la calidad de la imagen original que recibe como argumento. Así como devuelve la imagen en la ruta absoluta que tú le indiques, lo que permite conectarla con otro proceso.

### PDFtoPNGConverter

Como su nombre lo indica, convierte archivos PDF a imágenes en formato PNG, recibe como argumento la ruta absoluta de un archivo PDF, y devuelve según el directorio indicado en el argumento, las N páginas del documento convertidos en imágenes PNG, cabe aclarar que las imágenes PNG se devuelven por separado (individuales, una por cada página). Esta función no se utilizó en el código final, sin embargo, se dejó en caso de que los documentos existentes requieran ser transformados de formato.

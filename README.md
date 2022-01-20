# Sistema Aeropuerto

  Trabajo Práctico Final para la materia Programación Concurrente de la Universidad Nacional del Comahue (UNCo). Programación Orientada a Objetos donde se destaca el uso de mecanismos y técnicas para lograr concurrencia.
  </br></br>
## Construido con 🛠️

  - [Java](https://www.w3schools.com/java/default.asp) - Lenguaje utilizado.

  - [NetBeans](https://netbeans.apache.org/) - Entorno de desarrollo utilizado.
 
## Requerimientos del sistema 📄
Se desea simular el funcionamiento del Aeropuerto "VIAJE BONITO", desde que el pasajero
llega al mismo para tomar su vuelo hasta que sube al avión. El aeropuerto siempre tiene sus puertas
abiertas, pero atiende a los pasajeros solo de 6.00 hrs. a 22.00 hrs.</br>
Hay n aerolíneas, coexistiendo en dicho aeropuerto (por ejemplo Aerolíneas Argentinas, LAN,
etc.), cada una de ellas con su puesto de atención de pasajeros.
(Considere para cada aerolínea 1 puesto de atención)</br>
Los pasajeros tienen su reserva en un vuelo de un
a de las aerolíneas del aeropuerto. Cuando un pasajero ingresa al aeropuerto llega a un puesto de
informes, donde es atendido y desde allí es derivado al puesto de atención que corresponda según su
vuelo y aerolinea para hacer el check-in.</br>
(*) Para poder trabajar esto se deben crear los vuelos al comenzar a trabajar. Cada Aerolinea tiene
algunos vuelos, cada uno con su horario de embarque/partida. No hay problema con vuelos de
distintas aerolíneas con el mismo horario, siempre y cuando se asignen a distinto puesto de
embarque.</br>
Cada puesto de atención tiene lugar para una cantidad max de pasajeros, que son atendidos por
orden de llegada. Los pasajeros que llegan cuando el limite max esta superado esperan en un hall
central hasta que se haga lugar. Además, en los puestos, hay un guardia que se encarga de dar paso
a los pasajeros que llegan, a medida que se va desocupando el lugar del puesto.
La idea es que haya un guardia por cada puesto.</br>
NO interesa simular el tema de las reservas en una aerolínea particular por lo que la misma, se
generará de forma aleatoria al llegar al aeropuerto.
Debe tomarse aleatoriamente un vuelo entre los existentes, que fueron ya creados, al inicio (ver * y
nota al final del texto).</br>
Además en el aeropuerto hay varias terminales. Cada terminal tiene varios puestos de embarque y
una sala de embarque compartida.</br>
Cuando un pasajero hace el check-in se le indica la terminal y puesto de embarque que corresponde
a su vuelo. Porque cada vuelo tiene asignado un puerto de embarque en alguna terminal.
Puede haber mas de un vuelo asignado a cada puesto de embarque, obviamente con distinto horario
de embarque. Por ejemplo en la terminal A, puesto de embarque 1, se embarcan los vuelos:
- A147 de Aerolineas Argentinas, horario 10 hrs.
- L12 de LAN, horario 15 hrs.
- A1234 de Aerolineas Argentinas, horario 19 hrs.</br></br>

Entonces, los pasajeros que ya hicieron el check-in son trasladados a la terminal que les
corresponde y permanecen allí hasta que se haga el llamado para embarcar.</br>
Por ejemplo, el aeropuerto "VIAJE BONITO" tiene 3 terminales: A, B y C. En la terminal A están
los puestos de embarque 1 a 7, en la terminal B los puestos del 8 a 15 y en la terminal C del 16 a 20.</br>
Por otro lado, en cada terminal hay un free-shop en el que los pasajeros que esperan para embarcar
pueden hacer compras, o solo mirar los productos que se ofrecen. Por una cuestion de organización
cada free-shop tiene una capacidad para “lugar” personas, es decir que si ya esta cubierta lacapacidad no podrán ingresar mas personas hasta que alguna abandone el free shop y deje el lugar. Hay una puerta de ingreso y una puerta de egreso, y 2 cajas cercanas a la puerta de egreso.</br>
Al llegar a la terminal, los pasajeros pueden ir al free-shop o solamente sentarse a esperar en la sala
de embarque general el llamado para embarcar. Es importante tener en cuenta que solo pueden ir al
free-shop cuando tengan tiempo suficiente antes de la hora de embarque.</br>
En el aeropuerto hay un “people mover/transporteATerminal” que es como un pequeño tren
interno que se utiliza para trasladar a los pasajeros hasta las distintas terminales, que se mueve hacia
las terminales por una vía. A lo largo del trayecto encuentra las terminales. En cada terminal se
detiene si algun pasajero lo solicita. Al llegar a la última terminal el tren debe quedar vacío, y
vuelve al punto de inicio de su trayecto (o sea el ingreso al aeropuerto), alli espera hasta tener su
capacidad completa para comenzar un nuevo recorrido.</br>
Aqui se debe considerar alguna forma extrra para que el transporte pueda comenzar su recorrido
aun cuando no logra completar su capacidad, por ejemplo porque ya esta cerca la hora de cierre de
atencion y quedan pocos pasajeros. Se podria considerar un tiempo maximo de espera.</br>
Debe resolverse utilizando los mecanismos de sincronizacion vistos en la materia y provistos
por el lenguaje: semáforos y monitores (obligatoriamente), y al menos algunos de los
siguientes: Locks, CyclicBarrier, CountDownLatch, Exchanger., implementaciones de
BlockingQueue.</br>

IMPORTANTE: en todo momento debe considerar los horarios.
Cuando a un pasajero se le asigna aleatoriamente un vuelo, hay que tener en cuenta los
horarios de los vuelos, no se le puede dar un vuelo que ya haya salido o este muy proximo a
salir. Es decir si el pasajero llega a informes a las 11 hrs, se le debe dar un vuelo que salga al
menos 2 horas mas tarde.
  
## Autor ✒️

- **Federico Aguilera** - [Fedeag-92](https://github.com/Fedeag-92)

package SistemaAeropuerto;

import utiles.ListaInt;
import java.util.Random;

public class SalaInformes {

    public static final String RESET = "\u001B[0m";
    public static final String LETRA_NEGRA = "\u001B[30m";
    public static final String LETRA_BLANCA = "\u001B[37m";

    public static final String FONDO_ROJO = "\u001B[41m";

    private final Reloj hora;
    private final Aerolinea[] aerolineas;

    public SalaInformes(Reloj hora, Aerolinea[] a) {
        this.hora = hora;
        this.aerolineas = a;
    }

    public int atenderEnInformes(Pasajero p) throws InterruptedException {
        int salida = -1;

        System.out.println(FONDO_ROJO + "█" + LETRA_BLANCA + " SALA DE INFORMES " + LETRA_NEGRA + "█" + LETRA_BLANCA + " El pasajero " + p.getDni() + " se encuentra en Informes." + RESET);
        Random aleatorio = new Random();

        Thread.sleep(aleatorio.nextInt(500) + 500);

        Vuelo v = getVueloPosible(p);

        if (v != null) {
            p.setPasajeDeVuelo(v);

            v.setCapacidad();

            String a = v.getNombreAerolinea();
            if ("LAN".equals(a)) {
                salida = 0;
            } else if ("Aerolineas Argentinas".equals(a)) {
                salida = 1;
            } else {
                salida = 2;
            }

            System.out.println(FONDO_ROJO + "█" + LETRA_BLANCA + " SALA DE INFORMES " + LETRA_NEGRA + "█" + LETRA_BLANCA + " El pasajero " + p.getDni() + " sale de Informes, se debe dirigir al puesto de atencion " + v.getNombreAerolinea() + " y su vuelo (" + v.getId() + ") sale a las " + p.getPasajeDeVuelo().getHorario() + ". Capacidad del vuelo " + v.getId() + " es: " + v.getCapacidad() + RESET);
            if (v.getCapacidad() == 0) {
                System.out.println(FONDO_ROJO + "█" + LETRA_BLANCA + " SALA DE INFORMES " + LETRA_NEGRA + "█" + LETRA_BLANCA + " Se informa que el vuelo " + v.getId() + " no tiene mas capacidad." + RESET);
            }
        } else {
            System.out.println(FONDO_ROJO + "█" + LETRA_BLANCA + " SALA DE INFORMES " + LETRA_NEGRA + "█" + LETRA_BLANCA + " El pasajero " + p.getDni() + " sale de Informes" + RESET);
        }

        Thread.sleep(aleatorio.nextInt(500) + 500);

        return salida;
    }

    private Vuelo getVueloPosible(Pasajero p) {
        int i = 0, j = 0;
        int horarioValido = this.hora.getHora() + 2;
        Vuelo salida = null;

        if (horarioValido > 21) {
            System.out.println(FONDO_ROJO + "█" + LETRA_BLANCA + " SALA DE INFORMES " + LETRA_NEGRA + "█" + LETRA_BLANCA + " El pasajero " + p.getDni() + " no consigue un vuelo, ya no se dan mas vuelos a esta hora." + RESET);
        } else {
            boolean hayVuelos = false, corte = false;
            int randomVuelo;
            int randomAerolinea;
            ListaInt aerolineasAzar = new ListaInt();

            //verifica si cada aerolinea tiene por lo menos un vuelo por salir, y las agrega la posicion de la aerolinea
            //en un arreglo, despues hace un random entre esas aerolineas
            while (i < this.aerolineas.length) {
                while (j < this.aerolineas[i].getArreVuelos().length && !hayVuelos) {
                    if (this.aerolineas[i].getArreVuelos()[j].getHorario() >= horarioValido) {
                        hayVuelos = true;
                    }
                    j++;
                }
                if (hayVuelos) {
                    aerolineasAzar.insertar(i, aerolineasAzar.longitud() + 1);
                    hayVuelos = false;
                }
                j = 0;
                i++;
            }
            if (!aerolineasAzar.esVacia()) {
                randomAerolinea = aerolineasAzar.recuperar((int) (Math.random() * (aerolineasAzar.longitud()) + 1));
                //como el arreglo de vuelos esta ordenado de manera creciente (segun el horario), entonces se calcula la
                //posicion en la cual, a partir de esta hay vuelos validos, luego se hace un random entre esa posicion y la ultima
                i = 0;
                while (i < this.aerolineas[randomAerolinea].getArreVuelos().length && !corte) {
                    if (this.aerolineas[randomAerolinea].getArreVuelos()[i].getHorario() >= horarioValido) {
                        corte = true;
                    } else {
                        i++;
                    }
                }
                randomVuelo = (int) ((Math.random() * this.aerolineas[randomAerolinea].getArreVuelos().length - i) + i);

                if (this.aerolineas[randomAerolinea].getArreVuelos()[randomVuelo].getCapacidad() == 0) {
                    System.out.println(FONDO_ROJO + "█" + LETRA_BLANCA + " SALA DE INFORMES " + LETRA_NEGRA + "█" + LETRA_BLANCA + " El pasajero " + p.getDni() + " no consigue un vuelo, no queda capacidad del vuelo elegido." + RESET);
                } else {
                    salida = aerolineas[randomAerolinea].getArreVuelos()[randomVuelo];
                }
            }
            else
                System.out.println(FONDO_ROJO + "█" + LETRA_BLANCA + " SALA DE INFORMES " + LETRA_NEGRA + "█" + LETRA_BLANCA + " El pasajero " + p.getDni() + " no consigue un vuelo, no quedan mas vuelos disponibles de ninguna aerolinea." + RESET);

        }

        return salida;
    }
}

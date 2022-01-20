package SistemaAeropuerto;

import java.util.Random;

public class Pasajero extends Thread {

    public static final String RESET = "\u001B[0m";
    public static final String LETRA_NEGRA = "\u001B[30m";
    public static final String LETRA_BLANCA = "\u001B[37m";

    public static final String FONDO_ROJO = "\u001B[41m";
    public static final String FONDO_VERDE = "\u001B[42m";
    public static final String FONDO_PURPURA = "\u001B[45m";
    public static final String FONDO_CYAN = "\u001B[46m";

    private final int dni;
    private final String nombre;
    private final Aeropuerto aeropuerto;
    private Vuelo pasajeDeVuelo;
    private final Random aleatorio = new Random();
    private final Reloj hora;
    private final Object avisoGuardia;

    public Pasajero(int dniNuevo, String nombre, Aeropuerto a, String aero, Reloj r, Object av) {
        this.dni = dniNuevo;
        this.aeropuerto = a;
        this.nombre = nombre;
        this.hora = r;
        this.avisoGuardia = av;
    }

    public int getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public Vuelo getPasajeDeVuelo() {
        return pasajeDeVuelo;
    }

    public void setPasajeDeVuelo(Vuelo pasajeDeVuelo) {
        this.pasajeDeVuelo = pasajeDeVuelo;
    }

    public void run() {

        try {
            Thread.sleep(aleatorio.nextInt(1000) + 1000); //entrar aeropuerto
            this.aeropuerto.entrarHall(this);
            if (this.hora.aeropuertoAbierto()) {
                int puesto = this.aeropuerto.getSalaInicial().atenderEnInformes(this);
                if (this.pasajeDeVuelo != null) {
                    if (this.hora.aeropuertoAbierto()) {
                        if (this.aeropuerto.getPuestosAtencion()[puesto].size() == 3) {
                            System.out.println("█ HALL CENTRAL █ El pasajero " + this.dni + " no puede entrar al puesto " + this.pasajeDeVuelo.getNombreAerolinea() + ", está lleno. Espera en Hall Central.");
                        }
                        this.aeropuerto.getPuestosAtencion()[puesto].put(this);

                        System.out.println(FONDO_VERDE + "█ PUESTO ATENCION: " + this.pasajeDeVuelo.getNombreAerolinea() + " █ El pasajero " + this.dni + " entra. Ocupado: " + this.aeropuerto.getPuestosAtencion()[puesto].size() + RESET);

                        if (this.hora.getHora() < 20) {
                            Thread.sleep(aleatorio.nextInt(2000) + 5000);
                        }

                        synchronized (avisoGuardia) {
                            this.avisoGuardia.notify();
                        }

                        if (this.hora.aeropuertoAbierto()) {
                            if (this.hora.getHora() <= this.pasajeDeVuelo.getHorario()) {
                                this.aeropuerto.getTransporte().entrarATransporte(this);
                                this.pasajeDeVuelo.getTerminal().entrarTerminal(this);
                                boolean deseaFreeshop = aleatorio.nextBoolean();
                                if (deseaFreeshop) {
                                    if (this.pasajeDeVuelo.getTerminal().puedeEntrarFreeshop(this)) {
                                        this.pasajeDeVuelo.getTerminal().getLocalComercial().entrarFreeshop(this);
                                        this.pasajeDeVuelo.getTerminal().getLocalComercial().salirFreeshop(this);
                                    }
                                } else {
                                    System.out.println(FONDO_PURPURA + "█" + LETRA_BLANCA + " TERMINAL " + this.pasajeDeVuelo.getTerminal().getLetra() + LETRA_NEGRA + "█" + LETRA_BLANCA + " El pasajero " + this.dni + " no quiere ir al Freeshop." + RESET);
                                }
                                this.pasajeDeVuelo.getTerminal().tomarVuelo(this);
                                this.pasajeDeVuelo.getTerminal().salirTerminal(this);
                            } else {
                                System.out.println(FONDO_CYAN + "█ AEROPUERTO █ El pasajero " + this.dni + " no puede viajar, pierde su vuelo que arribó a las " + this.pasajeDeVuelo.getHorario() + "." + RESET);
                            }

                        } else {
                            System.out.println(FONDO_CYAN + "█ AEROPUERTO CERRADO █ El pasajero " + this.dni + " no puede viajar, se encontraba en un puesto de atencion." + RESET);
                        }

                    } else {
                        System.out.println(FONDO_CYAN + "█ AEROPUERTO CERRADO █ El pasajero " + this.dni + " no puede viajar, se encontraba en informes." + RESET);
                    }
                } else {
                    System.out.println(FONDO_ROJO + "█" + LETRA_BLANCA + " SALA DE INFORMES " + LETRA_NEGRA + "█" + LETRA_BLANCA + " El pasajero " + this.dni + " no puede viajar, no tiene vuelo." + RESET);
                }
            } else {
                System.out.println(FONDO_CYAN + "█ AEROPUERTO CERRADO █ El pasajero " + this.dni + " no puede viajar, se encontraba en el hall central." + RESET);
            }

        } catch (InterruptedException ex) {
        }
    }
}

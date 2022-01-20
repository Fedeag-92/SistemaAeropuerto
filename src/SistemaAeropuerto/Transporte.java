package SistemaAeropuerto;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Transporte {

    public static final String RESET = "\u001B[0m";
    public static final String FONDO_AMARILLO = "\u001B[43m";

    private final Reloj hora;
    private final Terminal[] terminales;
    private final int capacidad = 5;
    private final int tolerancia = 2;
    private int tiempoLimite = Integer.MAX_VALUE;
    private final ArrayList<Pasajero> pasajeros;
    private final Lock candado = new ReentrantLock();
    private final Condition noVacio = candado.newCondition();
    private final Condition enViaje = candado.newCondition();
    private final Chofer chofer;
    private final Random aleatorio = new Random();

    public Transporte(Reloj r, Terminal[] t) {
        this.hora = r;
        this.terminales = t;
        this.pasajeros = new ArrayList();
        this.chofer = new Chofer(1, "", this, hora);

    }

    public Chofer getChofer() {
        return chofer;
    }

    public void entrarATransporte(Pasajero p) {
        candado.lock();

        try {

            pasajeros.add(p);

            System.out.println(FONDO_AMARILLO + "█ TRANSPORTE █ El pasajero " + p.getDni() + " se subio al transporte. Su vuelo sale a las " + p.getPasajeDeVuelo().getHorario() + RESET);

            System.out.println(FONDO_AMARILLO + "█ TRANSPORTE █ En el transporte hay " + this.pasajeros.size() + " pasajeros." + RESET);

            if (this.pasajeros.size() == 1) {
                this.tiempoLimite = this.hora.getHora() + this.tolerancia;
                System.out.println(FONDO_AMARILLO + "█ TRANSPORTE █ Entró un pasajero, si no se llena antes de la hora " + this.tiempoLimite + " igual sale." + RESET);
            }

            noVacio.signal();

            this.enViaje.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(Transporte.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            candado.unlock();
        }
    }

    public void iniciaViaje() throws InterruptedException {
        candado.lock();
        try {
            while (this.pasajeros.size() < capacidad && this.hora.getHora() < this.tiempoLimite && !algunoConUrgencia() && this.hora.getHora() != 21) {
                System.out.println(FONDO_AMARILLO + "█ TRANSPORTE █ Chofer esperando a salir." + RESET);
                noVacio.await();
            }
            if (this.pasajeros.size() != 0) {
                if (this.pasajeros.size() >= capacidad) {
                    System.out.println(FONDO_AMARILLO + "█ TRANSPORTE █ Se llenó." + RESET);
                } else if (this.hora.getHora() >= this.tiempoLimite) {
                    System.out.println(FONDO_AMARILLO + "█ TRANSPORTE █ Ya esperó hasta las " + this.tiempoLimite + RESET);
                } else if (this.hora.getHora() == 21) {
                    System.out.println(FONDO_AMARILLO + "█ TRANSPORTE █ Ya está por cerrar el aeropuerto." + RESET);
                } else {
                    System.out.println(FONDO_AMARILLO + "█ TRANSPORTE █ Hay un pasajero con urgencia." + RESET);
                }
                System.out.println(FONDO_AMARILLO + "█ TRANSPORTE █ Está en viaje." + RESET);
                Thread.sleep(aleatorio.nextInt(500) + 200);
                for (int i = 0; i < terminales.length; i++) {
                    if (algunoBajaTerminal(terminales[i].getLetra())) {
                        System.out.print(FONDO_AMARILLO + "█ TRANSPORTE █ Se bajan en la terminal " + terminales[i].getLetra() + " los pasajeros: " + RESET);
                        for (int j = 0; j < pasajeros.size(); j++) {
                            if (pasajeros.get(j).getPasajeDeVuelo().getTerminal().getLetra() == terminales[i].getLetra()) {
                                System.out.print(FONDO_AMARILLO + pasajeros.get(j).getDni() + " ," + RESET);
                            }
                        }
                        System.out.println();
                        Thread.sleep(aleatorio.nextInt(500) + 200);
                    } else {
                        System.out.println(FONDO_AMARILLO + "█ TRANSPORTE █ No se baja nadie en la terminal " + terminales[i].getLetra() + RESET);
                        Thread.sleep(50);
                    }
                }

                System.out.println(FONDO_AMARILLO + "█ TRANSPORTE █ Terminó el viaje, vuelve." + RESET);
                Thread.sleep(400);
                this.tiempoLimite = Integer.MAX_VALUE;
                this.pasajeros.clear();
                enViaje.signalAll();
            }

        } finally {
            candado.unlock();
        }
    }

    private boolean algunoBajaTerminal(char let) {
        boolean sale = false;
        int i = 0;

        while (i < pasajeros.size() && !sale) {
            if (pasajeros.get(i).getPasajeDeVuelo().getTerminal().getLetra() == let) {
                sale = true;
            }
            i++;
        }

        return sale;
    }

    private boolean algunoConUrgencia() {
        boolean urgencia = false;
        int i = 0;

        while (!urgencia && i < this.pasajeros.size()) {
            if (this.pasajeros.get(i).getPasajeDeVuelo().getHorario() - this.hora.getHora() <= 1) {
                urgencia = true;
            }
            i++;
        }

        return urgencia;
    }
}

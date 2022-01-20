package SistemaAeropuerto;

import java.util.ArrayList;

public class Terminal {

    public static final String RESET = "\u001B[0m";
    public static final String LETRA_NEGRA = "\u001B[30m";
    public static final String LETRA_BLANCA = "\u001B[37m";

    public static final String FONDO_PURPURA = "\u001B[45m";

    private final char letra;
    private final Freeshop localComercial;
    private final PuestoPartida arrePuestos[];
    private int esperandoSala;
    private final Reloj hora;

    public Terminal(char letra, Reloj h, PuestoPartida[] arrePuestosPartida, ArrayList<Pasajero> s) {
        this.letra = letra;
        this.arrePuestos = arrePuestosPartida;
        this.hora = h;
        this.localComercial = new Freeshop();
        this.esperandoSala = 0;
    }

    public char getLetra() {
        return letra;
    }

    public Freeshop getLocalComercial() {
        return localComercial;
    }

    public PuestoPartida[] getArrePuestos() {
        return arrePuestos;
    }

    public void entrarTerminal(Pasajero p) {
        this.esperandoSala++;
    }

    public boolean puedeEntrarFreeshop(Pasajero p) {
        boolean puede = p.getPasajeDeVuelo().getHorario() > this.hora.getHora() + 1;
        if (this.hora.getHora() <= p.getPasajeDeVuelo().getHorario()) {
            if (puede) {
                System.out.println(FONDO_PURPURA + "█" + LETRA_BLANCA + " TERMINAL " + this.letra + LETRA_NEGRA + " █" + LETRA_BLANCA + " El pasajero " + p.getDni() + " SI puede entrar al Freeshop, tiene tiempo, su vuelo sale a las " + p.getPasajeDeVuelo().getHorario() + "." + RESET);
            } else {
                System.out.println(FONDO_PURPURA + "█" + LETRA_BLANCA + " TERMINAL " + this.letra + LETRA_NEGRA + " █" + LETRA_BLANCA + " El pasajero " + p.getDni() + " quiere ir al Freeshop pero NO puede, no tiene tiempo, su vuelo sale a las " + p.getPasajeDeVuelo().getHorario() + "." + RESET);
            }
        }

        return puede;
    }

    public void tomarVuelo(Pasajero p) throws InterruptedException {
        while (this.hora.getHora() < p.getPasajeDeVuelo().getHorario()) {
            synchronized (this.hora) {
                System.out.println(FONDO_PURPURA + "█" + LETRA_BLANCA + " TERMINAL " + this.letra + LETRA_NEGRA + " █" + LETRA_BLANCA + " El pasajero " + p.getDni() + " espera por su vuelo en la terminal, sale a las " + p.getPasajeDeVuelo().getHorario() + ". Esperando: " + this.esperandoSala + RESET);
                this.hora.wait();
            }
        }

        if (this.hora.getHora() > p.getPasajeDeVuelo().getHorario()) {
            System.out.println(FONDO_PURPURA + "█" + LETRA_BLANCA + " TERMINAL " + this.letra + LETRA_NEGRA + " █" + LETRA_BLANCA + " Lamentablemente el pasajero " + p.getDni() + " perdió su vuelo. Salía a las " + p.getPasajeDeVuelo().getHorario() + RESET);
        } else {
            System.out.println(FONDO_PURPURA + "█" + LETRA_BLANCA + " TERMINAL " + this.letra + LETRA_NEGRA + " █" + LETRA_BLANCA + " El pasajero " + p.getDni() + " se tomo su vuelo a la hora " + p.getPasajeDeVuelo().getHorario() + RESET);
        }
    }

    public void salirTerminal(Pasajero p) {
        this.esperandoSala--;
        if (this.hora.getHora() > p.getPasajeDeVuelo().getHorario()) {
            System.out.println(FONDO_PURPURA + "█" + LETRA_BLANCA + " TERMINAL " + this.letra + LETRA_NEGRA + " █" + LETRA_BLANCA + " El pasajero " + p.getDni() + " deja la terminal por perder su vuelo. Esperando: " + this.esperandoSala + RESET);
        } else {
            System.out.println(FONDO_PURPURA + "█" + LETRA_BLANCA + " TERMINAL " + this.letra + LETRA_NEGRA + " █" + LETRA_BLANCA + " El pasajero " + p.getDni() + " deja la terminal por tomar su vuelo. Esperando: " + this.esperandoSala + RESET);
        }
    }
}

package SistemaAeropuerto;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Guardia extends Thread {

    public static final String RESET = "\u001B[0m";
    public static final String FONDO_VERDE = "\u001B[42m";

    private final int legajo;
    private final String nombre;
    private final ArrayBlockingQueue puesto;
    private final String nombreAerolinea;
    private final Object aviso;
    private final Reloj hora;

    public Guardia(int l, String n, ArrayBlockingQueue p, String nom, Reloj h) {
        this.legajo = l;
        this.nombre = n;
        this.puesto = p;
        this.nombreAerolinea = nom;
        this.aviso = new Object();
        this.hora = h;
    }

    public Object getAviso() {
        return aviso;
    }

    public void run() {
        Pasajero p;
        while (true) {
            if (this.hora.getHora() >= 20 && !this.puesto.isEmpty()) {
                puesto.clear();
                System.out.println(FONDO_VERDE + "█ PUESTO ATENCION: " + this.nombreAerolinea + " █ El guardia desocupa a todos los pasajeros, se acerca la hora de cierre. Ocupado: " + this.puesto.size() + RESET);

            }
            try {
                synchronized (aviso) {
                    aviso.wait();

                    p = (Pasajero) puesto.take();

                    System.out.println(FONDO_VERDE + "█ PUESTO ATENCION: " + this.nombreAerolinea + " █ El pasajero " + p.getDni() + " sale, ya hizo el check in." + RESET);
                    System.out.println(FONDO_VERDE + "█ PUESTO ATENCION: " + this.nombreAerolinea + " █ El guardia avisa que se desocupa un lugar." + RESET);
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(Guardia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

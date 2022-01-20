package SistemaAeropuerto;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Chofer extends Thread {

    private final String nombre;
    private final int dni;
    private final Transporte tren;
    private final Reloj hora;

    public Chofer(int dni, String n, Transporte t, Reloj h) {
        this.dni = dni;
        this.nombre = n;
        this.tren = t;
        this.hora = h;
    }

    public void run() {
        while (true) {
            if (this.hora.aeropuertoAbierto()) {
                try {
                    
                    tren.iniciaViaje();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Chofer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
}
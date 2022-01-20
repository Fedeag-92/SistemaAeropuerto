package SistemaAeropuerto;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;

public class testPrueba {

    public static void main(String[] args) {
        Reloj r = new Reloj();
        Aeropuerto aero = new Aeropuerto(r);
        r.start();
        r.setHall(aero.getHall());
        Pasajero[] pasajeros = new Pasajero[50];
        aero.getGuardias()[0].start(); //LAN
        aero.getGuardias()[1].start(); //Aerolineas Argentinas
        aero.getGuardias()[2].start(); //Flybondi
        aero.getTransporte().getChofer().start();
        Terminal[] terminales = aero.getTerminales();
        PuestoPartida[] salidas;
        for (int i = 0; i < terminales.length; i++) {
            salidas = terminales[i].getArrePuestos();
            for (int j = 0; j < salidas.length; j++) {
                salidas[j].start();
            }
        }
        crearPasajeros(pasajeros, aero, r, aero.getGuardias());
    }

    public static void crearPasajeros(Pasajero arre[], Aeropuerto a1, Reloj r, Guardia[] guardias) {
        String[] aero = {"LAN", "Aerolineas Argentinas", "FlyBondi"};
        Random aleatorio = new Random();
        
        int random;

        for (int i = 0; i < arre.length; i++) {
            random = (int) (Math.random() * 3);
            arre[i] = new Pasajero(i, "", a1, aero[random], r, guardias[random].getAviso());
        }

        for (int i = 0; i < arre.length; i++) {
            try {
                arre[i].start();
                Thread.sleep(aleatorio.nextInt(2000) + 500);
            } catch (InterruptedException ex) {
                Logger.getLogger(testPrueba.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

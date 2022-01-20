package SistemaAeropuerto;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Freeshop {
    
    private final int maximo = 2;
    private final Semaphore control = new Semaphore(maximo);
    private final Random aleatorio = new Random();
    private int ocupado = 0;

    public Freeshop() {
    }

    public void entrarFreeshop(Pasajero p) {
        try {
            if (this.control.availablePermits() == 0) {
                System.out.println("█ FREESHOP █ El pasajero " + p.getDni() + " espera para entrar al Freeshop de la terminal " + p.getPasajeDeVuelo().getTerminal().getLetra() + "(lleno).");
            }
            this.control.acquire();
            Thread.sleep(aleatorio.nextInt(1000));
            this.ocupado++;
            System.out.println("█ FREESHOP █ El pasajero " + p.getDni() + " se encuentra en el Freeshop de la terminal " + p.getPasajeDeVuelo().getTerminal().getLetra() + ". Ocupado: " + this.ocupado);
            Thread.sleep(aleatorio.nextInt(5000) + 5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Freeshop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void salirFreeshop(Pasajero p) {
        this.ocupado--;
        System.out.println("█ FREESHOP █ El pasajero " + p.getDni() + " sale del Freeshop de la terminal " + p.getPasajeDeVuelo().getTerminal().getLetra() + ". Ocupado: " + this.ocupado);
        this.control.release();

    }

}

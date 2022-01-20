package SistemaAeropuerto;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PuestoPartida extends Thread {

    public static final String RESET = "\u001B[0m";
    public static final String LETRA_BLANCA = "\u001B[37m";
    public static final String LETRA_NEGRA = "\u001B[30m";

    public static final String FONDO_AZUL = "\u001B[44m";

    private final int nro;
    private final ArrayList<Vuelo> vuelosAsignados = new ArrayList<>();
    private final Reloj hora;

    public PuestoPartida(int nro, Reloj r) {
        this.nro = nro;
        this.hora = r;
    }

    public void agregarVuelo(Vuelo unVuelo) {

        if (verificarHorario(unVuelo.getHorario())) {
            this.vuelosAsignados.add(unVuelo);
        }
    }

    public ArrayList<Vuelo> getVuelosAsignados() {
        return vuelosAsignados;
    }

    public boolean verificarHorario(int unHorario) {

        boolean exito = true;
        int i = 0;

        while (i < this.vuelosAsignados.size() && exito) {

            if (this.vuelosAsignados.get(i).getHorario() != unHorario) {
                i++;
            } else {
                exito = false;
            }
        }
        return exito;
    }

    public void mostrarVuelosAsignados() {
        for (int j = 0; j < this.vuelosAsignados.size(); j++) {
            System.out.println("Puesto: " + this.nro + " Vuelo: " + this.vuelosAsignados.get(j).getId() + " Horario: " + this.vuelosAsignados.get(j).getHorario() + " hs." + " Capacidad: " + this.vuelosAsignados.get(j).getCapacidad());
        }
    }

    public int getNro() {
        return nro;
    }

    public void run() {
        while (true) {
            if (this.hora.aeropuertoAbierto()) {
                synchronized (this.hora) {
                    try {
                        this.hora.wait();
                        for (int i = 0; i < vuelosAsignados.size(); i++) {
                            if (vuelosAsignados.get(i).getHorario() == this.hora.getHora()) {
                                System.out.println(FONDO_AZUL + "█" + LETRA_BLANCA + " PUESTO PARTIDA " + this.nro + LETRA_NEGRA + " █" + LETRA_BLANCA + " El vuelo " + this.vuelosAsignados.get(i).getId() + " está arribando." + RESET);
                                this.vuelosAsignados.remove(vuelosAsignados.get(i));
                            }
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PuestoPartida.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }
    }
}

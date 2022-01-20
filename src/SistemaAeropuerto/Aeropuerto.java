package SistemaAeropuerto;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import utiles.MetodosOrdenamiento;

public class Aeropuerto {

    public static final String RESET = "\u001B[0m";
    public static final String FONDO_CYAN = "\u001B[46m";

    private final Reloj hora;
    private final Object Hall = new Object();
    private final SalaInformes salaInicial;
    private final BlockingQueue[] puestosAtencion;
    private final Terminal[] terminales;
    private final Transporte transporte;
    private final Aerolinea[] aerolineas;
    private final Random aleatorio = new Random();
    private final Guardia[] guardias;

    public Aeropuerto(Reloj hora) {
        this.hora = hora;
        this.terminales = new Terminal[3];
        this.aerolineas = new Aerolinea[3];
        this.puestosAtencion = new BlockingQueue[3];
        this.guardias = new Guardia[3];
        cargarAerolineas();
        cargarPuestosAtencion();
        this.salaInicial = new SalaInformes(hora, aerolineas);
        cargarTerminales();
        this.transporte = new Transporte(hora, terminales);
        asignarPuestosEmbarque();
    }

    public Object getHall() {
        return Hall;
    }

    public Transporte getTransporte() {
        return transporte;
    }

    public Terminal[] getTerminales() {
        return this.terminales;
    }

    public void entrarHall(Pasajero p) throws InterruptedException {
        System.out.println(FONDO_CYAN + "█ AEROPUERTO █ El pasajero " + p.getDni() + " entró." + RESET);
        while (!hora.aeropuertoAbierto()) {
            synchronized (this.Hall) {
                System.out.println(FONDO_CYAN + "█ AEROPUERTO █ El pasajero " + p.getDni() + " espera en el Hall central." + RESET);
                this.Hall.wait();
                Thread.sleep(aleatorio.nextInt(3000) + 1500);
            }
        }
    }

    public Guardia[] getGuardias() {
        return this.guardias;
    }

    public SalaInformes getSalaInicial() {
        return salaInicial;
    }

    public BlockingQueue[] getPuestosAtencion() {
        return puestosAtencion;
    }

    private void cargarAerolineas() {
        Aerolinea a1 = new Aerolinea("LAN");
        Aerolinea a2 = new Aerolinea("Aerolineas Argentinas");
        Aerolinea a3 = new Aerolinea("FlyBondi");

        this.aerolineas[0] = a1;
        this.aerolineas[1] = a2;
        this.aerolineas[2] = a3;
    }

    private void cargarPuestosAtencion() {
        ArrayBlockingQueue puesto1 = new ArrayBlockingQueue(3);
        ArrayBlockingQueue puesto2 = new ArrayBlockingQueue(3);
        ArrayBlockingQueue puesto3 = new ArrayBlockingQueue(3);

        puestosAtencion[0] = puesto1;
        puestosAtencion[1] = puesto2;
        puestosAtencion[2] = puesto3;

        Guardia g1 = new Guardia(1, "", puesto1, "LAN", this.hora);
        Guardia g2 = new Guardia(2, "", puesto2, "Aerolineas Argentinas", this.hora);
        Guardia g3 = new Guardia(3, "", puesto3, "Flybondi", this.hora);

        guardias[0] = g1;
        guardias[1] = g2;
        guardias[2] = g3;
    }

    private void cargarTerminales() {
        PuestoPartida[] puestosAux = new PuestoPartida[7];

        ArrayList<Pasajero> salaCompartida = new ArrayList();
        PuestoPartida p1 = new PuestoPartida(1, this.hora);
        PuestoPartida p2 = new PuestoPartida(2, this.hora);
        PuestoPartida p3 = new PuestoPartida(3, this.hora);
        PuestoPartida p4 = new PuestoPartida(4, this.hora);
        PuestoPartida p5 = new PuestoPartida(5, this.hora);
        PuestoPartida p6 = new PuestoPartida(6, this.hora);
        PuestoPartida p7 = new PuestoPartida(7, this.hora);
        puestosAux[0] = p1;
        puestosAux[1] = p2;
        puestosAux[2] = p3;
        puestosAux[3] = p4;
        puestosAux[4] = p5;
        puestosAux[5] = p6;
        puestosAux[6] = p7;

        terminales[0] = new Terminal('A', this.hora, puestosAux, salaCompartida);

        puestosAux = new PuestoPartida[8];

        salaCompartida = new ArrayList();

        PuestoPartida p8 = new PuestoPartida(8, this.hora);
        PuestoPartida p9 = new PuestoPartida(9, this.hora);
        PuestoPartida p10 = new PuestoPartida(10, this.hora);
        PuestoPartida p11 = new PuestoPartida(11, this.hora);
        PuestoPartida p12 = new PuestoPartida(12, this.hora);
        PuestoPartida p13 = new PuestoPartida(13, this.hora);
        PuestoPartida p14 = new PuestoPartida(14, this.hora);
        PuestoPartida p15 = new PuestoPartida(15, this.hora);
        puestosAux[0] = p8;
        puestosAux[1] = p9;
        puestosAux[2] = p10;
        puestosAux[3] = p11;
        puestosAux[4] = p12;
        puestosAux[5] = p13;
        puestosAux[6] = p14;
        puestosAux[7] = p15;

        terminales[1] = new Terminal('B', this.hora, puestosAux, salaCompartida);

        puestosAux = new PuestoPartida[5];

        salaCompartida = new ArrayList();

        PuestoPartida p16 = new PuestoPartida(16, this.hora);
        PuestoPartida p17 = new PuestoPartida(17, this.hora);
        PuestoPartida p18 = new PuestoPartida(18, this.hora);
        PuestoPartida p19 = new PuestoPartida(19, this.hora);
        PuestoPartida p20 = new PuestoPartida(20, this.hora);
        puestosAux[0] = p16;
        puestosAux[1] = p17;
        puestosAux[2] = p18;
        puestosAux[3] = p19;
        puestosAux[4] = p20;

        terminales[2] = new Terminal('C', this.hora, puestosAux, salaCompartida);
    }

    private synchronized void asignarPuestosEmbarque() {

        for (int i = 0; i < this.aerolineas.length; i++) {
            for (int j = 0; j < this.aerolineas[i].getArreVuelos().length; j++) {
                int posTerminal = 0;
                boolean libre = false;
                int posPuestoPartida = 0;

                while (!libre) {
                    posTerminal = (int) (Math.random() * 3);
                    posPuestoPartida = (int) (Math.random() * this.terminales[posTerminal].getArrePuestos().length);
                    //Se verifica que no haya un vuelo en un mismo horario en un mismo puesto de embarque.
                    if (this.terminales[posTerminal].getArrePuestos()[posPuestoPartida].verificarHorario(this.aerolineas[i].getArreVuelos()[j].getHorario())) {
                        libre = true;
                    }
                }

                //Seteamos los datos al vuelo.
                this.aerolineas[i].getArreVuelos()[j].setTerminal(this.terminales[posTerminal]);
                this.aerolineas[i].getArreVuelos()[j].setPuestoPartida(this.terminales[posTerminal].getArrePuestos()[posPuestoPartida]);
                this.terminales[posTerminal].getArrePuestos()[posPuestoPartida].agregarVuelo(this.aerolineas[i].getArreVuelos()[j]);
            }
        }

        //Ordena la lista de vuelos por horario en cada puesto de embarque de cada terminal
        for (Terminal terminale : this.terminales) {
            for (PuestoPartida arrePuesto : terminale.getArrePuestos()) {
                MetodosOrdenamiento.heapSort(arrePuesto.getVuelosAsignados());
            }
        }
        
        for (int i = 0; i < this.terminales.length; i++) {
            System.out.println("Terminal: " + this.terminales[i].getLetra());

            for (int j = 0; j < this.terminales[i].getArrePuestos().length; j++) {
                this.terminales[i].getArrePuestos()[j].mostrarVuelosAsignados();
            }
        }
    }
}

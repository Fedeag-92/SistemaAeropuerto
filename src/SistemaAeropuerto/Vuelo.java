package SistemaAeropuerto;

public class Vuelo {

    private final String id;
    private String nombreAerolinea;
    private int horario;
    private Terminal terminal;
    private PuestoPartida puestoPartida;
    private int capacidad;

    public Vuelo(String aero, int h, int c) {
        this.nombreAerolinea = aero;
        this.horario = h;
        this.capacidad = c;
        this.id = aero.charAt(0) + Integer.toString(this.horario);
    }

    public String getId() {
        return id;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public synchronized void setCapacidad() {
        this.capacidad--;
        if (capacidad < 0) {
            capacidad = 0;
        }
    }

    public String getNombreAerolinea() {
        return nombreAerolinea;
    }

    public void setNombreAerolinea(String nombreAerolinea) {
        this.nombreAerolinea = nombreAerolinea;
    }

    public int getHorario() {
        return horario;
    }

    public void setHorario(int horario) {
        this.horario = horario;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public PuestoPartida getPuestoPartida() {
        return puestoPartida;
    }

    public void setPuestoPartida(PuestoPartida puestoPartida) {
        this.puestoPartida = puestoPartida;
    }

}

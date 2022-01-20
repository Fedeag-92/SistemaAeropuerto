package SistemaAeropuerto;

import utiles.MetodosOrdenamiento;

public class Aerolinea {

    private String nombre;
    private final Vuelo[] arreVuelos;

    public Aerolinea(String nombre) {
        this.nombre = nombre;
        this.arreVuelos = new Vuelo[5];
        cargarVuelos();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Vuelo[] getArreVuelos() {
        return arreVuelos;
    }

    private void cargarVuelos() {

        for (int i = 0; i < 5; i++) {
            this.arreVuelos[i] = (new Vuelo(this.nombre, (int) (Math.random() * (14) + 8), (int) (Math.random() * (5) + 2)));
        }
        MetodosOrdenamiento.heapSortPorHorario(arreVuelos);
    }

    public void mostrarVuelos() {
        System.out.println("");

        String cadena = "";
        for (int i = 0; i < arreVuelos.length; i++) {
            cadena += "ID: " + arreVuelos[i].getId() + " Horario: " + arreVuelos[i].getHorario() + " Capacidad:" + arreVuelos[i].getCapacidad();
            cadena += '\n';
        }
        System.out.println(cadena);
        System.out.println("");
    }
}

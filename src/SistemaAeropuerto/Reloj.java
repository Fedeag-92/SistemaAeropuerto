package SistemaAeropuerto;

public class Reloj extends Thread {

    private boolean permiso;
    private int h = 0;
    private Object Hall;

    public synchronized int getHora() {
        return h;
    }

    public void setHall(Object Hall) {
        this.Hall = Hall;
    }

    synchronized void hora() throws InterruptedException {
        this.h = this.h + 1;

        if (h >= 6 && h <= 21) {

            if (h == 6) {
                System.out.println("\n                 = = = = = = = = = = = = = = = = = > AEROPUERTO ABIERTO < = = = = = = = = = = = = = = = = = \n");
                this.permiso = true;

                synchronized (this.Hall) {
                    this.Hall.notifyAll();
                }
            }
        } else {
            if (h >= 21 && h < 6) {
                this.permiso = false;
            }

            if (h == 22) {
                System.out.println("\n                 = = = = = = = = = = = = = = = = = > AEROPUERTO CERRADO < = = = = = = = = = = = = = = = = = \n");
                this.permiso = false;
            }

            if (h == 24) {
                h = 0;
            }
        }
        this.notifyAll();
    }

    synchronized boolean aeropuertoAbierto() {
        return permiso;
    }

    public void run() {
        while (true) {
            try {
                this.hora();
                String imprimir = "╔══════╗\n";
                if (this.h >= 0 && this.h <= 9) {
                    imprimir = imprimir + "║  0" + this.h + ":00   ║\n";
                } else {
                    imprimir = imprimir + "║  " + this.h + ":00   ║\n";
                }
                imprimir = imprimir + "╚══════╝";
                System.out.println(imprimir);
                Thread.sleep(2500);
            } catch (InterruptedException ex) {
            }
        }
    }
}

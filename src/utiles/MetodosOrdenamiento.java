package utiles;

import SistemaAeropuerto.Vuelo;
import java.util.ArrayList;

public class MetodosOrdenamiento {

//MÉTODO DE ORDENAMIENTO BURBUJA MEJORADO
    public static void burbujaMejorado(int[] arre) //ORDEN N^2
    {
        boolean bandera = true;
        int aux;
        int i = 0;
        int tamaño = arre.length;

        while (i < tamaño - 1 && bandera) {
            bandera = false;

            for (int j = 0; j < tamaño - 1 - i; j++) {
                if (arre[j] > arre[j + 1]) {
                    bandera = true;
                    aux = (arre[j]);
                    arre[j] = arre[j + 1];
                    arre[j + 1] = aux;
                }
            }
        }
        i++;
    }

//______________________________________________________________________________
//MÉTODO DE ORDENAMIENTO QUICKSORT
    public static void quicksort(int min, int max, int arre[]) //ORDEN (N log N)
    {
        int i = min, j = max;
        // creamos un pivot que va a ser el valor del medio del arreglo
        int pivot = arre[min + (max - min) / 2];

        // divide en dos el arreglo
        while (i <= j) {
            // si el valor actual es menor al del pivot
            // el elemento pasa a la otra posicion del arreglo
            while (arre[i] < pivot) {
                i++;
            }
            // si el valor es mayor al del pivot
            // el elemento pasa a la a posicion siguiente (j--)
            while (arre[j] > pivot) {
                j--;
            }

            /*Si encontramos un elemento mayor al pivot en el lado izquierdo
              y si encontramos un elemento menor al pivot del lado derecho los intercambiamos
              una vez hecho aumentamos i y disminuimos j.*/
            if (i <= j) {
                cambiar(i, j, arre);
                i++;
                j--;
            }
        }
        // Recursion
        if (min < j) {
            quicksort(min, j, arre);
        }
        if (i < max) {
            quicksort(i, max, arre);
        }
    }

    private static void cambiar(int i, int j, int[] arreglo) {
        int aux = arreglo[i];
        arreglo[i] = arreglo[j];
        arreglo[j] = aux;
    }

//______________________________________________________________________________
//MÉTODO DE ORDENAMIENTO HEAPSORT
    public static void heapSortPorHorario(Vuelo[] arre) {
        final int N = arre.length;
        for (int nodo = N / 2; nodo >= 0; nodo--) {
            auxHeapSort(arre, nodo, N - 1);
        }
        for (int nodo = N - 1; nodo >= 0; nodo--) {
            Vuelo aux = arre[0];
            arre[0] = arre[nodo];
            arre[nodo] = aux;
            auxHeapSort(arre, 0, nodo - 1);
        }
    }

    private static void auxHeapSort(Vuelo[] arre, int nodo, int fin) {
        int izq = 2 * nodo + 1;
        int der = izq + 1;
        int may;
        if (izq > fin) {
            return;
        }
        if (der > fin) {
            may = izq;
        } else {
            may = arre[izq].getHorario() > arre[der].getHorario() ? izq : der;
        }
        if (arre[nodo].getHorario() < arre[may].getHorario()) {
            Vuelo aux = arre[nodo];
            arre[nodo] = arre[may];
            arre[may] = aux;
            auxHeapSort(arre, may, fin);
        }
    }
    
        public static void heapSort(ArrayList <Vuelo>arre) {
        final int N = arre.size();
        for (int nodo = N / 2; nodo >= 0; nodo--) {
            auxHeapSort(arre, nodo, N - 1);
        }
        for (int nodo = N - 1; nodo >= 0; nodo--) {
            Vuelo aux = arre.get(0);
            arre.set(0, arre.get(nodo));
            arre.set(nodo, aux);
            auxHeapSort(arre, 0, nodo - 1);
        }
    }

    private static void auxHeapSort(ArrayList <Vuelo> arre, int nodo, int fin) {
        int izq = 2 * nodo + 1;
        int der = izq + 1;
        int may;
        if (izq > fin) {
            return;
        }
        if (der > fin) {
            may = izq;
        } else {
            may = arre.get(izq).getHorario() > arre.get(der).getHorario() ? izq : der;
        }
        if (arre.get(nodo).getHorario() < arre.get(may).getHorario()) {
            Vuelo aux = arre.get(nodo);
            arre.set(nodo, arre.get(may));
            arre.set(may, aux);
            auxHeapSort(arre, may, fin);
        }
    }

//______________________________________________________________________________
//MÉTODO DE ORDENAMIENTO MERGESORT
    public static void mergesort(int arre[], int izq, int der) {
        if (izq < der) {
            int m = (izq + der) / 2;
            mergesort(arre, izq, m);
            mergesort(arre, m + 1, der);
            merge(arre, izq, m, der);
        }
    }

    private static void merge(int arre[], int izq, int m, int der) {
        int i, j, k;
        int[] aux = new int[arre.length]; //array auxiliar
        for (i = izq; i <= der; i++) //copia ambas mitades en el array auxiliar
        {
            aux[i] = arre[i];
        }

        i = izq;
        j = m + 1;
        k = izq;
        while (i <= m && j <= der) //copia el siguiente elemento más grande
        {
            if (aux[i] <= aux[j]) {
                arre[k++] = aux[i++];
            } else {
                arre[k++] = aux[j++];
            }
        }
        while (i <= m) //copia los elementos que quedan de la
        {
            arre[k++] = aux[i++]; //primera mitad (si los hay)
        }
    }

//______________________________________________________________________________
//MÉTODO PARA MOSTRAR ARREGLO     
    public static void mostrarArre(int[] arre) {
        String cadena = "[ ";

        for (int i = 0; i < arre.length; i++) {
            cadena += arre[i];

            if (i != arre.length - 1) {
                cadena += ", ";
            }
        }
        cadena += " ]";
        System.out.println(cadena);
    }
}

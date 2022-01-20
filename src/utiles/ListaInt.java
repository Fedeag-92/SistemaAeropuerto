package utiles;

public class ListaInt
{

    private NodoInt cabecera;
    private int largo;

    public ListaInt()
    {
        cabecera = null;
        largo = 0;
    }

    public boolean insertar(int elem, int pos)
    {

        boolean exito = true;

        if (pos < 1 || pos > largo + 1)
        {
            exito = false;
        }
        else
        {
            if (pos == 1)
            {
                this.cabecera = new NodoInt(elem, this.cabecera);
            }
            //crea un nuevo nodo y lo enlaza a la cabecera
            else
            {
                NodoInt aux = this.cabecera; //avanza hasta el elemento en posicion pos-1
                int i = 1;
                while (i < pos - 1)
                {
                    aux = aux.getEnlace();
                    i++;
                }
                NodoInt nuevo = new NodoInt(elem, aux.getEnlace()); //crea el nodo y lo enlaza
                aux.setEnlace(nuevo);
            }
            largo++;
        }
        return exito;
    }

    public boolean eliminar(int pos)
    { //dudas, pero elimina bien :|
        boolean exito = false;

        if (!this.esVacia() && pos >= 1 && pos <= largo)
        {
            if (pos == 1)
            {
                this.cabecera = this.cabecera.getEnlace();
            }
            else
            {
                NodoInt aux = this.cabecera;
                int i = 1;
                while (i < pos - 1)
                {
                    aux = aux.getEnlace(); //avanza hasta el nodo de la pos-1 y corta
                    i++;
                }
                aux.setEnlace(aux.getEnlace().getEnlace()); //enlaza el nodo 2 posiciones mas
            }
            largo--;
            exito = true;
        }
        return exito;
    }

    public int recuperar(int pos)
    {
        int sale = -1;

        NodoInt aux = this.cabecera;

        if (pos >= 1 && pos <= largo)
        {
            int i = 1;
            while (i < pos)
            {
                aux = aux.getEnlace();
                i++;
            }
            sale = aux.getElem();
        }
        return sale;
    }

    public int localizar(int elem)
    { //DUDAS, igual anda

        int pos = 1;
        boolean encuentra = false;
        NodoInt aux = this.cabecera;
        while (aux != null && encuentra != true)
        {
            if (aux.getElem() == elem)
            {
                encuentra = true;
            }
            else
            {
                aux = aux.getEnlace();
                pos++;
            }
        }
        if (encuentra == false)
        {
            pos = -1;
        }
        return pos;
    }

    public ListaInt invertir()
    {
        ListaInt listaInv = new ListaInt();
        if (!this.esVacia())
        {
            
            int largoLista = this.longitud(), i = 1;

            while (largoLista > 0)
            {
                listaInv.insertar(this.recuperar(largoLista), i);
                i++;
                largoLista--;
            }
        }
        return listaInv;
    }

    public void vaciar()
    {
        this.cabecera = null;
        this.largo = 0;
    }

    public boolean esVacia()
    {

        return (cabecera == null);
    }

    public ListaInt clonar()
    {

        ListaInt ListaClonada = new ListaInt();
        NodoInt aux = this.cabecera;
        int pos = 1;
        while (aux != null)
        {
            ListaClonada.insertar(aux.getElem(), pos);
            aux = aux.getEnlace();
            pos++;
        }
        return ListaClonada;
    }

    public int longitud()
    {
        return largo;
    }

    public String aString()
    {

        String cadena = "";
        NodoInt aux = this.cabecera;

        while (aux != null)
        {
            cadena += "[ " + aux.getElem() + " ]";
            aux = aux.getEnlace();
        }
        return cadena;
    }
    
    public void eliminarImpares(){
        while(this.cabecera.getElem() % 2 == 1 && this.cabecera != null){
            this.cabecera = this.cabecera.getEnlace();
        }
        
        NodoInt aux = this.cabecera;
        NodoInt ant = this.cabecera;
        while(aux.getEnlace() != null){
            if(aux.getEnlace().getElem() % 2 == 1){
                ant = aux;
                aux = aux.getEnlace().getEnlace();
                ant.setEnlace(aux);
            }
            else
                aux = aux.getEnlace();
        }
    }

}

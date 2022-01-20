package utiles;

public class NodoInt {
    private int elem;
    private NodoInt enlace;

    public NodoInt(int elem, NodoInt enlace){
        this.elem = elem;
        this.enlace = enlace;
    }

    public NodoInt(int elemN){
        this.elem = elemN;
        this.enlace = null;
    }

    public int getElem(){
        return elem;
    }

    public void setElem(int elem){
        this.elem = elem;
    }

    public NodoInt getEnlace(){
        return enlace;
    }

    public void setEnlace(NodoInt enlace){
        this.enlace = enlace;
    }

}

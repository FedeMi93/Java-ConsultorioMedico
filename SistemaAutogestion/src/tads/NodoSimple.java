package tads;


public class NodoSimple<T extends Comparable> {

    private T dato;
    private NodoSimple<T> siguiente;
    
    public NodoSimple(T elDato){
        this.setDato(elDato);
        this.setSiguiente(null); 
    }

  
    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public NodoSimple getSiguiente() {
        return siguiente;
    }

   
    public void setSiguiente(NodoSimple siguiente) {
        this.siguiente = siguiente;
    }
    
}

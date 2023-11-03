package tads;


public class NodoDoble<T extends Comparable> {

    private T dato;
    private NodoDoble<T> siguiente;
    private NodoDoble<T> anterior;
    
    public NodoDoble(T elDato){
        this.setDato(elDato);
        this.setSiguiente(null);
        this.setAnterior(null);
    }

  
    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public NodoDoble getSiguiente() {
        return siguiente;
    }

   
    public void setSiguiente(NodoDoble siguiente) {
        this.siguiente = siguiente;
    }

    public NodoDoble<T> getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoDoble<T> anterior) {
        this.anterior = anterior;
    }
    
}

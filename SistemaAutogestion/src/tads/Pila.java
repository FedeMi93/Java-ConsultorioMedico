package tads;


public class Pila<T extends Comparable<T>> implements IPila {

    private NodoSimple<T> tope;
    private int cant;

    public Pila() {
        this.tope = null;
        cant = 0;
    }

    public boolean estaVacia() {
        return tope == null;
    }

    @Override
    public void apilar(Object dato) {
       NodoSimple n = new NodoSimple((Comparable) dato);
       n.setSiguiente(tope);       
       tope = n;
       cant++;
    }

    @Override
    public T desapilar() {
       if(estaVacia()){
            return null;
       }
       NodoSimple<T> aux = tope;
       tope = tope.getSiguiente();
       cant--;
       aux.setSiguiente(null);
       return aux.getDato();
    }

    public T top() {
        if(estaVacia()){
            return null;
        }
        return tope.getDato();       
    }

    @Override
    public void vaciar() {
        tope = null;
        cant = 0;        
    }

    @Override
    public int cantidadNodos() {
        return cant;
    }
    
    public void mostrar(){
       if(!estaVacia()){
           NodoSimple<T> aux = tope;
           while(aux != null){
               System.out.println(aux.getDato());
               aux = aux.getSiguiente();
           }
       }
    }
    public Pila copiar(){
        
        Pila p3 = new Pila();
        Pila p2 = new Pila();
        
        while(!this.estaVacia()){
            p3.apilar(this.desapilar());            
        }
        while(!p3.estaVacia()){
            Comparable dato = p3.desapilar();
            this.apilar(dato);
            p2.apilar(dato);
        }        
        return p2;
    }
    
}

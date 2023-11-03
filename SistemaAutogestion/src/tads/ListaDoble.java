/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tads;

/**
 *
 * @author marod
 */
public class ListaDoble<T extends Comparable<T>> implements ILista<T> {

    private NodoDoble<T> inicio;
    private NodoDoble<T> fin; 
    private int cantElementos;

    public ListaDoble() {
        cantElementos = 0;
        inicio = null;
        fin = null;
    }

    public NodoDoble getInicio() {
        return inicio;
    }

    public void setInicio(NodoDoble inicio) {
        this.inicio = inicio;
    }
    public NodoDoble getFin(){
        return fin;
    }

    public void setFin(NodoDoble<T> fin) {
        this.fin = fin;
    }    
   

    @Override
    public void vaciar() {
        this.inicio = null;
        this.fin = null;
        cantElementos = 0;
    }

    @Override
    public void eliminarInicio() {
        
        if (!esVacia()) {
            NodoDoble borrar = inicio;
            inicio = inicio.getSiguiente();
            borrar.setSiguiente(null);            
            cantElementos--;
        }
        if(esVacia()){
            fin = null;
        } else {
            inicio.setAnterior(null);
        }
        
    }

    @Override
    public void eliminarFinal() {        
        if (!esVacia()) {
            if (inicio.getDato() == fin.getDato()) {
                this.vaciar();
            } else {
                NodoDoble aBorrar = fin;
                fin = fin.getAnterior();
                fin.setSiguiente(null);
                aBorrar.setAnterior(null);
                cantElementos--;
            }
        }
        
    }

    @Override
    public int getCantidadElementos() {

        return cantElementos;
    }

    @Override
    public void agregarFinal(T x) {
        
        NodoDoble nuevo = new NodoDoble(x);
        if (esVacia()) {
            inicio = nuevo;
            fin = nuevo;
        } else {
            NodoDoble alFinal = fin;
            alFinal.setSiguiente(nuevo);
            nuevo.setAnterior(fin);
            fin = nuevo;
        }
        cantElementos++;
        
    }

    @Override
    public void agregarInicio(T n) {
        
        NodoDoble nuevo = new NodoDoble(n);
        if(esVacia()){
            fin = nuevo;
            inicio = nuevo;            
        } else {            
            nuevo.setSiguiente(inicio);            
            inicio.setAnterior(nuevo);
            inicio = nuevo;                
        }
        cantElementos++;
    }

    @Override
    public void mostrar() {        
        
        NodoDoble mostrar = inicio;

        while (mostrar != null) {
            System.out.println(mostrar.getDato());
            mostrar = mostrar.getSiguiente();
        }
        
    }

    @Override
    public boolean esVacia() {
        return inicio == null;
    }

    @Override
    public boolean existeElemento(T x) {       
        boolean existe = false;
        NodoDoble aux = inicio;

        while (aux != null && !existe) {
            if (aux.getDato().equals(x)) {
                existe = true;
            }
            aux = aux.getSiguiente();
        }        
        return existe;
    }

    @Override
    public void eliminarElemento(T x) {        
        if (!esVacia()) {
            if (inicio.getDato().equals(x)) {
                this.eliminarInicio();
            } else  if(fin.getDato().equals(x)){
                this.eliminarFinal();
            }else {

                NodoDoble aux = inicio;

                while (aux.getSiguiente() != null && !aux.getSiguiente().getDato().equals(x)) {
                    aux = aux.getSiguiente();
                }
                
                if(aux.getSiguiente()!= null){ //Enbcontre el elemento
                    
                    NodoDoble aBorrar = aux.getSiguiente();
                    aux.setSiguiente(aBorrar.getSiguiente());
                    aBorrar.setSiguiente(null);
                    cantElementos--;                    
                }
            }
        }
    }

    
    public void agregarOrdenado(T x) {
        
        
        if(esVacia() || inicio.getDato().compareTo(x) >= 0){
            this.agregarInicio(x);
        }
        else{
        
            NodoDoble aux = inicio;
            
            while(aux.getSiguiente()!=null && aux.getSiguiente().getDato().compareTo(x) < 0){
                aux = aux.getSiguiente();                
            }
            
            if(aux.getSiguiente() == null){
                this.agregarFinal(x);
            }
            else{
            
                NodoDoble nuevo = new NodoDoble(x);
                nuevo.setSiguiente(aux.getSiguiente());
                aux.setSiguiente(nuevo);
                cantElementos++;
            }        
        }        
    }

    @Override
    public T getElemento(T x) {
        boolean encontro = false;
        NodoDoble aux = inicio;

        while (aux != null && !encontro) {
            if (aux.getDato().equals(x)) {
                encontro = true;
            } else{
                aux = aux.getSiguiente();
            }            
        }
        if(aux != null){
            return (T) aux.getDato(); 
        } 
        return null;               
    }   

    
}

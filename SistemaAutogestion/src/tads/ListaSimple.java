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
public class ListaSimple<T extends Comparable<T>> implements ILista<T> {

    private NodoSimple<T> inicio;
    private int cantElementos;
    private int cantMax;
    private NodoSimple<T> fin;

    public ListaSimple(int tope) {
        cantElementos = 0;
        inicio = null;
        cantMax = tope;
        fin = null;
    }

    public NodoSimple getInicio() {
        return inicio;
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
            NodoSimple borrar = inicio;
            inicio = inicio.getSiguiente();
            borrar.setSiguiente(null);
            cantElementos--;
        } if(esVacia()){
            this.fin = null;
        } 
    }

    @Override
    public void eliminarFinal() {
        if (!esVacia()) {
            if (inicio.getSiguiente() == null) {
                this.vaciar();
            } else {

                NodoSimple actual = inicio;

                while (actual.getSiguiente() != fin) {
                    actual = actual.getSiguiente();

                }
                actual.setSiguiente(null);
                fin = actual;
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
        if (cantElementos < cantMax) {
            NodoSimple nuevo = new NodoSimple(x);
            if (esVacia()) {
                inicio = nuevo;
                fin = nuevo;
            } else {
                fin.setSiguiente(nuevo);
                fin = nuevo;
            }
            cantElementos++;
        }

    }

    @Override
    public void agregarInicio(T n) {
        if (cantElementos < cantMax) {
            NodoSimple nuevo = new NodoSimple(n);
            nuevo.setSiguiente(inicio);
            inicio = nuevo;
            cantElementos++;
        }
    }

    @Override
    public void mostrar() {

        NodoSimple mostrar = inicio;

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
        NodoSimple aux = inicio;
        boolean existe = false;

        while (aux != null && existe == false) {
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
            } else {
                NodoSimple aux = inicio;
                while (aux.getSiguiente() != null && !aux.getSiguiente().getDato().equals(x)) {
                    aux = aux.getSiguiente();
                }
                if (aux.getSiguiente() != null) {
                    NodoSimple aBorrar = aux.getSiguiente();
                    aux.setSiguiente(aBorrar.getSiguiente());
                    aBorrar.setSiguiente(null);
                    cantElementos--;
                    // verificar el puntero final cuando lo tenga
                }
            }
        }
    }     
    
    public void cambiar(T n, T m){
        NodoSimple aux = inicio;
        while(aux != null){
            if(aux.getDato().equals(n)){
                aux.setDato(m);
            }
            aux = aux.getSiguiente();
        }
    }    

    @Override
    public T getElemento(T x) {
        if(!esVacia()){
            if(inicio.getDato().equals(x)){
                return inicio.getDato();
            } else if(fin.getDato().equals(x)){
                return fin.getDato();
            } else{
                NodoSimple aux = inicio.getSiguiente();
                while(aux != null && !aux.getDato().equals(x)){
                    aux = aux.getSiguiente();
                }
                if(aux != null){
                    return (T) aux.getDato();
                }
            }
        }
        return null;
    }

}

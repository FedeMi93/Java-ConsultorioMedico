package tads;


public interface IPila<T> {

    public boolean estaVacia();

    public void apilar(T dato);

    public T desapilar();

    public T top();

    public void vaciar();

    public int cantidadNodos();
    
    public void mostrar();
}

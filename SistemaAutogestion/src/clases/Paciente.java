/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import tads.Pila;

/**
 *
 * @author feder
 */
public class Paciente implements Comparable<Paciente>{
    
    String nombre;
    int cedula;
    String direccion;
    Pila historiaClinica;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public Paciente(int cedula){
        this.cedula = cedula;
    }

    public Paciente(String nombre, int cedula, String direccion) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.direccion = direccion;
        this.historiaClinica = new Pila();
    }

    @Override
    public int compareTo(Paciente o) {
        Integer c = (Integer)this.cedula;
        Integer ac = (Integer) o.getCedula();
        return c.compareTo(ac);
    }
     @Override
    public boolean equals(Object o){
        Paciente p = (Paciente) o;
        return this.cedula == p.getCedula();
    }

    public boolean tieneConsultas() {
        return !historiaClinica.estaVacia();
    }
    
    @Override
    public String toString(){
        return "Nombre: " + this.nombre + " - Cedula: " + this.cedula;
    }

    public void agregarConsulta(Reserva laReserva) {
        historiaClinica.apilar(laReserva);
    }

    public Pila copiarHistoriaClinica() {
        return historiaClinica.copiar();
    }
    
    
    
}

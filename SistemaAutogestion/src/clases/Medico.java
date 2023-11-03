/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.util.Date;
import tads.*;

/**
 *
 * @author feder
 */
public class Medico implements Comparable<Medico>{
    
    String nombre;
    int codMedico;
    int telefono;
    int especialidad;
    static int maxReservas;
    ListaDoble agenda;

    public static int getMaxReservas() {
        return maxReservas;
    }

    public static void setMaxReservas(int maxReservas) {
        Medico.maxReservas = maxReservas;
    }    

    
    public String getNombre() {
        return nombre;
    }   

    public int getCodMedico() {
        return codMedico;
    }   

    public int getTelefono() {
        return telefono;
    }

    public int getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(int especialidad) {
        this.especialidad = especialidad;
    }
    public Medico (int codMedico){
        this.codMedico = codMedico;
    }

    public Medico(String nombre, int codMedico, int telefono, int especialidad) {
        this.nombre = nombre;
        this.codMedico = codMedico;
        this.telefono = telefono;
        this.especialidad = especialidad;
        this.agenda = new ListaDoble();
    }
    
    

    @Override
    public int compareTo(Medico o) {
        return this.nombre.compareTo(o.getNombre());
    }
    @Override
    public boolean equals(Object o){
        Medico m = (Medico) o;
        return this.codMedico == m.getCodMedico();    
    }
    
    public Reserva agregarReserva(Date fecha, int codPaciente){
        Reserva seAgrego = null;
        FechaAgenda nuevaFecha = new FechaAgenda(fecha);
        FechaAgenda fa = (FechaAgenda) agenda.getElemento(nuevaFecha);        
        
        if(!tieneReserva(codPaciente)){
            seAgrego = fa.agregarReserva(codPaciente, codMedico);            
        }
        return seAgrego;        
    }
    
    public boolean validar() {
        return validarNombre() && validarCodigo() && validarEspecialidad();
    }

    private boolean validarNombre() {
        return !this.nombre.isEmpty();
    }

    private boolean validarCodigo() {
        return this.codMedico != 0;
    }

    private boolean validarEspecialidad() {
        return this.especialidad >= 1 && this.especialidad <= 20;
    }

    public boolean tieneConsultasPendientes() {
       return !agenda.esVacia(); //Valido para esta entrega por no tener implementado las reservas.
    }
    @Override
    public String toString(){
        return "Nombre: " + this.nombre + " - Codigo: " + this.codMedico + " - Especialidad: " + this.especialidad;
    }

    public boolean agregarFecha(Date fecha) {
        FechaAgenda fa = new FechaAgenda(fecha);
        if(agenda.existeElemento(fa)) return false;
        agenda.agregarOrdenado(fa);
        return true;
    }

    public boolean existeFecha(Date fecha) {
        FechaAgenda fa = new FechaAgenda(fecha);
        return agenda.existeElemento(fa);
    }

    private boolean tieneReserva(int codPaciente) {
        boolean tiene = false;
        NodoDoble nodoActual = agenda.getInicio();
        while(nodoActual != null && !tiene){
            FechaAgenda fa = (FechaAgenda) nodoActual.getDato();
            if(fa.tieneReservaAbierta(codPaciente, codMedico)){
                tiene = true;
            }
            nodoActual = nodoActual.getSiguiente();
        }   
        return tiene;
    }

    public int cancelarReserva(int ciPaciente) {
        NodoDoble nodoActual = agenda.getInicio();
        int retorno = 3;
        boolean encontro = false;
        boolean cancelo = false;
        while(nodoActual != null && !cancelo){
            FechaAgenda fa = (FechaAgenda)nodoActual.getDato();
            if(fa.tieneReservaPaciente(ciPaciente, codMedico)){
                encontro = true;
                if(fa.cancelarReserva(ciPaciente, codMedico)){
                    cancelo = true;
                }
            }
            nodoActual = nodoActual.getSiguiente();
        }
        if(encontro) retorno = 4;
        if(cancelo) retorno = 0;
        return retorno;            
    }

    public boolean anunciarLlegada(int CIPaciente) {        
        boolean anuncio = false;
        NodoDoble nodoActual = agenda.getInicio();        
        while(nodoActual != null && !anuncio){
            FechaAgenda fa = (FechaAgenda) nodoActual.getDato();
            if(fa.tieneReservaAbierta(CIPaciente, codMedico)){
                fa.anunciarLlegada(CIPaciente, codMedico);
                anuncio = true;                
            }
            nodoActual = nodoActual.getSiguiente();
        }
        return anuncio;
    }
    //Implementar un metodo que reciba por parametro el estado de la consulta para generalizar
    public Reserva terminarConsultaMedicoPaciente(int CIPaciente, String detalleDeConsulta) {
        Reserva laReserva = null;
        boolean termino = false;
        NodoDoble nodoActual = agenda.getInicio();
        while(nodoActual != null && !termino){
            FechaAgenda fa = (FechaAgenda) nodoActual.getDato();
            if(fa.tieneReservaEnEspera(CIPaciente, codMedico)){
                laReserva = fa.terminarConsultaMedicoPaciente(CIPaciente, codMedico, detalleDeConsulta);
                termino = true;               
            }
            nodoActual = nodoActual.getSiguiente();
        }   
        return laReserva;
    }

    public Pila cerrarConsultas(Date fechaConsulta) {
        FechaAgenda fa = new FechaAgenda(fechaConsulta);
        FechaAgenda laFecha = (FechaAgenda)agenda.getElemento(fa);
        Pila retorno = laFecha.cerrarConsultas();
        return retorno;
    }
    
}

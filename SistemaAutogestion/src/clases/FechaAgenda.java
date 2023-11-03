/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.util.Date;
import tads.ListaSimple;
import tads.NodoSimple;
import tads.Pila;


/**
 *
 * @author feder
 */
public class FechaAgenda implements Comparable<FechaAgenda> {
    private Date fecha;
    static int maxReservas;
    private ListaSimple reservas;
    private ListaSimple reservasPendientes; // Es claramente una cola cambiar

    public static void setMaxReservas(int maxReservas) {
        FechaAgenda.maxReservas = maxReservas;
    }
    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public FechaAgenda(Date fecha) {
        this.fecha = fecha;
        this.reservas = new ListaSimple(maxReservas);
        this.reservasPendientes = new ListaSimple(Integer.MAX_VALUE);
    }

    @Override
    public int compareTo(FechaAgenda o) {
        return this.fecha.compareTo(o.getFecha());
    }
    @Override
    public boolean equals(Object o){
        FechaAgenda fa = (FechaAgenda)o;
       return this.fecha.equals(fa.getFecha());   
    }
    
    public Reserva agregarReserva(int codPaciente, int codMedico){
        Reserva r = new Reserva(codPaciente, codMedico);
        r.setEstado(Reserva.Estado.pendiente);
        r.setFecha(fecha);
        if(reservas.getCantidadElementos() < maxReservas){
            r.setNumero(reservas.getCantidadElementos()+1);            
            reservas.agregarFinal((Comparable) r);
        } else {
            reservasPendientes.agregarFinal(r);
        }
        return r;        
    }

    public boolean tieneReservaAbierta(int codPaciente, int codMedico) {
        boolean tiene = false;
        if(!reservas.esVacia()){
            Reserva r = new Reserva(codPaciente,codMedico);
            Reserva laReserva =(Reserva)reservas.getElemento(r);
            if(laReserva != null){
                if(laReserva.getEstado() == Reserva.Estado.en_espera || laReserva.getEstado() == Reserva.Estado.pendiente ){
                tiene = true;
                }
            }
        
        }        
        return tiene;
    }
    public boolean tieneReservaEnEspera(int codPaciente, int codMedico) {
        boolean tiene = false;
        if(!reservas.esVacia()){
            Reserva r = new Reserva(codPaciente,codMedico);
            Reserva laReserva =(Reserva)reservas.getElemento(r);
            if(laReserva != null){
                if(laReserva.getEstado() == Reserva.Estado.en_espera){
                tiene = true;
                }
            }
        
        }        
        return tiene;
    }

    boolean tieneReservaPaciente(int ciPaciente, int codMedico) {
        boolean tiene = false;
        if(!reservas.esVacia()){
            Reserva r = new Reserva(ciPaciente,codMedico);
            Reserva laReserva =(Reserva)reservas.getElemento(r);
            if(laReserva != null){                
                tiene = true;                
            }
        
        }        
        return tiene;
    }

    boolean cancelarReserva(int ciPaciente, int codMedico) {       
        boolean cancelo = false;
        Reserva res = new Reserva(ciPaciente, codMedico);
        NodoSimple nodoActual = reservas.getInicio();
        while(nodoActual != null && !nodoActual.getDato().equals(res)){
            nodoActual = nodoActual.getSiguiente();
        }
        if(nodoActual != null){
            Reserva laReserva = (Reserva)nodoActual.getDato();
            if(laReserva.getEstado() == Reserva.Estado.pendiente){
                if(!reservasPendientes.esVacia()){
                    Reserva aRemplazar = (Reserva)reservasPendientes.getInicio().getDato();
                    int numeroRes = laReserva.getNumero();
                    aRemplazar.setNumero(numeroRes);
                    nodoActual.setDato(aRemplazar);
                    reservasPendientes.eliminarElemento(aRemplazar);// Esto seria mas facil si en vez de ser una lista simple es una cola
                } else{
                    reservas.eliminarElemento(res);
                }
                cancelo = true;
            }
        }        
       return cancelo;     
    }

    void anunciarLlegada(int CIPaciente, int codMedico) {
        Reserva r = new Reserva(CIPaciente, codMedico);
        Reserva laReserva = (Reserva)reservas.getElemento(r);
        laReserva.setEstado(Reserva.Estado.en_espera);
    }

    public Reserva terminarConsultaMedicoPaciente(int CIPaciente, int codMedico, String detalleDeConsulta) {
        Reserva r = new Reserva(CIPaciente, codMedico);
        Reserva laReserva = (Reserva)reservas.getElemento(r);
        laReserva.setEstado(Reserva.Estado.en_espera);
        laReserva.setDescripcion(detalleDeConsulta);
        return laReserva;
    }

    Pila cerrarConsultas() {
        Pila retorno = new Pila();
        NodoSimple aux = reservas.getInicio();
        while(aux != null){
            Reserva r = (Reserva)aux.getDato();
            if(r.getEstado() == Reserva.Estado.pendiente){
                r.setEstado(Reserva.Estado.no_asistió);
                retorno.apilar(r);
            }
            aux = aux.getSiguiente();
        }
        return retorno;
    }
    
    
    
}

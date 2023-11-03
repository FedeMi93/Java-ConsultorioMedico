/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.util.Date;

/**
 *
 * @author feder
 */
public class Reserva implements Comparable<Reserva>{
    int codMedico;
    int cedPaciente;
    Date fecha;
    int numero;
    public enum Estado {
		pendiente, en_espera, cerrada, no_asistió
	};
    private Estado estado;
    private String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }    

    public int getCodMedico() {
        return codMedico;
    }

    public void setCodMedico(int codMedico) {
        this.codMedico = codMedico;
    }

    public int getCedPaciente() {
        return cedPaciente;
    }

    public void setCedPaciente(int cedPaciente) {
        this.cedPaciente = cedPaciente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public Estado getEstado(){
        return this.estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    

    public Reserva(int cedPaciente, int codMedico, int numero) {
        this.codMedico = codMedico;
        this.cedPaciente = cedPaciente;
        this.numero = numero;
    }
    public Reserva(int codPaciente, int codMedico){
        this.codMedico = codMedico;
        this.cedPaciente = codPaciente;
    }

    @Override
   public int compareTo(Reserva o) {    
    return this.fecha.compareTo(o.getFecha());
    }   //Agregarle el comparable por mismo dc y por numero 

    @Override
    public boolean equals(Object obj) {
        Reserva r = (Reserva) obj;      
        if (this.codMedico != r.codMedico){
            return false;
        }
        return this.cedPaciente == r.cedPaciente;
    }

    @Override
    public String toString() {
        return "Reserva{" + "codMedico=" + codMedico + ", cedPaciente=" + cedPaciente + ", fecha=" + fecha + ", numero=" + numero + ", estado=" + estado + ", descripcion=" + descripcion + '}';
    }
    
    

    
    
    
    
}

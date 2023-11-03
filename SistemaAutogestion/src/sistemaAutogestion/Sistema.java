package sistemaAutogestion;


import clases.*;
import java.time.YearMonth;
import java.util.Date;
import tads.*;

public class Sistema implements IObligatorio {
    
    private ListaDoble medicos;
    private ListaDoble pacientes;
    private ListaDoble consultas;
    
    
    /*
    Pre: se resive un entero entre 1 y 15 para indicar el maximo de pacientes por medico
    pos: se inicializan las listas si el numero es correcto y se asigna el maximo de pacientes
    */
    @Override
    public Retorno crearSistemaDeAutogestion(int maxPacientesporMedico) {
        if(maxPacientesporMedico > 0 && maxPacientesporMedico <= 15){
            medicos = new ListaDoble();
            FechaAgenda.setMaxReservas(maxPacientesporMedico);
            pacientes = new ListaDoble(); 
            consultas = new ListaDoble();
            return new Retorno(Retorno.Resultado.OK);        
        }
        return new Retorno(Retorno.Resultado.ERROR_1);
    }
    

    /*
    Pre: Se reciben los datos de un medico (nombre, codigo, telefono, especialidad)
        la especialidad debe ser un Int entre 1 y 20
    Pos: Si no existe un medico con los mismos datos, se suma a la lista de medicos
    */
    @Override
    public Retorno registrarMedico(String nombre, int codMedico, int tel, int especialidad) {
       Medico m = new Medico(nombre, codMedico, tel, especialidad);
       if(medicos.existeElemento(m)){
           return new Retorno(Retorno.Resultado.ERROR_1);
       }
       if(!m.validar()){
           return new Retorno(Retorno.Resultado.ERROR_2);
       }
       medicos.agregarOrdenado(m);
       return new Retorno(Retorno.Resultado.OK);       
    }

    /*
    Pre: recibe un codigo de medico valor int positivo
    
    Pos: Si encuentra un medico con ese codigo, y este no tiene consultas pendientes, lo elimina
    */
    @Override
    public Retorno eliminarMedico(int codMedico) {
        Medico m = new Medico(codMedico);
        Medico elMedico = (Medico) medicos.getElemento(m);
        if(elMedico == null){
            return new Retorno(Retorno.Resultado.ERROR_1);
        }
        if(elMedico.tieneConsultasPendientes()){
            return new Retorno(Retorno.Resultado.ERROR_2);
        }
        medicos.eliminarElemento(elMedico);
        return new Retorno(Retorno.Resultado.OK);        
    }

    /*
    Pre: recibe los datos de un Paciente, String nombre, int CI, String direccion     
    Pos: Si no encuentra el Paciente en la lista de pacientes, lo agrega. 
    */
    @Override
    public Retorno agregarPaciente(String nombre, int CI, String direccion) {
         Paciente p = new Paciente(nombre, CI, direccion);
         if(pacientes.existeElemento(p)){
             return new Retorno(Retorno.Resultado.ERROR_1);
         }
         pacientes.agregarFinal(p);
         return new Retorno(Retorno.Resultado.OK);
    }
    /*
    Pre: recibe una cedula de un cliente
    Pos: si existe un Paciente con esa cedula, y no tiene consultas agendadas lo elimina
    */

    @Override
    public Retorno eliminarPaciente(int CI) {
        Paciente p = new Paciente(CI);
        Paciente elPaciente =(Paciente)pacientes.getElemento(p);
        if(elPaciente == null){
            return new Retorno(Retorno.Resultado.ERROR_1);
        }
        if(elPaciente.tieneConsultas()){
            return new Retorno(Retorno.Resultado.ERROR_2);
        }
        pacientes.eliminarElemento(elPaciente);
        return new Retorno(Retorno.Resultado.OK);
    }
    /*
    Pre: recibe un codigo de medico(int) y una fecha mayor o igual a la fecha de hoy
    Pos: se registra una fecha de consultas para un medico. En caso de que no exista el medico devuelve error_1,
    en caso de que ya exista una fecha devuelve error_2. 
     */
    public Retorno registrarDiaDeConsulta(int codMedico,Date fecha){
         Medico m = new Medico(codMedico);
        Medico elMedico = (Medico) medicos.getElemento(m);
        if(elMedico == null){
            return new Retorno(Retorno.Resultado.ERROR_1);
        }
        boolean agrego = elMedico.agregarFecha(fecha);
        if(agrego){
            return new Retorno(Retorno.Resultado.OK);
        }
        return new Retorno(Retorno.Resultado.ERROR_2);
    }
    
    /*
    Pre: ser recibe un codigo de medico existente, una ci de paciente registrado, y una fecha 
    Pos: se registra correctamente la reserva en caso de que el paciente no tenga otra consulta
    pendiente con ese medico. En caso de que el medico ya tenga todas las consultas para esa fecha, 
    la consulta queda en una lista de espera.
    */

    @Override
    public Retorno reservaConsulta(int codMedico, int ciPaciente, Date fecha) {
        Paciente p = new Paciente(ciPaciente);
        Paciente elPaciente = (Paciente) pacientes.getElemento(p);
        if(elPaciente == null){
            return new Retorno(Retorno.Resultado.ERROR_1);
        }       
        Medico m = new Medico(codMedico);
        Medico elMedico = (Medico) medicos.getElemento(m);
        if(elMedico == null){
            return new Retorno(Retorno.Resultado.ERROR_2);
        }
        if(!elMedico.existeFecha(fecha)){
            return new Retorno(Retorno.Resultado.ERROR_4);
        }
        
        Reserva laReserva = elMedico.agregarReserva(fecha, ciPaciente);
        if(laReserva != null){
            consultas.agregarOrdenado(laReserva);
            return new Retorno(Retorno.Resultado.OK);
        }else {
            return new Retorno(Retorno.Resultado.ERROR_3);
        }  
    }
    
    /*Pre: Recibe un cod de Medico y una cedula de un paciente registrado
    Pos: elimina la consulta que este en estado pendiente
    */

    @Override
    public Retorno cancelarReserva(int codMedico, int ciPaciente) {//Borara la reserva de la lista de reservas
        Paciente p = new Paciente(ciPaciente);
        Paciente elPaciente = (Paciente)pacientes.getElemento(p);
        if(elPaciente == null){
            return new Retorno(Retorno.Resultado.ERROR_1);
        }
        
        Medico m = new Medico(codMedico);
        Medico elMedico =(Medico)medicos.getElemento(m);
        if(elMedico == null){
            return new Retorno(Retorno.Resultado.ERROR_2);
        }
        int estado = elMedico.cancelarReserva(ciPaciente);
        if(estado == 3){
            return new Retorno(Retorno.Resultado.ERROR_3);
        } else if(estado == 4){
            return new Retorno(Retorno.Resultado.ERROR_4);
        }
        Reserva r = new Reserva(ciPaciente, codMedico);
        consultas.eliminarElemento(r);
        return new Retorno(Retorno.Resultado.OK);
    }
    
    /*
    Pre: Recibe un codigo de medico registrado y una ci de un paciente registrado
    Pos: Imprime nombre del medico y el numero de la consulta en caso de que el paciente 
    tenga una consulta con ese medico en ese día.
    */
    @Override
    public Retorno anunciaLlegada(int codMedico, int CIPaciente) {
       Paciente p = new Paciente(CIPaciente);
       Paciente elPaciente = (Paciente)pacientes.getElemento(p);
       if(elPaciente == null){
            return new Retorno(Retorno.Resultado.ERROR_1);
       } 
        
       Medico m = new Medico(codMedico);
       Medico elMedico = (Medico)medicos.getElemento(m);
       if(elMedico.anunciarLlegada(CIPaciente)){
           System.out.println(elMedico.getNombre());
           return new Retorno(Retorno.Resultado.OK);
       } else{
           return new Retorno(Retorno.Resultado.ERROR_2);
       }       
    }
    
    /*
    Pre: Recibe la ci de un paciente registrado, el codigo de un medico registrado, un texto con las especificaciones del medico
    Pos: en caso de que ese paciente tenga una consulta en "en espera" con ese medico en la esa fecha, la misma se cierra, y se le suma al historial del paciente.
    */
    @Override
    public Retorno terminarConsultaMedicoPaciente(int CIPaciente, int codMedico, String detalleDeConsulta) {
       Paciente p = new Paciente(CIPaciente);
       Paciente elPaciente = (Paciente)pacientes.getElemento(p);
       if(elPaciente == null){
            return new Retorno(Retorno.Resultado.ERROR_1);
       } 
       Medico m = new Medico(codMedico);
       Medico elMedico = (Medico)medicos.getElemento(m);
       Reserva laReserva = elMedico.terminarConsultaMedicoPaciente(CIPaciente, detalleDeConsulta);
       if(laReserva != null){
           elPaciente.agregarConsulta(laReserva);           
           return new Retorno(Retorno.Resultado.OK);
       }      
       return new Retorno(Retorno.Resultado.ERROR_2);
    }
    
    /*
    Pre: Recibe un codigo de un medico registrado, y una fecha
    Pos: Se cierran todas las consultas de ese medico, se les carga "no asistio" 
    a las consultas que sigan en estado "pendiente", y se guardan en el historial de los pacientes.
    */
    @Override
    public Retorno cerrarConsulta(int codMédico, Date fechaConsulta) {
        Medico m = new Medico(codMédico);
        Medico elMedico =(Medico) medicos.getElemento(m);
        if(elMedico == null){
            return new Retorno(Retorno.Resultado.ERROR_1);
        }
        
        if(elMedico.existeFecha(fechaConsulta)){
            Pila reservasEnLaFecha = elMedico.cerrarConsultas(fechaConsulta);
            
            while(!reservasEnLaFecha.estaVacia()){
                Reserva r = (Reserva)reservasEnLaFecha.desapilar();
                Paciente p = new Paciente(r.getCedPaciente());
                Paciente elPaciente = (Paciente)pacientes.getElemento(p);
                elPaciente.agregarConsulta(r);                
            }
            return new Retorno(Retorno.Resultado.OK);
        } else{
            return new Retorno(Retorno.Resultado.ERROR_2);
        }        
    }
    
    /*
    Pre: La lista de medicos no esta vacia
    Pos: Se imprimen los datos de todos los medicos ordenados alfabeticamente
    */
    @Override
    public Retorno listarMédicos() {
        medicos.mostrar();
        return new Retorno(Retorno.Resultado.OK);       
    }
    /*
    Pre: La lista de pacientes no esta vacia
    Pos: Imprime los datos de los pacientes ordenados por registro
    */
    @Override
    public Retorno listarPacientes() {        
        pacientes.mostrar();//Evaluar el To String del metodo, que hay que mostrar
        return new Retorno(Retorno.Resultado.OK);        
    }
    /*
    Pre: recibe un codigo de medico que este registrado
    Pos: imprime todas las consultas de ese medico, agrupadas por fecha
    */
    @Override
    public Retorno listarConsultas(int codMédico) {
        Medico m = new Medico(codMédico);
        Medico elMedico = (Medico)medicos.getElemento(m);
        if(elMedico == null){
            return new Retorno(Retorno.Resultado.ERROR_1);
        }
        NodoDoble aux = consultas.getInicio();
        listarConsultas(codMédico, aux);
        return new Retorno(Retorno.Resultado.OK);
    }
    private void listarConsultas(int codMedico, NodoDoble aux){
        if(aux != null){            
            Reserva r = (Reserva)aux.getDato();
            if(r.getCodMedico() == codMedico){
                System.out.println(r);//Ver el To String
            }
            aux = aux.getSiguiente();
            listarConsultas(codMedico, aux);
        }
    }
    /*
    Pre: reciibe un codigo de medico registrado, y una fecha
    Pos: imprime los pacientes que tengan consulta con ese medico en esa fecha, y que esten en estado "en espera"
    */
    @Override
    public Retorno listarPacientesEnEspera(int codMédico, Date fecha) {
        Medico m = new Medico(codMédico);
        Medico elMedico = (Medico)medicos.getElemento(m);
        if(elMedico.existeFecha(fecha)){
           NodoDoble aux = consultas.getInicio();
           listarPacientesEnEspera(codMédico, fecha, aux); 
           return new Retorno(Retorno.Resultado.OK);
        }
        return new Retorno(Retorno.Resultado.ERROR_1);
    }
    private void listarPacientesEnEspera(int codMedico, Date fecha, NodoDoble aux){
        if(aux != null){
            Reserva r = (Reserva)aux.getDato();
            if(r.getCodMedico() == codMedico && r.getFecha().equals(fecha) && r.getEstado() == Reserva.Estado.en_espera){
                Paciente p = new Paciente(r.getCedPaciente());
                Paciente elPaciente = (Paciente)pacientes.getElemento(p);
                System.out.println( "N° reserva: " + r.getNumero() + " Paciente: " + elPaciente.getNombre() + " CI: " + r.getCedPaciente());
            }
            aux = aux.getSiguiente();
            listarPacientesEnEspera(codMedico, fecha, aux);
        }
    }
    
    /*
    Pre:Recibe la ci de un paciente registrado 
    Pos: Imprimi todas las consultas del paciente que esten en "pendientes"
    */
    @Override
    public Retorno consultasPendientesPaciente(int CIPaciente) {
        Paciente p = new Paciente(CIPaciente);
        Paciente elPaciente = (Paciente)pacientes.getElemento(p);
        if(elPaciente == null){
            return new Retorno(Retorno.Resultado.ERROR_1);
        }
        NodoDoble aux = consultas.getInicio();
        consultasPendientesPacientes(CIPaciente, aux);
        return new Retorno(Retorno.Resultado.OK);        
    }
    //Preguntar si el paciente tedria que guardar las consultas abiertas
    private void consultasPendientesPacientes(int CIPaciente, NodoDoble aux){
         if(aux != null){
            Reserva r = (Reserva)aux.getDato();
            if(r.getCedPaciente() == CIPaciente && r.getEstado() == Reserva.Estado.pendiente){
                System.out.println( r);
            }
            aux = aux.getSiguiente();
            consultasPendientesPacientes(CIPaciente, aux);
        }
    }
    
    /*
    Pre:Recibe la ci de un paciente que este registrado
    Pos: Imprimi todas las consultas del paciente que esten cerradas
    */
    @Override
    public Retorno historiaClínicaPaciente(int ci) {
        Paciente p = new Paciente(ci);
        Paciente elPaciente = (Paciente)pacientes.getElemento(p);
        if(elPaciente == null) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        }
        Pila historiaClinica = elPaciente.copiarHistoriaClinica();
        historiaClinicaPaciente(historiaClinica);
        
        return new Retorno(Retorno.Resultado.OK);
    }
    //Se entiende que si las reservas se cierran en el dia de la consulta, en el tope de la pila va quedando siempre la mas actual.
    private void historiaClinicaPaciente(Pila p){
        if(!p.estaVacia()){
            Reserva r = (Reserva)p.desapilar();
            System.out.println(r);
            historiaClinicaPaciente(p);
        }
    }
    /*
    Pre: Recibe un valor numerico, mes, entre 1 y 12, y un valor numerico de año entre 2020 y 2023 
    Pos: Imprime dado el dia y la especialidad la cantidad de pacientes que fueron atendidos
    */
    @Override
    public Retorno reporteDePacientesXFechaYEspecialidad(int mes, int año) {
        //Especialidades del 1 al 20
        //dias del mes hasta 31
        if(mes < 0 || mes > 12 && año < 2020 || año > 2023){
            return new Retorno(Retorno.Resultado.ERROR_1);            
        }
        // Codigo preguntado a Chat GPT
        YearMonth yearMonth = YearMonth.of(año, mes);
        int diasEnElMes = yearMonth.lengthOfMonth();
        //
        int[][] reporte = new int[diasEnElMes + 1][21];
        
        for(int j = 1; j <= diasEnElMes; j++){
            reporte[j][0] = j;
        }
        for(int i = 1; i < 21; i++){
            reporte[0][i] = i;
        }
        mostrarMatriz(reporte);
        
        
        return new Retorno(Retorno.Resultado.OK);
    }
    private void mostrarMatriz(int[][] mat){
        for(int i = 0; i< mat.length; i++){
            for(int j = 0; j < mat[i].length; j++){
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
    }
   

    

}

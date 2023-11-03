package inicio;

import java.util.Date;
import sistemaAutogestion.*;

public class Main {

    public static void main(String[] args) {
        
        Prueba p = new Prueba();
        Sistema s = new Sistema();
        
        
        p_ejecutarPruebasEnOrden(p,s);
        
    }
    
    public static void p1_creacionSistema(Prueba p, Sistema s){
        p.ver(s.crearSistemaDeAutogestion(1).resultado, Retorno.Resultado.OK, "Se crea correctamente el sistema con capacidad para 1 pacientes");
        p.ver(s.crearSistemaDeAutogestion(15).resultado, Retorno.Resultado.OK, "Se crea correctamente el sistema con capacidad para 15 pacientes");
        p.ver(s.crearSistemaDeAutogestion(0).resultado, Retorno.Resultado.ERROR_1, "No se crea el sitema, la capacidad debe ser mayor a 0");
        p.ver(s.crearSistemaDeAutogestion(16).resultado, Retorno.Resultado.ERROR_1, "No se crea el sitema, la capacidad debe ser menor a 16");
    }
    public static void p2_registrarMedico(Prueba p, Sistema s){
        p.ver(s.registrarMedico("Pedro", 123, 12345678, 1).resultado, Retorno.Resultado.OK, "Se registra correctamente un medico");
        p.ver(s.registrarMedico("Marta", 124, 456123789, 20).resultado, Retorno.Resultado.OK, "Se registra correctamente un medico");
        p.ver(s.registrarMedico("Andres", 123, 456789123, 5).resultado, Retorno.Resultado.ERROR_1, "No se registra el medico, ya existe uno cn el mismo codigo.");
        p.ver(s.registrarMedico("Juan", 321, 789456123, 0).resultado, Retorno.Resultado.ERROR_2, "No se registra el medico, la especialidad debe ser mayor a 0");
        p.ver(s.registrarMedico("Maria", 321, 789456123, 21).resultado, Retorno.Resultado.ERROR_2, "No se registra el medico, la especialidad debe ser menor a 21");
    }
    public static void p3_eliminarMedico(Prueba p, Sistema s){
        p.ver(s.eliminarMedico(123).resultado, Retorno.Resultado.OK, "El medico fue eliminado correctamente.");
        p.ver(s.eliminarMedico(123).resultado, Retorno.Resultado.ERROR_1, "No existe un medico con ese codigo.");
    }
    public static void p4_registrarPaciente(Prueba p, Sistema s){
        p.ver(s.agregarPaciente("Homero", 123456789, "Calle A 123").resultado, Retorno.Resultado.OK, "Se registra correctamente el paciente");
        p.ver(s.agregarPaciente("Homero", 123456987, "Calle A 123").resultado, Retorno.Resultado.OK, "Se registra correctamente el paciente");//Ingreso un segundo paciente para tener al menos uno para imprimir
        p.ver(s.agregarPaciente("Bart", 123456789, "Calle B 123").resultado, Retorno.Resultado.ERROR_1, "No se registra el paciente, ya existe un paciente con esa cedula");
        p.ver(s.agregarPaciente("Bart", 456456789, "Calle B 123").resultado, Retorno.Resultado.OK, "Se registra correctamente el paciente");
        p.ver(s.agregarPaciente("Lisa", 456123789, "Calle B 123").resultado, Retorno.Resultado.OK, "Se registra correctamente el paciente");
        p.ver(s.agregarPaciente("Magie", 456456123, "Calle B 123").resultado, Retorno.Resultado.OK, "Se registra correctamente el paciente");
    }
    public static void p5_eliminarPaciente(Prueba p, Sistema s){
        p.ver(s.eliminarPaciente(123456789).resultado, Retorno.Resultado.OK, "Paciente eliminado correctamente");
        p.ver(s.eliminarPaciente(123456789).resultado, Retorno.Resultado.ERROR_1, "No existe un paciente con esa cedula");
    }
    public static void p6_mostrarMedicos(Prueba p, Sistema s){
        p.ver(s.listarMédicos().resultado, Retorno.Resultado.OK, "Se listan correctamente los medicos ordenados alfabeticamente");
    }
    public static void p7_mostrarPacientes(Prueba p, Sistema s){
        p.ver(s.listarPacientes().resultado, Retorno.Resultado.OK, "Se listan correctamente los pacientes segun el orden de registro");
    }
    public static void p8_registrarDiaConsulta(Prueba p, Sistema s){
        p.ver(s.registrarDiaDeConsulta(123, new Date(2023,11,12)).resultado, Retorno.Resultado.OK, "Se agrega correctamente una fecha de consultas a un medico.");
        p.ver(s.registrarDiaDeConsulta(321, new Date(2023, 11, 12)).resultado, Retorno.Resultado.ERROR_1, "No existe un medico con el codigo ingresado");
        p.ver(s.registrarDiaDeConsulta(123, new Date(2023, 11,12)).resultado, Retorno.Resultado.ERROR_2, "Ya esta registrada la fecha ingresada para ese medico.");
        p.ver(s.registrarDiaDeConsulta(123, new Date(2023, 11, 10)).resultado, Retorno.Resultado.OK, "Se agrega correctamente una fecha de consultas a un medico.");
        p.ver(s.registrarDiaDeConsulta(123, new Date(2023, 11, 20)).resultado, Retorno.Resultado.OK, "Se agrega correctamente una fecha de consultas a un medico.");
        p.ver(s.registrarDiaDeConsulta(123, new Date(2023, 11, 23)).resultado, Retorno.Resultado.OK, "Se agrega correctamente una fecha de consultas a un medico.");
    }
    public static void p9_reservaConsulta(Prueba p, Sistema s){
        p.ver(s.reservaConsulta(123, 123456789,  new Date(2023, 11, 12)).resultado, Retorno.Resultado.OK, "Se registra correctamente en la agenda del medico, la consulta");
        p.ver(s.reservaConsulta(123, 123456781,  new Date(2023, 11, 12)).resultado, Retorno.Resultado.ERROR_1, "No existe un paciente con esa cedula");
        p.ver(s.reservaConsulta(321, 123456987,  new Date(2023, 11, 12)).resultado, Retorno.Resultado.ERROR_2, "No existe un medico con ese codigo");
        p.ver(s.reservaConsulta(123, 123456789,  new Date(2023, 11, 12)).resultado, Retorno.Resultado.ERROR_3, "Ya existe una consulta del paciente con el medico ingresado");//agregar  mas fechas y probar con otra fecha
        p.ver(s.reservaConsulta(123, 123456789,  new Date(2023, 11, 13)).resultado, Retorno.Resultado.ERROR_4, "El medico no tiene consultas en la fecha seleccionada");
        p.ver(s.reservaConsulta(123, 456456789,  new Date(2023, 11, 10)).resultado, Retorno.Resultado.OK, "Se registra correctamente en la agenda del medico, la consulta");
        p.ver(s.reservaConsulta(123, 456123789,  new Date(2023, 11, 20)).resultado, Retorno.Resultado.OK, "Se registra correctamente en la agenda del medico, la consulta");
                            
    }
    public static void p10_cancelarReserva(Prueba p, Sistema s){
        p.ver(s.cancelarReserva(123,123456789 ).resultado, Retorno.Resultado.OK, "Se cancelo correctamente la consulta");
        p.ver(s.cancelarReserva(123,123456781 ).resultado, Retorno.Resultado.ERROR_1, "No existe un paciente con esa cedula");
        p.ver(s.cancelarReserva(321,123456789 ).resultado, Retorno.Resultado.ERROR_2, "No existe un medico con ese codigo");
        p.ver(s.cancelarReserva(123,456456123 ).resultado, Retorno.Resultado.ERROR_3, "El paciente no tiene reservas con ese medico");
//falta caso de reservas cerradas
    }
    public static void p11_anunciarLlegada(Prueba p, Sistema s){
        p.ver(s.anunciaLlegada(123, 456123789).resultado, Retorno.Resultado.OK, "Se anuncio correctamente la llegada del paciente");
        p.ver(s.anunciaLlegada(123, 123123789).resultado, Retorno.Resultado.ERROR_1, "No existe un paciente con esa ci");
        p.ver(s.anunciaLlegada(123, 456456123).resultado, Retorno.Resultado.ERROR_2, "El paciente no tiene consultas con el medico");
    }
    public static void p12_terminarConsultaMedicoPaciente(Prueba p, Sistema s){
        p.ver(s.terminarConsultaMedicoPaciente(456123789, 123, "Se le recomienda al paciente cuidarse con las comidas. Se deriba a Nutricionista").resultado, Retorno.Resultado.OK, "Se cierra correctamente la consulta");
        p.ver(s.terminarConsultaMedicoPaciente(456123111, 123, "Se le recomienda al paciente cuidarse con las comidas. Se deriba a Nutricionista").resultado, Retorno.Resultado.ERROR_1, "No existe un medico con ese codigo");
        p.ver(s.terminarConsultaMedicoPaciente(456456789, 123, "Se le recomienda al paciente cuidarse con las comidas. Se deriba a Nutricionista").resultado, Retorno.Resultado.ERROR_2, "El paciente no tiene consultas en espera");        
        
    }
    
    public static void p13_cerrarConsulta(Prueba p, Sistema s){
        p.ver(s.cerrarConsulta(123, new Date(2023, 11, 10)).resultado, Retorno.Resultado.OK, "Se cerraron correctamente las consultas del medico");
        p.ver(s.cerrarConsulta(111, new Date(2023, 11, 10)).resultado, Retorno.Resultado.ERROR_1, "No existe un medico con ese codigo");
        p.ver(s.cerrarConsulta(123, new Date(2023, 11, 13)).resultado, Retorno.Resultado.ERROR_2, "El medico no tiene consultas para cerrar ese día");     
        
    }
    
    public static void p14_mostrarCOnsultasMedico(Prueba p, Sistema s){
        p.ver(s.listarConsultas(123).resultado, Retorno.Resultado.OK, "Se listan las consultas del medico");
        p.ver(s.listarConsultas(111).resultado, Retorno.Resultado.ERROR_1, "No existe un medico con ese codigo");
    }
    
    public static void p15_listarPacientesEnEspera(Prueba p, Sistema s){
        p.ver(s.listarPacientesEnEspera(123, new Date(2023, 11, 20)).resultado, Retorno.Resultado.OK, "Se listo correctamente a los pacientes en espera.");
        p.ver(s.listarPacientesEnEspera(123, new Date(2023, 11, 2)).resultado, Retorno.Resultado.ERROR_1, "El medico seleccionado no tiene consultas en la fecha ingresada");
    }
    
    public static void p_reporteDePacientesXFechaYEspecialidad(Prueba p, Sistema s){
        p.ver(s.reporteDePacientesXFechaYEspecialidad(07, 2023).resultado, Retorno.Resultado.OK, "Se muestra correctamente la matriz");
    }
    
    public static void p_ejecutarPruebasEnOrden(Prueba p, Sistema s){
        p.inicializarResultadosPrueba();
        p1_creacionSistema(p,s);
        p2_registrarMedico(p,s);
        //p3_eliminarMedico(p,s);
        p4_registrarPaciente(p,s);
        //p5_eliminarPaciente(p,s);
        //p6_mostrarMedicos(p,s);
        //p7_mostrarPacientes(p,s);
        p8_registrarDiaConsulta(p,s);
        p9_reservaConsulta(p,s);
        p10_cancelarReserva(p,s);
        p11_anunciarLlegada(p,s);
        //p12_terminarConsultaMedicoPaciente(p,s);
        //p13_cerrarConsulta(p,s);
        p14_mostrarCOnsultasMedico(p,s);
        p15_listarPacientesEnEspera(p,s);
        p_reporteDePacientesXFechaYEspecialidad(p,s);
        p.imprimirResultadosPrueba();
        
    }
}

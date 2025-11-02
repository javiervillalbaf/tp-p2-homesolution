package entidades;

import java.util.HashMap;
import java.util.List;

public class HomeSolution implements IHomeSolution {
	HashMap<Integer,Empleado> empleados;
	HashMap<Integer,Proyecto> proyectos;

	@Override
    public void registrarEmpleado(String nombre, double valor) {
    	if(nombre == null || valor <= 0) {
    		throw new IllegalArgumentException("Ingrese un nombre valido y valor mayor a 0");
    	}
    	
    	Empleado empleado = new Empleado(nombre, valor);
    	empleados.put(empleado.legajo, empleado);
    }
	
	@Override
    public void registrarEmpleado(String nombre, double valor, String categoria) {
		if(nombre == null || valor <= 0 || categoria == null) {
    		throw new IllegalArgumentException("Ingrese un nombre valido y valor mayor a 0");
    	}
    	
    	EmpleadoDePlanta empleado = new EmpleadoDePlanta(nombre, valor, categoria);
    	empleados.put(empleado.legajo, empleado);
    }
	
	@Override
    public void registrarProyecto(String[] titulos, String[] descripcion, double[] dias,
                                  String domicilio, String[] cliente, String inicio, String finEstimado) {
    	if(titulos == null || descripcion == null || dias == null || domicilio == null || cliente == null || inicio == null || finEstimado == null) {
    		throw new IllegalArgumentException("Ingrese valores validos");
    	}
    	
    	Proyecto proyecto = new Proyecto(cliente, domicilio, inicio, finEstimado);
    	for (int i = 0; i < titulos.length; i++) {
			agregarTareaEnProyecto(proyecto.codigoProyecto, titulos[i], descripcion[i], dias[i]);
		}
    	
    }

	@Override
    public void asignarResponsableEnTarea(Integer numero, String titulo) {
    	if(numero <= 0 || titulo == null) {
    		throw new IllegalArgumentException("Ingrese valores validos");
    	}
    	
    	if(proyectos.get(numero).tareas.get(titulo).empleadoAsignado != 0) {
    		throw new IllegalArgumentException("Ya hay un empleado asignado");
    	}
    	
    	if(proyectos.get(numero).estado == "Finalizado") {
    		throw new IllegalArgumentException("El proyecto ya esta finalizado");
    	}
    	
    	int empleadoDisponible = 0;
    	
    	for (Empleado empleado : empleados.values()) {
			if(empleado.estado == false) {
				empleadoDisponible = empleado.legajo;
				proyectos.get(numero).tareas.get(titulo).empleadoAsignado = empleadoDisponible;
		    	empleados.get(empleadoDisponible).tituloTarea = titulo;
				break;
			}
		}
    	
    	if(empleadoDisponible == 0) {
    		throw new IllegalArgumentException("No hay empleados disponibles");
    	}
    }

	@Override
    public void asignarResponsableMenosRetraso(Integer numero, String titulo) {
		if(numero <= 0 || titulo == null) {
    		throw new IllegalArgumentException("Ingrese valores validos");
    	}
    	
    	if(proyectos.get(numero).tareas.get(titulo).empleadoAsignado != 0) {
    		throw new IllegalArgumentException("Ya hay un empleado asignado");
    	}
    	
    	if(proyectos.get(numero).estado == "Finalizado") {
    		throw new IllegalArgumentException("El proyecto ya esta finalizado");
    	}
    	
    	int empleadoDisponible = 0;
    	int menosRetraso = 99;
    	
    	for (Empleado empleado : empleados.values()) {
			if(empleado.estado == false && menosRetraso < empleado.retrasos) {
				menosRetraso = empleado.retrasos;
				empleadoDisponible = empleado.legajo;
			}
		}
    	
    	if(empleadoDisponible == 0) {
    		throw new IllegalArgumentException("No hay empleados disponibles");
    	}
    	
    	proyectos.get(numero).tareas.get(titulo).empleadoAsignado = empleadoDisponible;
    	empleados.get(empleadoDisponible).tituloTarea = titulo;
    }
	
	@Override		 
    public void registrarRetrasoEnTarea(Integer numero, String titulo, double cantidadDias){
		if(numero <= 0 || titulo == null || cantidadDias <= 0) {
    		throw new IllegalArgumentException("Ingrese valores validos");
    	}
		
		proyectos.get(numero).tareas.get(titulo).dias += cantidadDias;
		int empleadoRetrasado = proyectos.get(numero).tareas.get(titulo).empleadoAsignado;
		empleados.get(empleadoRetrasado).retrasos += 1;
    }
	
	@Override
    public void agregarTareaEnProyecto(Integer numero, String titulo, String descripcion, double dias){
		if(numero <= 0 || titulo == null || descripcion == null || dias <= 0) {
			throw new IllegalArgumentException("Ingrese valores validos");
		}
		
		Tarea tarea = new Tarea(numero, titulo, descripcion, dias);
		proyectos.get(numero).tareas.put(titulo, tarea);
    }
	
	@Override
    public void finalizarTarea(Integer numero, String titulo){
		
    }

	@Override
    public void finalizarProyecto(Integer numero, String fin){

    }
	
	@Override
    public void reasignarEmpleadoEnProyecto(Integer numero, Integer legajo, String titulo){

    }
	
	@Override
    public void reasignarEmpleadoConMenosRetraso(Integer numero, String titulo){

    }
	
	@Override
    public double costoProyecto(Integer numero){

    }
	
	@Override
    public List<Tupla<Integer, String>> proyectosFinalizados(){

	}

	@Override
    public List<Tupla<Integer, String>> proyectosPendientes(){
    	
    }

	@Override
    public List<Tupla<Integer, String>> proyectosActivos(){

	}

	@Override
    public Object[] empleadosNoAsignados(){

	}

	@Override
    public boolean estaFinalizado(Integer numero){

	}

	@Override
    public int consultarCantidadRetrasosEmpleado(Integer legajo) {

	}

	@Override
    public List<Tupla<Integer, String>> empleadosAsignadosAProyecto(Integer numero){

	}

	@Override
    public Object[] tareasProyectoNoAsignadas(Integer numero){

	}
    
	@Override
    public Object[] tareasDeUnProyecto(Integer numero){

	}

	@Override
    public String consultarDomicilioProyecto(Integer numero){

	}

	@Override
    public boolean tieneRestrasos(Integer legajo){

	}
	
	@Override
    public List<Tupla<Integer, String>> empleados(){

	}

	@Override
    public String consultarProyecto(Integer numero){

	}
}
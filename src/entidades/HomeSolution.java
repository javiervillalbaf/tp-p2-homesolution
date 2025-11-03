package entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class HomeSolution implements IHomeSolution {
	HashMap<Integer,Empleado> empleados;
	HashMap<Integer,Proyecto> proyectos;

	
	public Proyecto getProyecto(int numero) {
		return proyectos.get(numero);
	}
	public Tarea getTarea(int numero, String titulo) {
		return getProyecto(numero).getTarea(titulo);
	}
	public Empleado getEmpleado(int numero) {
		return empleados.get(numero);
	}
	
	public void validadorProyectos() {
		if (proyectos == null) {
			throw new IllegalArgumentException("No se encuentran proyectos");
		}
	}
	
	public HomeSolution() {
		this.empleados = new HashMap<>();
		this.proyectos = new HashMap<>();
	}
	
	@Override
    public void registrarEmpleado(String nombre, double valor) {
    	if(nombre == null || valor <= 0) {
    		throw new IllegalArgumentException("Ingrese un nombre valido y valor mayor a 0");
    	}
    	Empleado empleado = new Empleado(nombre, valor);
    	empleados.put(empleado.getLegajo(), empleado);
    }
	
	@Override
    public void registrarEmpleado(String nombre, double valor, String categoria) {
		if(nombre == null || valor <= 0 || categoria == null) {
    		throw new IllegalArgumentException("Ingrese un nombre valido y valor mayor a 0");
    	}
    	
    	EmpleadoDePlanta empleado = new EmpleadoDePlanta(nombre, valor, categoria);
    	empleados.put(empleado.getLegajo(), empleado);
    }
	
	@Override
    public void registrarProyecto(String[] titulos, String[] descripcion, double[] dias,
                                  String domicilio, String[] cliente, String inicio, String finEstimado) {
    	if(titulos == null || descripcion == null || dias == null || domicilio == null || cliente == null || inicio == null || finEstimado == null) {
    		throw new IllegalArgumentException("Ingrese valores validos");
    	}
    	 	
    	Proyecto proyecto = new Proyecto(cliente, domicilio, inicio, finEstimado);
    	proyectos.put(proyecto.codigoProyecto, proyecto);
    	
    	for (int i = 0; i < titulos.length; i++) {
			agregarTareaEnProyecto(proyecto.codigoProyecto, titulos[i], descripcion[i], dias[i]);
		}	
    }

	@Override
    public void asignarResponsableEnTarea(Integer numero, String titulo) {
    	if(numero <= 0 || titulo == null) {
    		throw new IllegalArgumentException("Ingrese valores validos");
    	}
    	
    	if(getTarea(numero, titulo).getEmpleadoAsignado() != 0) {
    		throw new IllegalArgumentException("Ya hay un empleado asignado");
    	}
    	
    	if(getProyecto(numero).estaFinalizado()) {
    		throw new IllegalArgumentException("El proyecto ya esta finalizado");
    	}
    	
    	int empleadoDisponible = 0;
    	
    	for (Empleado empleado : empleados.values()) {
			if(empleado.getEstado() == false) {
				empleadoDisponible = empleado.getLegajo();
				getTarea(numero, titulo).reasignarEmpleado(empleadoDisponible);
		    	getEmpleado(empleadoDisponible).reasignarTarea(titulo);
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
    	
    	if(getTarea(numero, titulo).getEmpleadoAsignado() != 0) {
    		throw new IllegalArgumentException("Ya hay un empleado asignado");
    	}
    	
    	if(getProyecto(numero).estaFinalizado()) {
    		throw new IllegalArgumentException("El proyecto ya esta finalizado");
    	}
    	
    	int empleadoDisponible = 0;
    	int menosRetraso = 99;
    	
    	for (Empleado empleado : empleados.values()) {
			if(empleado.getEstado() == false && menosRetraso > empleado.getRetraso()) {
				menosRetraso = empleado.getRetraso();
				empleadoDisponible = empleado.getLegajo();
			}
		}
    	
    	if(empleadoDisponible == 0) {
    		throw new IllegalArgumentException("No hay empleados disponibles");
    	}
    	
    	getTarea(numero, titulo).reasignarEmpleado(empleadoDisponible);
    	getEmpleado(empleadoDisponible).reasignarTarea(titulo);
    }
	
	@Override		 
    public void registrarRetrasoEnTarea(Integer numero, String titulo, double cantidadDias){
		if(numero <= 0 || titulo == null || cantidadDias <= 0) {
    		throw new IllegalArgumentException("Ingrese valores validos");
    	}
		
		getTarea(numero, titulo).agregarRetraso(cantidadDias);
		int empleadoRetrasado = getTarea(numero, titulo).getEmpleadoAsignado();
		getEmpleado(empleadoRetrasado).sumarRetraso();
    }
	
	@Override
    public void agregarTareaEnProyecto(Integer numero, String titulo, String descripcion, double dias){
		if(numero <= 0 || titulo == null || descripcion == null || dias <= 0) {
			throw new IllegalArgumentException("Ingrese valores validos");
		}
		validadorProyectos();
		
		Tarea tarea = new Tarea(numero, titulo, descripcion, dias);
		getProyecto(numero).tareas.put(titulo, tarea);
    }
	
	@Override
    public void finalizarTarea(Integer numero, String titulo){
		if(numero <= 0 || titulo == null) {
			throw new IllegalArgumentException("Ingrese valores validos");
		}
		if(proyectos.get(numero).tareas.get(titulo).getEstado() == true) {
			throw new IllegalArgumentException("La tarea ya fue finalizada");
		}
		proyectos.get(numero).tareas.get(titulo).cambiarEstado();
    }

	@Override
    public void finalizarProyecto(Integer numero, String fin){
		if(numero <= 0 || fin == null) {
			throw new IllegalArgumentException("Ingrese valores validos");
		}
		if(LocalDate.parse(fin).isBefore(LocalDate.parse(getProyecto(numero).getFechaInicio()))) {
			throw new IllegalArgumentException("La fecha ingresada no puede ser anterior a la fecha de inicio");
		}
		getProyecto(numero).finalizarProyecto(fin);
    }
	
	@Override
    public void reasignarEmpleadoEnProyecto(Integer numero, Integer legajo, String titulo){
		if(getTarea(numero, titulo).getEmpleadoAsignado() == 0 ) {
			throw new IllegalArgumentException("No hay ningun empleado asignado a la tarea");
		}
		
		int empleadoLiberado = getTarea(numero, titulo).getEmpleadoAsignado();
		getTarea(numero, titulo).reasignarEmpleado(legajo);
		getEmpleado(empleadoLiberado).quitarTarea();
		getEmpleado(legajo).reasignarTarea(titulo);
    }
	
	@Override
    public void reasignarEmpleadoConMenosRetraso(Integer numero, String titulo){
		if(getTarea(numero, titulo).getEmpleadoAsignado() == 0) {
			throw new IllegalArgumentException("No hay ningun empleado asignado a la tarea");
		}
		
		int empleadoDisponible = 0;
    	int menosRetraso = 99;
    	
    	for (Empleado empleado : empleados.values()) {
			if(empleado.getEstado() == false && menosRetraso > empleado.getRetraso()) {
				menosRetraso = empleado.getRetraso();
				empleadoDisponible = empleado.getLegajo();
			}
		}
    	
    	if(empleadoDisponible == 0) {
    		throw new IllegalArgumentException("No hay empleados disponibles");
    	}
    	
    	int empleadoLiberado = getTarea(numero, titulo).getEmpleadoAsignado();
    	getTarea(numero, titulo).reasignarEmpleado(empleadoDisponible);
    	getEmpleado(empleadoDisponible).reasignarTarea(titulo);
    	getEmpleado(empleadoLiberado).quitarTarea();
    }
	
	@Override
    public double costoProyecto(Integer numero){
		Proyecto proyecto = getProyecto(numero);
		double costoProyecto = 0;
		
		for (Tarea tarea : proyecto.tareas.values()) {
			costoProyecto += (tarea.getDias());
			if(getEmpleado(tarea.getEmpleadoAsignado()) != null) {
				if(tarea.getDias() % 1 != 0) {
					if(getEmpleado(tarea.getEmpleadoAsignado()).getRetraso() > 0) {
						costoProyecto += (tarea.getDias() + (1 - (tarea.getDias() % 1))) * getEmpleado(tarea.getEmpleadoAsignado()).valor;
					} else {
						costoProyecto += ((tarea.getDias() + (1 - (tarea.getDias() % 1))) * getEmpleado(tarea.getEmpleadoAsignado()).valor) * 1.02;
					}
				} if(tarea.getDias() % 1 == 0) {
					if(getEmpleado(tarea.getEmpleadoAsignado()).getRetraso() > 0) {
						costoProyecto += tarea.getDias() * getEmpleado(tarea.getEmpleadoAsignado()).valor;
					} else {
						costoProyecto += (tarea.getDias() * getEmpleado(tarea.getEmpleadoAsignado()).valor) * 1.02;
					}
				}
			} else {
				costoProyecto += tarea.getDias() * 8 * getEmpleado(tarea.getEmpleadoAsignado()).valor;
			}
		}
		
		if(proyecto.getFechaFinReal() != proyecto.getFechaFinEstimado()) {
			costoProyecto = costoProyecto * 1.25;
		} else {
			costoProyecto = costoProyecto * 1.35;
		}
		
		return costoProyecto;
    }
	
	@Override
    public List<Tupla<Integer, String>> proyectosFinalizados(){
		List<Tupla<Integer,String>> lista = new ArrayList<>();
		
		for (Proyecto proyecto : proyectos.values()) {
			if(proyecto.estaFinalizado()) {
				Tupla<Integer,String> tupla = new Tupla<>();
				tupla.setValor1(proyecto.codigoProyecto);
				tupla.setValor2(proyecto.domicilio);
				lista.add(tupla);
			}
		}
		
		return lista;
	}

	@Override
    public List<Tupla<Integer, String>> proyectosPendientes(){
		List<Tupla<Integer,String>> lista = new ArrayList<>();
		
		for (Proyecto proyecto : proyectos.values()) {
			if(proyecto.getEstado() == "PENDIENTE") {
				Tupla<Integer,String> tupla = new Tupla<>();
				tupla.setValor1(proyecto.codigoProyecto);
				tupla.setValor2(proyecto.domicilio);
				lista.add(tupla);
			}
		}
		
		return lista;
    }

	@Override
    public List<Tupla<Integer, String>> proyectosActivos(){
		List<Tupla<Integer,String>> lista = new ArrayList<>();
		
		for (Proyecto proyecto : proyectos.values()) {
			if(proyecto.getEstado() == "ACTIVOS") {
				Tupla<Integer,String> tupla = new Tupla<>();
				tupla.setValor1(proyecto.codigoProyecto);
				tupla.setValor2(proyecto.domicilio);
				lista.add(tupla);
			}
		}
		
		return lista;
	}

	@Override
    public Object[] empleadosNoAsignados(){
		Object[] empleadosNoAsignados =  new Object[empleados.size()];
		int cont = 0;
		
		for (Empleado empleado : empleados.values()) {
			if(empleado.getEstado() == false) {
				empleadosNoAsignados[cont] = empleado.getLegajo();
				cont++;
			}
		}
		
		return empleadosNoAsignados;
	}

	@Override
    public boolean estaFinalizado(Integer numero){
		return getProyecto(numero).estaFinalizado();
	}

	@Override
    public int consultarCantidadRetrasosEmpleado(Integer legajo) {
		return getEmpleado(legajo).getRetraso();
	}

	@Override
    public List<Tupla<Integer, String>> empleadosAsignadosAProyecto(Integer numero){
		List<Tupla<Integer,String>> lista = new ArrayList<>();
		Proyecto proyecto = getProyecto(numero);
		
		for (Tarea tarea : proyecto.tareas.values()) {
			if(tarea.getEmpleadoAsignado() != 0) {
				Tupla<Integer,String> tupla = new Tupla<>();
				tupla.setValor1(tarea.getEmpleadoAsignado());
				tupla.setValor2(getEmpleado(tarea.getEmpleadoAsignado()).getNombre());
				lista.add(tupla);
			}
		}
		
		return lista;
	}

	@Override
    public Object[] tareasProyectoNoAsignadas(Integer numero){
		ArrayList<Tarea> tareas = getProyecto(numero).getTareasProyecto();
		Object[] tareasSinAsignar =  new Object[tareas.size()];
		int cont = 0;
		
		if(getProyecto(numero).estaFinalizado()) {
			throw new IllegalArgumentException("El proyecto esta finalizado");
		}
		
		for (Tarea tarea : tareas) {
			if(tarea.getEstado() == false) {
				tareasSinAsignar[cont] = tarea;
				cont++;
			}
		}
		
		return tareasSinAsignar;
	}
    
	@Override
    public Object[] tareasDeUnProyecto(Integer numero){
		ArrayList<Tarea> tareas = getProyecto(numero).getTareasProyecto();
		Object[] tareasSinAsignar =  new Object[tareas.size()];
		int cont = 0;
		
		for (Tarea tarea : tareas) {
			tareasSinAsignar[cont] = tarea;
			cont++;
		}
		
		return tareasSinAsignar;
	}

	@Override
    public String consultarDomicilioProyecto(Integer numero){
		return getProyecto(numero).getDomicilio();
	}

	@Override
    public boolean tieneRestrasos(Integer legajo){
		return getEmpleado(legajo).getRetraso() > 0;
	}
	
	@Override
    public List<Tupla<Integer, String>> empleados(){
		List<Tupla<Integer,String>> lista = new ArrayList<>();
		
		for (Empleado empleado : empleados.values()) {
			Tupla<Integer,String> tupla = new Tupla<>();
			tupla.setValor1(empleado.getLegajo());
			tupla.setValor2(empleado.getNombre());
			lista.add(tupla);
		}
		
		return lista;
	}

	@Override
    public String consultarProyecto(Integer numero){
		return getProyecto(numero).toString();
	}
}
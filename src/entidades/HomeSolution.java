package entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class HomeSolution implements IHomeSolution {
	HashMap<Integer,Empleado> empleados;
	HashMap<Integer,Proyecto> proyectos;

	public HomeSolution() {
		this.empleados = new HashMap<>();
		this.proyectos = new HashMap<>();
	}
	
	public Proyecto getProyecto(int numero) {
		return proyectos.get(numero);
	}
	public Tarea getTarea(int numero, String titulo) {
		return getProyecto(numero).getTarea(titulo);
	}
	public Empleado getEmpleado(int numero) {
		return empleados.get(numero);
	}
	
	@Override
    public void registrarEmpleado(String nombre, double valor) {
    	if(nombre == null || valor <= 0) {
    		throw new IllegalArgumentException("Ingrese un nombre valido y valor mayor a 0");
    	}
    	EmpleadoComun empleado = new EmpleadoComun(nombre, valor);
    	empleados.put(empleado.getLegajo(), empleado);
    }
	
	@Override
    public void registrarEmpleado(String nombre, double valor, String categoria) {
		if(nombre == null || valor <= 0 || categoria == null  || !(categoria.equalsIgnoreCase("EXPERTO") || categoria.equalsIgnoreCase("INICIAL") || categoria.equalsIgnoreCase("TECNICO"))) {
    		throw new IllegalArgumentException("Ingrese un nombre valido y valor mayor a 0");
    	}
    	EmpleadoDePlanta empleado = new EmpleadoDePlanta(nombre, valor, categoria);
    	empleados.put(empleado.getLegajo(), empleado);
    }
	
	@Override
	public void registrarProyecto(String[] titulos, String[] descripcion, double[] dias,
            String domicilio, String[] cliente, String inicio, String finEstimado) {
			LocalDate fechaInicio = LocalDate.parse(inicio);
			LocalDate fechaFin = LocalDate.parse(finEstimado);

			if (fechaFin.isBefore(fechaInicio)) {
				throw new IllegalArgumentException("La fecha de fin no puede ser anterior al inicio.");
			}
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
    	if(getTarea(numero, titulo).getEstado()) {
    		throw new IllegalArgumentException("La tarea ya esta finalizada");
    	}
    	Proyecto proyecto = getProyecto(numero);
    	Tarea tarea = getTarea(numero, titulo);
    	
    	int empleadoDisponible = 0;
    	
    	for (Empleado empleado : empleados.values()) {
			if(empleado.getTareaAsignada() == null) {
				empleadoDisponible = empleado.getLegajo();
				tarea.reasignarEmpleado(empleadoDisponible);
		    	getEmpleado(empleadoDisponible).reasignarTarea(titulo);
				break;
			}
		}
    	boolean empleadoAsignado = true;
    	for (Tarea tareaP : proyecto.getTareasProyecto()) {
    		if (tareaP.getEmpleadoAsignado() != 0) {
    			empleadoAsignado = true;
    		}
    		else {
    			empleadoAsignado = false;
    			break;
    		}	
    	}
    	if (empleadoAsignado == true) {
    		proyecto.activarProyecto();
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
    	if(getTarea(numero, titulo).getEstado()) {
    		throw new IllegalArgumentException("La tarea ya esta finalizada");
    	}
    	
    	int empleadoDisponible = 0;
    	int menosRetraso = Integer.MAX_VALUE;
    	
    	for (Empleado empleado : empleados.values()) {
			if((empleado.getTareaAsignada() == null) && (menosRetraso > empleado.getRetraso())) {
				menosRetraso = empleado.getRetraso();
				empleadoDisponible = empleado.getLegajo();
			}
		}
    	
    	if(empleadoDisponible == 0) {
    		throw new IllegalArgumentException("No hay empleados disponibles");
    	}
    	
    	getTarea(numero, titulo).reasignarEmpleado(empleadoDisponible);
    	getEmpleado(empleadoDisponible).reasignarTarea(titulo);
    	
    	boolean empleadoAsignado = true;
    	for (Tarea tareaP : getProyecto(numero).getTareasProyecto()) {
    		if (tareaP.getEmpleadoAsignado() != 0) {
    			empleadoAsignado = true;
    		}
    		else {
    			empleadoAsignado = false;
    			break;
    		}	
    	}
    	if (empleadoAsignado == true) {
    		getProyecto(numero).activarProyecto();
    	}
    }
	
	@Override		 
    public void registrarRetrasoEnTarea(Integer numero, String titulo, double cantidadDias){
		if(numero <= 0 || titulo == null || cantidadDias <= 0) {
    		throw new IllegalArgumentException("Ingrese valores validos");
    	}
		if(getTarea(numero, titulo).getEstado()) {
    		throw new IllegalArgumentException("La tarea ya esta finalizada");
    	}
		getTarea(numero, titulo).agregarRetraso(cantidadDias);
		int empleadoRetrasado = getTarea(numero, titulo).getEmpleadoAsignado();
		if (empleadoRetrasado == 0) {
			throw new IllegalArgumentException("No hay un empleado asignado");
		} else {			
			getEmpleado(empleadoRetrasado).sumarRetraso();
		}
    }
	
	@Override
    public void agregarTareaEnProyecto(Integer numero, String titulo, String descripcion, double dias){
		if(numero <= 0 || titulo == null || descripcion == null || dias <= 0) {
			throw new IllegalArgumentException("Ingrese valores validos");
		}
		if (proyectos == null) {
			throw new IllegalArgumentException("No se encuentran proyectos");
		}
		
		Tarea tarea = new Tarea(numero, titulo, descripcion, dias);
		getProyecto(numero).tareas.put(titulo, tarea);
    }
	
	@Override
    public void finalizarTarea(Integer numero, String titulo){
		if(numero <= 0 || numero == null || titulo == null) {
			throw new IllegalArgumentException("Ingrese valores validos");
		}
		if(getTarea(numero, titulo).getEstado()) {
			throw new IllegalArgumentException("La tarea ya fue finalizada");
		}
		Empleado empleadoAsignado = getEmpleado(getTarea(numero, titulo).getEmpleadoAsignado());
		if (empleadoAsignado != null) {
			(getEmpleado(getTarea(numero, titulo).getEmpleadoAsignado())).quitarTarea();			
		}
		(getProyecto(numero).getTarea(titulo)).cambiarEstado();
    }

	@Override
    public void finalizarProyecto(Integer numero, String fin){
		if(numero <= 0 || numero == null || fin == null) {
			throw new IllegalArgumentException("Ingrese valores validos");
		}
		if(getProyecto(numero).estaFinalizado()) {
			throw new IllegalArgumentException("El proyecto ya está finalizado");
		}
		if(LocalDate.parse(fin).isBefore(LocalDate.parse(getProyecto(numero).getFechaInicio())) || LocalDate.parse(fin).isBefore(LocalDate.parse(getProyecto(numero).getFechaFinEstimado()))) {
			throw new IllegalArgumentException("La fecha ingresada no puede ser anterior a la fecha de inicio o la fecha estimada de Fin");
		}
		for (Tarea tarea : getProyecto(numero).getTareasProyecto()) {
			if (tarea.getEstado() == false) {
				finalizarTarea(numero, tarea.toString());
			}
		}
		getProyecto(numero).finalizarProyecto(fin);
		getProyecto(numero).actualizarCosto(costoProyecto(numero));
	}
	
	@Override
    public void reasignarEmpleadoEnProyecto(Integer numero, Integer legajo, String titulo){
		if(getTarea(numero, titulo).getEmpleadoAsignado() == 0 ) {
			throw new IllegalArgumentException("No hay ningun empleado asignado a la tarea");
		}
		if(getTarea(numero, titulo).getEstado()) {
    		throw new IllegalArgumentException("La tarea ya esta finalizada");
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
		if(getTarea(numero, titulo).getEstado()) {
    		throw new IllegalArgumentException("La tarea ya esta finalizada");
    	}
		
		int empleadoDisponible = 0;
    	int menosRetraso = Integer.MAX_VALUE;
    	
    	for (Empleado empleado : empleados.values()) {
			if(empleado.getTareaAsignada() == null && menosRetraso > empleado.getRetraso()) {
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
			if (getEmpleado(tarea.getEmpleadoAsignado()) instanceof EmpleadoDePlanta) {
	            EmpleadoDePlanta empPlanta = (EmpleadoDePlanta) getEmpleado(tarea.getEmpleadoAsignado());
				if(tarea.getDias() % 1 != 0) {
					if(empPlanta.getRetraso() > 0) {
						costoProyecto += (tarea.getDias() + (1 - (tarea.getDias() % 1))) * empPlanta.getValor();
					} else {
						costoProyecto += ((tarea.getDias() + (1 - (tarea.getDias() % 1))) * empPlanta.getValor()) * 1.02;
					}
				} if(tarea.getDias() % 1 == 0) {
					if(empPlanta.getRetraso() > 0) {
						costoProyecto += tarea.getDias() * empPlanta.getValor();
					} else {
						costoProyecto += (tarea.getDias() * empPlanta.getValor()) * 1.02;
					}
				}
			} else if (getEmpleado(tarea.getEmpleadoAsignado()) instanceof EmpleadoComun) {
				costoProyecto += tarea.getDias() * 8 * getEmpleado(tarea.getEmpleadoAsignado()).getValor();
			}
		}
		if(proyecto.getFechaFinReal() != proyecto.getFechaFinEstimado() && proyecto.getFechaFinReal() != null) {
			costoProyecto *= 1.25;
		} else {
			costoProyecto *= 1.35;
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
			if(proyecto.getEstado() == Estado.pendiente) {
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
			if(proyecto.getEstado() == Estado.activo) {
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
		ArrayList<Integer> arrayEmpleadosNoAsignados =  new ArrayList<>();
        for (Empleado empleado : empleados.values()) {
            if(empleado.getTareaAsignada() == null) {
            	arrayEmpleadosNoAsignados.add(empleado.getLegajo());
            }
        }
        return arrayEmpleadosNoAsignados.toArray();
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
		if(getProyecto(numero).estaFinalizado()) {
			throw new IllegalArgumentException("El proyecto esta finalizado");
		}
		ArrayList<Tarea> tareasP = getProyecto(numero).getTareasProyecto();
		Iterator<Tarea> tareas = tareasP.iterator();
		ArrayList<Tarea> tareasSinAsignar =  new ArrayList<>();
		
		while (tareas.hasNext()) {
			Tarea tarea = tareas.next();
			if(tarea.getEmpleadoAsignado() == 0) {
				tareasSinAsignar.add(tarea);
			}
		}
		return tareasSinAsignar.toArray();
	}
    
	@Override
    public Object[] tareasDeUnProyecto(Integer numero){
		ArrayList<Tarea> tareas = getProyecto(numero).getTareasProyecto();	
		return tareas.toArray();
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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Proyectos :").append(proyectos)
	      .append("\nEmpleados :").append(empleados);
	      
		return sb.toString();
	}
	
}
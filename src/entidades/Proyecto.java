package entidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Proyecto {
	public int codigoProyecto = 0;
	private static int cont = 0;
	private Cliente cliente;
	public String domicilio;
	private String inicio;
	private String finEstimado;
	private String finReal;
	private String estado;
	private double costo;
	HashMap<String,Tarea> tareas;
	
	public Proyecto(String[] cliente, String domicilio, String inicio, String finEstimado) {
		cont++;
		this.codigoProyecto = cont;
		this.cliente = new Cliente(cliente[0], cliente[1], cliente[2]);
		this.domicilio = domicilio;
		this.inicio = inicio;
		this.finEstimado = finEstimado;
		this.estado = Estado.pendiente;
		this.costo = 0;
		this.tareas = new HashMap<>();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Proyecto [codigoProyecto=").append(codigoProyecto)
	      .append(", cliente=").append(cliente)
	      .append(", domicilio=").append(domicilio)
	      .append(", inicio=").append(inicio)
	      .append(", finEstimado=").append(finEstimado)
	      .append(", finReal=").append(finReal)
	      .append(", estado=").append(estado)
	      .append(", costo=").append(costo)
	      .append(", tareas=").append(tareas)
	      .append("]");
		return sb.toString();
	}	
	
	public boolean estaFinalizado() {
		if (estado == Estado.finalizado) {
			return true;
		}
		return false;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void finalizarProyecto(String fechaFin) {
		estado = Estado.finalizado;
		finReal = fechaFin;
	}
	public void activarProyecto() {
		estado = Estado.activo;
	}
		
	public String getFechaInicio() {
		return inicio;
	}
	
	public String getFechaFinEstimado() {
		return finEstimado;
	}
	
	public String getFechaFinReal() {
		return finReal;
	}
	
	public String getDomicilio() {
		return domicilio;
	}
	public double getCosto() {
		return costo;
	}
	
	public Tarea getTarea(String titulo){
		return tareas.get(titulo);
	}
	
	public void  actualizarCosto(double cost) {
		costo = cost;
	}
	
	public ArrayList<Tarea> getTareasProyecto() {
		ArrayList<Tarea> listaTareas = new ArrayList<>();
		for (Tarea tarea : tareas.values()) {
			listaTareas.add(tarea);
		}	
		return listaTareas;
	}
	
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Proyecto other = (Proyecto) obj;
	    return codigoProyecto == other.codigoProyecto;
	}
	
}

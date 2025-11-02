package entidades;

public class Empleado {
	private String nombre;
	private int legajo = 1;
	private int contadorLegajo = 0;
	private boolean estado;
	private double valor;
	private int retrasos;
	private String tituloTarea;
	
	public Empleado(String nombre, double valor) {
		this.nombre = nombre;
		contadorLegajo++;
		this.legajo = contadorLegajo;
		this.estado = false;
		this.valor = valor;
		this.retrasos = 0;
		tituloTarea = null;
	}

	public String toString() {
		return "Empleado [nombre=" + nombre + ", legajo=" + legajo + ", estado=" + estado + ", retrasos=" + retrasos
				+ ", TituloTarea=" + tituloTarea + "]";
	}
	
	public void reasignarTarea(String tarea) {
		tituloTarea = tarea;
	}
	public void sumarRetraso() {
		retrasos += 1;
	}
	
	
	public int getLegajo() {
		return legajo;
	}
	public boolean getEstado() {
		return estado;
	}
	public int getRetraso() {
		return retrasos;
	}
	public String getTareaAsignada() {
		return tituloTarea;
	}
}

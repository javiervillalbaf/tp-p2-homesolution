package entidades;

public class Empleado {
	private String nombre;
	private int legajo;
	private static int cont = 0;
	public double valor;
	private int retrasos;
	private String tituloTarea;
	
	public Empleado(String nombre, double valor) {
		this.nombre = nombre;
		cont++;
		this.legajo = cont;
		this.valor = valor;
		this.retrasos = 0;
		tituloTarea = null;
	}

	public String toString() {
		return "" + legajo;
	}
	
	public void reasignarTarea(String tarea) {
		tituloTarea = tarea;
	}
	
	public void quitarTarea() {
		tituloTarea = null;
	}
	
	public void sumarRetraso() {
		retrasos += 1;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public int getLegajo() {
		return legajo;
	}
	public int getRetraso() {
		return retrasos;
	}
	public String getTareaAsignada() {
		return tituloTarea;
	}
}

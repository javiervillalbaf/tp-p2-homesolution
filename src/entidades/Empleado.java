package entidades;

public class Empleado {
	private String nombre;
	public int legajo = 1;
	private int contadorLegajo = 0;
	public boolean estado;
	private double valor;
	public int retrasos;
	public String tituloTarea;
	
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
}

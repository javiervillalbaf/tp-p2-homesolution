package entidades;

public abstract class Empleado {
	private String nombre;
	private int legajo;
	private static int cont = 0;
	private int retrasos;
	private String tituloTarea;
	
	public Empleado(String nombre) {
		this.nombre = nombre;
		cont++;
		this.legajo = cont;
		this.retrasos = 0;
		tituloTarea = null;
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

	@Override
	public String toString() {
		return "" + getLegajo();
	}
	
	protected abstract double getValor();
	
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Empleado other = (Empleado) obj;
	    return legajo == other.legajo;
	}
}

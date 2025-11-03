package entidades;

public class Tarea {
	private int codigoProyecto;
	private String titulo;
	private String descripcion;
	private boolean estado;
	private double dias;
	private int empleadoAsignado;
	
	public Tarea(int codigoProyecto, String titulo, String descripcion, double dias) {
		this.codigoProyecto = codigoProyecto;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.estado = false;
		this.dias = dias;
		this.empleadoAsignado = 0;
	}

	@Override
	public String toString() {
		return "titulo=" + titulo;
	}
	
	public void cambiarEstado() {
		estado = !estado;
	}
	public void reasignarEmpleado(int empleado) {
		empleadoAsignado = empleado;
	}
	public void agregarRetraso(double retraso) {
		dias += retraso;
	}
	
	public boolean getEstado() {
		return estado;
	}
	public int getEmpleadoAsignado() {
		return empleadoAsignado;
	}
	public double getDias() {
		return dias;
	}
	
}

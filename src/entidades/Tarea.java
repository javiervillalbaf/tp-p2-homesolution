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
		return titulo;
	}
	
	public void cambiarEstado() {
		estado = !estado;
	}
	public void reasignarEmpleado(int empleado) {
		empleadoAsignado = empleado;
	}
	public void quitarEmpleado() {
		empleadoAsignado = 0;
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
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Tarea other = (Tarea) obj;
	    return titulo == other.titulo;
	}
	
}

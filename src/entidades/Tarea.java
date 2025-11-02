package entidades;

public class Tarea {
	private int codigoProyecto;
	private String titulo;
	private String descripcion;
	private boolean estado;
	public double dias;
	public int empleadoAsignado;
	
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
		return "Tarea [codigoProyecto=" + codigoProyecto + ", titulo=" + titulo + ", descripcion=" + descripcion
				+ ", estado=" + estado + ", horas=" + dias + ", empleadoAsignado=" + empleadoAsignado + "]";
	}
}

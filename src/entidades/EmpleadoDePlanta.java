package entidades;

public class EmpleadoDePlanta extends Empleado {
	private String categoria;

	public EmpleadoDePlanta(String nombre, double valor, String categoria) {
		super(nombre, valor);
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "EmpleadoDePlanta [categoria=" + categoria + "]";
	}
	
	public String getCategoria() {
		return categoria;
	}
}

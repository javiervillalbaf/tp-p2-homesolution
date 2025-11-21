package entidades;

public class EmpleadoDePlanta extends Empleado {
	private String categoria;
	public double valorXDia;

	public EmpleadoDePlanta(String nombre, double valor, String categoria) {
		super(nombre);
		this.valorXDia = valor;
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "EmpleadoDePlanta [categoria=" + categoria + "]";
	}
	
    public String getCategoria() {
        return categoria;
    }
    @Override
    public double getValor() {
        return valorXDia;
    }
    @Override
    public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    EmpleadoDePlanta other = (EmpleadoDePlanta) obj;
	    return getLegajo() == other.getLegajo();
	}
}

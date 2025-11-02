package entidades;

import java.util.Arrays;
import java.util.HashMap;

public class Proyecto {
	int codigoProyecto = 1;
	private String[] cliente;
	private String domicilio;
	private String inicio;
	private String finEstimado;
	private String finReal;
	public String estado;
	private double costo;
	HashMap<String,Tarea> tareas;
	
	
	public Proyecto(String[] cliente, String domicilio, String inicio, String finEstimado) {
		codigoProyecto++;
		this.codigoProyecto = codigoProyecto;
		this.cliente = cliente;
		this.domicilio = domicilio;
		this.inicio = inicio;
		this.finEstimado = finEstimado;
		this.estado = "Pendiente";
		this.costo = 0;
	}

	@Override
	public String toString() {
		return "Proyecto [codigoProyecto=" + codigoProyecto + ", cliente=" + Arrays.toString(cliente) + ", domicilio="
				+ domicilio + ", inicio=" + inicio + ", finEstimado=" + finEstimado + ", finReal=" + finReal
				+ ", estado=" + estado + ", costo=" + costo + ", tareas=" + tareas + "]";
	}	
}

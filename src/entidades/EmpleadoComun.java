package entidades;

public class EmpleadoComun extends Empleado {
		public double valorXHora;

		public EmpleadoComun(String nombre, double valor) {
			super(nombre);
			this.valorXHora = valor;
		}
		@Override
		public double getValor() {
	        return valorXHora;
	    }
}

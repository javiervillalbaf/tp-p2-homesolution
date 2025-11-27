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
		
		@Override
	    public boolean equals(Object obj) {
		    if (this == obj) return true;
		    if (obj == null || getClass() != obj.getClass()) return false;
		    EmpleadoComun other = (EmpleadoComun) obj;
		    return getLegajo() == other.getLegajo();
		}
}

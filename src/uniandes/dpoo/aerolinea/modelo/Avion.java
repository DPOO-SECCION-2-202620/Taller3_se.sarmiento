package uniandes.dpoo.aerolinea.modelo;

public class Avion {
	
	private int capacidad;
	
	private String nombre;
	
	public Avion(String nombre, int capacidad)
	{
		this.nombre = nombre;
		this.capacidad = capacidad;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public String getNombre() {
		return nombre;
	}
}

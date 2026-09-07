package uniandes.dpoo.aerolinea.modelo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import uniandes.dpoo.aerolinea.exceptions.VueloSobrevendidoException;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.tarifas.CalculadoraTarifas;
import uniandes.dpoo.aerolinea.tiquetes.GeneradorTiquetes;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

public class Vuelo{

	private Avion avion;
	
	private String fecha;
	
	private Ruta ruta;
	
	private Map<String, Tiquete> tiquetes;
	
	public Vuelo(Ruta rutas, String fecha, Avion avion) {
		super();
		this.avion = avion;
		this.fecha = fecha;
		this.ruta = rutas;
		this.tiquetes = new HashMap<String, Tiquete>( );
	}

	public Avion getAvion() {
		return avion;
	}

	public String getFecha() {
		return fecha;
	}

	public Ruta getRuta() {
		return ruta;
	}

	public Collection<Tiquete> getTiquetes() {
		return tiquetes.values();
	}
	
	public int venderTiquetes​(Cliente cliente, CalculadoraTarifas calculadora, int cantidad) throws VueloSobrevendidoException {
		
		if(tiquetes.size() + cantidad > avion.getCapacidad())
		{
		    throw new VueloSobrevendidoException(this);
		}
		
		int tarifa = calculadora.calcularTarifa(this, cliente);
		int totalTarifa = 0;
		for(int i=0; i<cantidad; i++) {
			Tiquete tiquete = GeneradorTiquetes.generarTiquete(this, cliente, tarifa);
			totalTarifa += tarifa;
			cliente.agregarTiquete(tiquete);
			tiquetes.put(tiquete.getCodigo(), tiquete);
		}
		return totalTarifa;
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}

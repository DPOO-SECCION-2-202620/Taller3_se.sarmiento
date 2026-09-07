package uniandes.dpoo.aerolinea.tiquetes;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;

public class Tiquete {

	private Cliente	cliente;
	
	private String codigo;
	
	private int	tarifa;
	
	private boolean	usado;
	
	private Vuelo vuelo;

	public Tiquete(String codigo, Vuelo vuelo, Cliente cliente, int tarifa) {
		super();
		this.cliente = cliente;
		this.codigo = codigo;
		this.tarifa = tarifa;
		this.vuelo = vuelo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public String getCodigo() {
		return codigo;
	}

	public int getTarifa() {
		return tarifa;
	}

	public Vuelo getVuelo() {
		return vuelo;
	}
	
	public void marcarComoUsado() {
		usado = true;
	}
	
	public boolean esUsado() {
		return usado;
	}
}

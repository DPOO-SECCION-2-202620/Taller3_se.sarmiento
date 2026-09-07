package uniandes.dpoo.aerolinea.modelo.cliente;

import java.util.Collection;
import java.util.List;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

public abstract class Cliente {

	private List<Tiquete> tiquetesSinUsar;
	
	private List<Tiquete> tiquetesUsados;
	
	public Cliente(List<Tiquete> tiquetesSinUsar, List<Tiquete> tiquetesUsados) {
		super();
		this.tiquetesSinUsar = tiquetesSinUsar;
		this.tiquetesUsados = tiquetesUsados;
	}
	
	public abstract String getTipoCliente();
	
	public abstract String getIdentificador();
	
	public List<Tiquete> getTiquetesSinUsar() {
		return tiquetesSinUsar;
	}

	public List<Tiquete> getTiquetesUsados() {
		return tiquetesUsados;
	}

	public void agregarTiquete​(Tiquete tiquete) {
		this.tiquetesSinUsar.add(tiquete);
	}
	
	public int calcularValorTotalTiquetes() {
		int valtorTiquetes = 0;
		for(Tiquete tiquete : tiquetesUsados) {
			valtorTiquetes += tiquete.getTarifa();
		}
		for(Tiquete tiquete : tiquetesSinUsar) {
			valtorTiquetes += tiquete.getTarifa();
		}
		return valtorTiquetes;
	}
	
	public void usarTiquetes​(Vuelo vuelo) {
		Collection<Tiquete> tiquetesVuelo = vuelo.getTiquetes();
	    for(Tiquete tiquete : tiquetesVuelo)
	    {
	        if(tiquetesSinUsar.contains(tiquete))
	        {
	            tiquetesSinUsar.remove(tiquete);
	            tiquetesUsados.add(tiquete);
	            tiquete.marcarComoUsado();
	        }
	    }
	}
}

package uniandes.dpoo.aerolinea.modelo.tarifas;

import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;

public class CalculadoraTarifasTemporadaAlta extends CalculadoraTarifas{

	protected final int COSTO_POR_KM = 1000;

	
	public CalculadoraTarifasTemporadaAlta() {
		super();
	}

	@Override
	protected int calcularCostoBase(Vuelo vuelo, Cliente cliente) {
		Ruta ruta = vuelo.getRuta();
		int distancia = calcularDistanciaVuelo(ruta);
		return COSTO_POR_KM * distancia;
	}

	@Override
	protected double calcularPorcentajeDescuento(Cliente cliente) {
		return 0;
	}
	
}

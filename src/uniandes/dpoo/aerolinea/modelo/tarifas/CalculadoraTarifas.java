package uniandes.dpoo.aerolinea.modelo.tarifas;

import uniandes.dpoo.aerolinea.modelo.Aeropuerto;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;

public abstract class CalculadoraTarifas {

	public static double IMPUESTO = 0.28;

	public CalculadoraTarifas() {
		super();
	}
	
	protected abstract int calcularCostoBase​(Vuelo vuelo, Cliente cliente);
	
	protected int calcularDistanciaVuelo​(Ruta ruta) {
		Aeropuerto origen = ruta.getOrigen();
	    Aeropuerto destino = ruta.getDestino();

	    return Aeropuerto.calcularDistancia(origen, destino);

	}
		
	protected abstract double calcularPorcentajeDescuento​(Cliente cliente);
	
	protected int calcularValorImpuestos​(int costoBase) {
		return (int)(costoBase * IMPUESTO);
	}
	
	public int calcularTarifa(Vuelo vuelo, Cliente cliente) {
		int costoBase = calcularCostoBase( vuelo, cliente );
	    double porcentajeDescuento = calcularPorcentajeDescuento(cliente);
	    int costoConDescuento = costoBase - (int)(costoBase * porcentajeDescuento);
	    int impuestos = calcularValorImpuestos(costoConDescuento);
	    return costoConDescuento + impuestos;
	}
}

package uniandes.dpoo.aerolinea.modelo.tarifas;

import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.cliente.ClienteCorporativo;

public class CalculadoraTarifasTemporadaBaja extends CalculadoraTarifas{
	
	protected final int	COSTO_POR_KM_CORPORATIVO = 900;
	
	protected final int	COSTO_POR_KM_NATURAL = 600;
	
	protected final double	DESCUENTO_GRANDES = 0.2;
	
	protected final double	DESCUENTO_MEDIANAS = 0.1;
	
	protected final double	DESCUENTO_PEQ = 0.02;

	public CalculadoraTarifasTemporadaBaja() {
		super();
	}

	@Override
	protected int calcularCostoBase(Vuelo vuelo, Cliente cliente) {
		Ruta ruta = vuelo.getRuta();
		int distancia = calcularDistanciaVuelo(ruta);
		if(cliente.getTipoCliente().equals("Corporativo")) {
			return COSTO_POR_KM_CORPORATIVO * distancia;
		} else if (cliente.getTipoCliente().equals("Natural")) {
			return COSTO_POR_KM_NATURAL * distancia;
		}
		else return 0;
	}

	@Override
	protected double calcularPorcentajeDescuento​(Cliente cliente) {
		if(cliente.getTipoCliente().equals("Corporativo"))	{
			ClienteCorporativo clienteCorporativo = (ClienteCorporativo) cliente;
			int tamanio = clienteCorporativo.getTamanoEmpresa();
			if(tamanio == 1) {
				return DESCUENTO_GRANDES;
			} else if(tamanio == 2) {
				return DESCUENTO_MEDIANAS;
			} else if(tamanio == 3) {
				return DESCUENTO_PEQ;
			} else {
				return 0;
			}
		}
		else {
			return 0;
		}
	}

	
}

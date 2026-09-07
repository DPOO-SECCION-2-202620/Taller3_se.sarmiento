package uniandes.dpoo.aerolinea.modelo.cliente;

import java.util.ArrayList;

import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

public class ClienteNatural extends Cliente{
	
	public static String NATURAL = "Natural";
	
	private String nombre;
	
	public ClienteNatural(String nombreCliente)
	{
		super(new ArrayList<Tiquete>(), new ArrayList<Tiquete>());
	    this.nombre = nombreCliente;
	}

	@Override
	public String getTipoCliente() {
		return NATURAL;
	}

	@Override
	public String getIdentificador() {
		return nombre;
	}
}

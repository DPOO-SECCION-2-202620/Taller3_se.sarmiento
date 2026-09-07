package uniandes.dpoo.aerolinea.modelo;

/**
 * Esta clase tiene la información de una ruta entre dos aeropuertos que cubre una aerolínea.
 */
public class Ruta
{
	private String codigoRuta;
	
	private Aeropuerto destino;	
	
	private String horaLlegada;	
	
	private String horaSalida;
	
	private Aeropuerto origen;

	
    public Ruta( Aeropuerto origen, Aeropuerto destino, String horaLlegada, String horaSalida, String codigoRuta) {
		super();
		this.codigoRuta = codigoRuta;
		this.destino = destino;
		this.horaLlegada = horaLlegada;
		this.horaSalida = horaSalida;
		this.origen = origen;
	}

	/**
     * Dada una cadena con una hora y minutos, retorna los minutos.
     * 
     * Por ejemplo, para la cadena '715' retorna 15.
     * @param horaCompleta Una cadena con una hora, donde los minutos siempre ocupan los dos últimos caracteres
     * @return Una cantidad de minutos entre 0 y 59
     */
    public static int getMinutos( String horaCompleta )
    {
        int minutos = Integer.parseInt( horaCompleta ) % 100;
        return minutos;
    }

    /**
     * Dada una cadena con una hora y minutos, retorna las horas.
     * 
     * Por ejemplo, para la cadena '715' retorna 7.
     * @param horaCompleta Una cadena con una hora, donde los minutos siempre ocupan los dos últimos caracteres
     * @return Una cantidad de horas entre 0 y 23
     */
    public static int getHoras( String horaCompleta )
    {
        int horas = Integer.parseInt( horaCompleta ) / 100;
        return horas;
    }

	public String getCodigoRuta() {
		return codigoRuta;
	}

	public Aeropuerto getDestino() {
		return destino;
	}

	public String getHoraLlegada() {
		return horaLlegada;
	}

	public String getHoraSalida() {
		return horaSalida;
	}

	public Aeropuerto getOrigen() {
		return origen;
	}

    public int getDuracion() {
    	int horasDuracion = getHoras(horaLlegada) - getHoras(horaSalida);
    	int minutosDuracion = getMinutos(horaLlegada) - getMinutos(horaSalida);
    	int duracion = horasDuracion * 60 + minutosDuracion;
    	return duracion;
    }
    
    
}

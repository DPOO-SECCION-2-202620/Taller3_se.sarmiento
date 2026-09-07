package uniandes.dpoo.aerolinea.persistencia;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import uniandes.dpoo.aerolinea.exceptions.AeropuertoDuplicadoException;
import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import uniandes.dpoo.aerolinea.modelo.Aeropuerto;
import uniandes.dpoo.aerolinea.modelo.Avion;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;

public class PersistenciaAerolineaJson implements IPersistenciaAerolinea{

	
	private static final String NOMBRE = "nombre";           
    private static final String CAPACIDAD = "capacidad";       
    private static final String CODIGO = "codigo";               
    private static final String NOMBRE_CIUDAD = "nombreCiudad";  
    private static final String LATITUD = "latitud";              
    private static final String LONGITUD = "longitud";            
    private static final String CODIGO_RUTA = "codigoRuta";     
    private static final String HORA_SALIDA = "horaSalida";      
    private static final String HORA_LLEGADA = "horaLlegada";   
    private static final String ORIGEN = "origen";               
    private static final String DESTINO = "destino";            
    private static final String FECHA = "fecha";                 
    private static final String AVION = "avion";                 
	
    @Override
    public void cargarAerolinea( String archivo, Aerolinea aerolinea ) throws IOException, InformacionInconsistenteException
    {
        String jsonCompleto = new String( Files.readAllBytes( new File( archivo ).toPath( ) ) );
        JSONObject raiz = new JSONObject( jsonCompleto );

        Map<String, Aeropuerto> aeropuertosPorCodigo = new HashMap<String, Aeropuerto>( );

        cargarAviones( aerolinea, raiz.getJSONArray( "aviones" ) );
        cargarAeropuertos( aerolinea, raiz.getJSONArray( "aeropuertos" ), aeropuertosPorCodigo );
        cargarRutas( aerolinea, raiz.getJSONArray( "rutas" ), aeropuertosPorCodigo );
        cargarVuelos( aerolinea, raiz.getJSONArray( "vuelos" ) );
    }

    @Override
    public void salvarAerolinea( String archivo, Aerolinea aerolinea ) throws IOException
    {
        JSONObject jobject = new JSONObject( );

        salvarAviones( aerolinea, jobject );
        salvarAeropuertos( aerolinea, jobject );
        salvarRutas( aerolinea, jobject );
        salvarVuelos( aerolinea, jobject );

        PrintWriter pw = new PrintWriter( archivo );
        jobject.write( pw, 2, 0 );
        pw.close( );
    }

    private void cargarAviones( Aerolinea aerolinea, JSONArray jAviones )
    {
        int numAviones = jAviones.length( );
        for( int i = 0; i < numAviones; i++ )
        {
            JSONObject jAvion = jAviones.getJSONObject( i );
            String nombre = jAvion.getString( NOMBRE );
            int capacidad = jAvion.getInt( CAPACIDAD );
            aerolinea.agregarAvion( new Avion( nombre, capacidad ) );
        }
    }

    private void salvarAviones( Aerolinea aerolinea, JSONObject jobject )
    {
        JSONArray jAviones = new JSONArray( );
        for( Avion avion : aerolinea.getAviones( ) )
        {
            JSONObject jAvion = new JSONObject( );
            jAvion.put( NOMBRE, avion.getNombre( ) );
            jAvion.put( CAPACIDAD, avion.getCapacidad( ) );
            jAviones.put( jAvion );
        }
        jobject.put( "aviones", jAviones );
    }

    private void cargarAeropuertos( Aerolinea aerolinea, JSONArray jAeropuertos, Map<String, Aeropuerto> aeropuertosPorCodigo ) throws InformacionInconsistenteException
    {
        int numAeropuertos = jAeropuertos.length( );
        for( int i = 0; i < numAeropuertos; i++ )
        {
            JSONObject jAeropuerto = jAeropuertos.getJSONObject( i );
            String nombre = jAeropuerto.getString( NOMBRE );
            String codigo = jAeropuerto.getString( CODIGO );
            String nombreCiudad = jAeropuerto.getString( NOMBRE_CIUDAD );
            double latitud = jAeropuerto.getDouble( LATITUD );
            double longitud = jAeropuerto.getDouble( LONGITUD );

            try
            {
                Aeropuerto nuevoAeropuerto = new Aeropuerto( nombre, codigo, nombreCiudad, latitud, longitud );
                aeropuertosPorCodigo.put( codigo, nuevoAeropuerto );
            }
            catch( AeropuertoDuplicadoException e )
            {
                throw new InformacionInconsistenteException( e.getMessage( ) );
            }
        }
    }

    private void salvarAeropuertos( Aerolinea aerolinea, JSONObject jobject )
    {
        JSONArray jAeropuertos = new JSONArray( );
        Set<String> codigosGuardados = new HashSet<String>( );

        for( Ruta ruta : aerolinea.getRutas( ) )
        {
            Aeropuerto origen = ruta.getOrigen( );
            if( !codigosGuardados.contains( origen.getCodigo( ) ) )
            {
                JSONObject jOrigen = new JSONObject( );
                jOrigen.put( NOMBRE, origen.getNombre( ) );
                jOrigen.put( CODIGO, origen.getCodigo( ) );
                jOrigen.put( NOMBRE_CIUDAD, origen.getNombreCiudad( ) );
                jOrigen.put( LATITUD, origen.getLatitud( ) );
                jOrigen.put( LONGITUD, origen.getLongitud( ) );
                jAeropuertos.put( jOrigen );
                codigosGuardados.add( origen.getCodigo( ) );
            }

            Aeropuerto destino = ruta.getDestino( );
            if( !codigosGuardados.contains( destino.getCodigo( ) ) )
            {
                JSONObject jDestino = new JSONObject( );
                jDestino.put( NOMBRE, destino.getNombre( ) );
                jDestino.put( CODIGO, destino.getCodigo( ) );
                jDestino.put( NOMBRE_CIUDAD, destino.getNombreCiudad( ) );
                jDestino.put( LATITUD, destino.getLatitud( ) );
                jDestino.put( LONGITUD, destino.getLongitud( ) );
                jAeropuertos.put( jDestino );
                codigosGuardados.add( destino.getCodigo( ) );
            }
        }
        jobject.put( "aeropuertos", jAeropuertos );
    }

    private void cargarRutas( Aerolinea aerolinea, JSONArray jRutas, Map<String, Aeropuerto> aeropuertosPorCodigo ) throws InformacionInconsistenteException
    {
        int numRutas = jRutas.length( );
        for( int i = 0; i < numRutas; i++ )
        {
            JSONObject jRuta = jRutas.getJSONObject( i );
            String codigoRuta = jRuta.getString( CODIGO_RUTA );
            String horaSalida = jRuta.getString( HORA_SALIDA );
            String horaLlegada = jRuta.getString( HORA_LLEGADA );
            String codigoOrigen = jRuta.getString( ORIGEN );
            String codigoDestino = jRuta.getString( DESTINO );

            Aeropuerto origen = aeropuertosPorCodigo.get( codigoOrigen );
            Aeropuerto destino = aeropuertosPorCodigo.get( codigoDestino );

            if( origen == null || destino == null )
            {
                throw new InformacionInconsistenteException( "No existe el aeropuerto para la ruta " + codigoRuta );
            }

            aerolinea.agregarRuta( new Ruta( origen, destino, horaSalida, horaLlegada, codigoRuta ) );
        }
    }

    private void salvarRutas( Aerolinea aerolinea, JSONObject jobject )
    {
        JSONArray jRutas = new JSONArray( );
        for( Ruta ruta : aerolinea.getRutas( ) )
        {
            JSONObject jRuta = new JSONObject( );
            jRuta.put( CODIGO_RUTA, ruta.getCodigoRuta( ) );
            jRuta.put( HORA_SALIDA, ruta.getHoraSalida( ) );
            jRuta.put( HORA_LLEGADA, ruta.getHoraLlegada( ) );
            jRuta.put( ORIGEN, ruta.getOrigen( ).getCodigo( ) );
            jRuta.put( DESTINO, ruta.getDestino( ).getCodigo( ) );
            jRutas.put( jRuta );
        }
        jobject.put( "rutas", jRutas );
    }

    private void cargarVuelos( Aerolinea aerolinea, JSONArray jVuelos ) throws InformacionInconsistenteException
    {
        int numVuelos = jVuelos.length( );
        for( int i = 0; i < numVuelos; i++ )
        {
            JSONObject jVuelo = jVuelos.getJSONObject( i );
            String fecha = jVuelo.getString( FECHA );
            String codigoRuta = jVuelo.getString( CODIGO_RUTA );
            String nombreAvion = jVuelo.getString( AVION );

            try
            {
                aerolinea.programarVuelo( fecha, codigoRuta, nombreAvion );
            }
            catch( Exception e )
            {
                throw new InformacionInconsistenteException( e.getMessage( ) );
            }
        }
    }

    private void salvarVuelos( Aerolinea aerolinea, JSONObject jobject )
    {
        JSONArray jVuelos = new JSONArray( );
        for( Vuelo vuelo : aerolinea.getVuelos( ) )
        {
            JSONObject jVuelo = new JSONObject( );
            jVuelo.put( FECHA, vuelo.getFecha( ) );
            jVuelo.put( CODIGO_RUTA, vuelo.getRuta( ).getCodigoRuta( ) );
            jVuelo.put( AVION, vuelo.getAvion( ).getNombre( ) );
            jVuelos.put( jVuelo );
        }
        jobject.put( "vuelos", jVuelos );
    }
}


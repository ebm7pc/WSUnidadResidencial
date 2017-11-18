package co.udea.iw.rest;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import co.udea.iw.dto.Cliente;
import co.udea.iw.exception.IWDaoException;
import co.udea.iw.exception.IWServiceException;
import co.udea.iw.service.ClienteService;

/**
 * 
 * @author Eduardo Bedoya, Yesid Montoya
 *Clase para implementar los servicios web del módulo de Cliente
 */
@Path("Cliente")
@Component
public class ServicioCliente {
	/**
	 * Objeto de tipo Logger para generar los mensajes de eventos de errores y excepciones
	 */
	private Logger logger = Logger.getRootLogger();
	@Autowired
	private ClienteService clienteservice;
	
	/**
	 * Método para obtener la lista de todos los clientes
	 * @return lista de todos los clientes
	 * @throws IWDaoException
	 */
	@Path("ListaClientes")
	@Produces(MediaType.APPLICATION_XML)
	@GET
	public List<Cliente> obtener() throws RemoteException{ //List<ClienteWS>
		
		List<Cliente> listaClientes= null;
		try {
			
			listaClientes=clienteservice.obtener();
			
		} catch (IWDaoException e) {
			throw new RemoteException(e.getMessage());
		}
		
		return listaClientes;
	}
	/**
	 * Método para obtener un cliente determinado
	 * @param ficho número del ficho asociado al cliente
	 * @return cliente con el ficho asociado
	 * @throws IWDaoException
	 * @throws IWServiceException
	 */
	@Path("ObtenerCliente")
	@Produces(MediaType.APPLICATION_XML)
	@GET
	public Cliente obtenerCliente(@QueryParam("ficho") Integer ficho) throws RemoteException, IWServiceException{ //List<ClienteWS>
		Cliente cliente= null;
		try {
			
			cliente=clienteservice.obtener(ficho);
		} catch (IWDaoException e) {
			throw new RemoteException(e.getMessage());
		} catch (IWServiceException e) {
			throw new IWServiceException(e.getMessage());
		}	
		return cliente;
	}
	/**
	 * Método para guardar un cliente
	 * @param ficho ficho del cliente
	 * @param tipo tipo de cliente
	 * @param nombre nombre de cliente
	 * @param apellido apellido del cliente
	 * @param identificacion cédula del cliente
	 * @param apto número de partamento donde vive el cliente si es Residente o del apartamento al que se dirige si es visitante
	 * @param tel teefono del cliente
	 * @param cel celular del cliente
	 * @param mail correo electrónico del cliente
	 * @param vehi booleano para determinar si el cliente tiene vehículo(s) o no
	 * @param res persona responsable de recibir al cliente si es un Visitante
	 * @param fsalida fecha de expiración del permiso de acceso si el cliente es un Visitante
	 * @return String con mensaje de si cliente se agregó correctamente
	 * @throws IWDaoException
	 * @throws IWServiceException
	 */
	@Path("InsertarCliente")
	 @Produces(MediaType.TEXT_PLAIN)
	 @GET
	 public String insertaCliente(@QueryParam("ficho") Integer ficho, @QueryParam("tipo") String tipo,
	   @QueryParam("nombre") String nombre, @QueryParam("apellido") String apellido,
	   @QueryParam("identificacion") Long identificacion, @QueryParam("apto") String apto,
	   @QueryParam("tel") Long tel, @QueryParam("cel") Long cel, @QueryParam("mail") String mail,
	   @QueryParam("vehi") boolean vehi, @QueryParam("resp") String resp, @QueryParam("fsalida") String fsalida) 
	     throws RemoteException, IWServiceException, ParseException{
	  SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	  Date fsalida1 = null;
	  try {
	   fsalida1=formatoDelTexto.parse(fsalida + " 23:59:59");
	   clienteservice.guardarCliente(ficho, tipo, nombre, apellido, identificacion, apto, tel, cel, mail, vehi, resp, fsalida1);
	  } catch (IWDaoException e) {
	   return e.getMessage();
	  } catch (IWServiceException e) {
	   return e.getMessage();
	  } catch (ParseException ex) {
	      ex.printStackTrace();

	  }
	  return "Se guardó el cliente";
	 }
	/**
	 * Método para modificar un cliente
	 * @param ficho número del ficho asociado al cliente
	 * @param apto apartamento del cliente
	 * @param tel telefono del cliente
	 * @param cel celular del cliente
	 * @param mail correo electrónico del cliente
	 * @param vehi booleano para deteminar si tiene vehículo o no
	 * @param fsalida fecha de expiracion de permiso de acceso para cientes de tipo Visitante
	 * @return String con mensaje de si cliente se actualizó correctamente
	 * @throws IWDaoException
	 * @throws IWServiceException
	 */
	@Path("ActualizarCliente")
	@Produces(MediaType.TEXT_PLAIN)
	@PUT
	public String actualizarCliente(@QueryParam("ficho") Integer ficho, @QueryParam("apto") String apto,
			@QueryParam("tel") Long tel, @QueryParam("cel") Long cel, @QueryParam("mail") String mail,
			@QueryParam("vehi") Boolean vehi, @QueryParam("fsalida") Date fsalida) 
					throws RemoteException, IWServiceException{ 
		try {
			clienteservice.modificarCliente(ficho, apto, tel, cel, mail, vehi, fsalida);
		} catch (IWDaoException e) {
			return e.getMessage();
		} catch (IWServiceException e) {
			return e.getMessage();
		}
		return "Se actualizó correctamente.";
	}
	/**
	 * Método para eliminar un cliente
	 * @param ficho número de ficho asiciado al cliente que se va a eliminar
	 * @return String con mensaje de si cliente se eliminó correctamente
	 * @throws IWDaoException
	 * @throws IWServiceException
	 */
	@Path("EliminarCliente")
	@Produces(MediaType.TEXT_PLAIN)
	@PUT
	public String eliminarCliente(@QueryParam("ficho") Integer ficho) throws RemoteException, IWServiceException{ //List<ClienteWS>
		try {
			
			clienteservice.eliminarCliente(ficho);
			
		} catch (IWDaoException e) {
			return e.getMessage();
		} catch (IWServiceException e) {
			return e.getMessage();
		}
		return "Se eliminó correctamente";
	}
	
	/**
	 * Método para obtener un cliente por tipo
	 * @param tipo tipo de cliente: Residente o Visitante
	 * @return lista con los clientes de tipo Residente o tipo visitante
	 * @throws RemoteException
	 * @throws IWServiceException
	 */
	@Path("ObtenerByTipo")
	@Produces(MediaType.APPLICATION_XML)
	@GET
	public List<Cliente> obtenerByTipo(@QueryParam("tipo") String tipo) throws RemoteException, IWServiceException{ //List<ClienteWS>
		
		List<Cliente> listaClientes= null;
		try {
			listaClientes=clienteservice.obtenerPorTipo(tipo);
		} catch (IWDaoException e) {
			throw new RemoteException(e.getMessage());
		}catch (IWServiceException e) {
			throw new IWServiceException(e.getMessage());
		}
		return listaClientes;
	}
	
	@Path("Acceso")
	@Produces(MediaType.TEXT_PLAIN)
	@GET
	public String acceso(@QueryParam("ficho") Integer ficho) throws RemoteException, IWServiceException{ //List<ClienteWS>
		
		String acceso="El cliente no esta regitrado en el sistema"; 
		try {
			acceso=clienteservice.comprobarAcceso(ficho);
		} catch (IWDaoException e) {
			e.getMessage();
		}catch (IWServiceException e) {
			e.getMessage();
		}
		return acceso;
	}
}

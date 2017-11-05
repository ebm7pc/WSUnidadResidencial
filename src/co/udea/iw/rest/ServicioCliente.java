package co.udea.iw.rest;

import java.rmi.RemoteException;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import co.udea.iw.dto.Cliente;
import co.udea.iw.exception.IWDaoException;
import co.udea.iw.exception.IWServiceException;
import co.udea.iw.service.ClienteService;

@Path("Cliente")
@Component
public class ServicioCliente {
	
	@Autowired
	private ClienteService clienteservice;
	
	
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
	
	
	@Path("InsertarCliente")
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	public String insertaCliente(@QueryParam("ficho") Integer ficho, @QueryParam("tipo") String tipo,
			@QueryParam("nombre") String nombre, @QueryParam("apellido") String apellido,
			@QueryParam("identificacion") Long identificacion, @QueryParam("apto") String apto,
			@QueryParam("tel") Long tel, @QueryParam("cel") Long cel, @QueryParam("mail") String mail,
			@QueryParam("vehi") boolean vehi, @QueryParam("resp") String resp, @QueryParam("fsalida") Date fsalida) 
					throws RemoteException, IWServiceException{
		try {
			clienteservice.guardarCliente(ficho, tipo, nombre, apellido, identificacion, apto, tel, cel, mail, vehi, resp, fsalida);
		} catch (IWDaoException e) {
			return e.getMessage();
		} catch (IWServiceException e) {
			return e.getMessage();
		}
		return "Se guardó el cliente";
	}
	
	
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
	
}

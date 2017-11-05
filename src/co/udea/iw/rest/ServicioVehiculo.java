package co.udea.iw.rest;

import java.rmi.RemoteException;
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

import co.udea.iw.dto.Cliente;
import co.udea.iw.dto.Vehiculo;
import co.udea.iw.exception.IWDaoException;
import co.udea.iw.exception.IWServiceException;
import co.udea.iw.service.UsuarioService;
import co.udea.iw.service.VehiculoService;

@Path("Vehiculo")
@Component
public class ServicioVehiculo {

	@Autowired
	VehiculoService vehiculoservice;

	@Path("InsertarVehiculo")
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	public String insertaUsuario(@QueryParam("placa")String placa, @QueryParam("marca") String marca, @QueryParam("cliente") Integer cliente) 
					throws RemoteException, IWServiceException{ 
		try {
			vehiculoservice.guardarVehiculo(placa, marca, cliente);
		} catch (IWDaoException e) {
			return e.getMessage();
		} catch (IWServiceException e) {
			return e.getMessage();
		}
		return "Se guardó el vehiculo correctamente";
	}
	
	@Path("EliminarVehiculo")
	@Produces(MediaType.TEXT_PLAIN)
	@PUT
	public String eliminarUsuario(@QueryParam("placa") String placa) throws RemoteException, IWServiceException{ //List<ClienteWS>
		
		try {
			vehiculoservice.eliminarVehiculo(placa);
		} catch (IWDaoException e) {
			return e.getMessage();
		} catch (IWServiceException e) {
			return e.getMessage();
		}
		return "Vehiculo eliminado";
	}
	
	@Path("ObtenerByCliente")
	@Produces(MediaType.APPLICATION_XML)
	@GET
	public List<Vehiculo> obtenerVehiculos(@QueryParam("ficho") Integer ficho) throws RemoteException, IWServiceException{ //List<ClienteWS>
		
		List<Vehiculo> listaVehiculos= null;
		try {
			
			listaVehiculos=vehiculoservice.obtenerVehiculos(ficho);
			
		} catch (IWDaoException e) {
			throw new RemoteException(e.getMessage());
		} catch (IWServiceException e) {
			throw new IWServiceException(e.getMessage());
		}
		
		return listaVehiculos;
	}
	
	@Path("ObtenerClienteByPlaca")
	@Produces(MediaType.APPLICATION_XML)
	@GET
	public Cliente obtenerCliente(@QueryParam("placa") String placa) throws RemoteException, IWServiceException{ //List<ClienteWS>
		
		Cliente cliente= null;
		try {
			
			cliente=vehiculoservice.obtenerDueño(placa);
			
		} catch (IWDaoException e) {
			throw new RemoteException(e.getMessage());
		} catch (IWServiceException ex) {
			throw new IWServiceException(ex.getMessage());
		}
		
		return cliente;
	}
}

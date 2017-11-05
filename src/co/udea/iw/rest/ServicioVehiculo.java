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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.udea.iw.dto.Cliente;
import co.udea.iw.dto.Vehiculo;
import co.udea.iw.exception.IWDaoException;
import co.udea.iw.exception.IWServiceException;
import co.udea.iw.service.UsuarioService;
import co.udea.iw.service.VehiculoService;

/**
 * 
 * @author Eduardo Bedoya, Yesid Montoya
 *Clase para implementar los servicios web del m�dulo de Vehiculo
 */
@Path("Vehiculo")
@Component
public class ServicioVehiculo {
	/**
	 * Objeto de tipo Logger para generar los mensajes de eventos de errores y excepciones
	 */
	private Logger logger = Logger.getRootLogger();

	@Autowired
	VehiculoService vehiculoservice;

	/**
	 * M�todo para guardar un veh�culo
	 * @param placa placa del veh�culo
	 * @param marca marca del veh�culo
	 * @param cliente n�mero de ficho del cliente asociado a veh�culo
	 * @return String con mensaje de guardado correcto
	 * @throws IWDaoException
	 * @throws IWServiceException
	 */
	@Path("InsertarVehiculo")
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	public String insertaVehicullo(@QueryParam("placa")String placa, @QueryParam("marca") String marca, @QueryParam("cliente") Integer cliente) 
					throws RemoteException, IWServiceException{ 
		try {
			vehiculoservice.guardarVehiculo(placa, marca, cliente);
		} catch (IWDaoException e) {
			return e.getMessage();
		} catch (IWServiceException e) {
			return e.getMessage();
		}
		return "Se guard� el vehiculo correctamente";
	}
	/**
	 * M�todo para eliminar un veh�culo
	 * @param placa placa de veh�culo
	 * @return String con mensaje de borrado correcto
	 * @throws IWDaoException
	 * @throws IWServiceException
	 */
	@Path("EliminarVehiculo")
	@Produces(MediaType.TEXT_PLAIN)
	@PUT
	public String eliminarVehiculo(@QueryParam("placa") String placa) throws RemoteException, IWServiceException{ //List<ClienteWS>	
		try {
			vehiculoservice.eliminarVehiculo(placa);
		} catch (IWDaoException e) {
			return e.getMessage();
		} catch (IWServiceException e) {
			return e.getMessage();
		}
		return "Vehiculo eliminado";
	}
	/**
	 * M�todo para obtener la ista de veh�culos de un cliente
	 * @param ficho n�mero del ficho asociado al cliente
	 * @return lista de veh�culos asociados a un cliente
	 * @throws IWDaoException
	 * @throws IWServiceException
	 */
	@Path("ObtenerByCliente")
	@Produces(MediaType.APPLICATION_XML)
	@GET
	public List<Vehiculo> obtenerVehiculoByCliente(@QueryParam("ficho") Integer ficho) throws RemoteException, IWServiceException{ //List<ClienteWS>
		
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
	/**
	 * M�todo para obtener el cliente asociado a un veh�culo
	 * @param placa placa del veh�culo
	 * @return cliente asociado al veh�culo
	 * @throws IWDaoException
	 * @throws IWServiceException
	 */
	@Path("ObtenerClienteByPlaca")
	@Produces(MediaType.APPLICATION_XML)
	@GET
	public Cliente obtenerVehiculoByPlaca(@QueryParam("placa") String placa) throws RemoteException, IWServiceException{ //List<ClienteWS>
		Cliente cliente= null;
		try {	
			cliente=vehiculoservice.obtenerDue�o(placa);
		} catch (IWDaoException e) {
			throw new RemoteException(e.getMessage());
		} catch (IWServiceException ex) {
			throw new IWServiceException(ex.getMessage());
		}
		return cliente;
	}
}

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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import co.udea.iw.dto.Acceso;
import co.udea.iw.dto.Cliente;
import co.udea.iw.exception.IWDaoException;
import co.udea.iw.exception.IWServiceException;
import co.udea.iw.service.AccesoService;
import co.udea.iw.service.ClienteService;

/**
 * 
 * @author Eduardo Bedoya, Yesid Montoya
 *Clase para implementar los servicios web del m�dulo de accesos
 */
@Path("Acceso")
@Component
public class ServicioAcceso {
	/**
	 * Objeto de tipo Logger para generar los mensajes de eventos de errores y excepciones
	 */
	private Logger logger = Logger.getRootLogger();

	@Autowired
	private AccesoService accesoservice;
	/**
	 * M�todo para guardar un acceso
	 * @param id identificador del acceso
	 * @param idCliente n�mero de ficho de cliente que accedi�
	 * @return cadena de caract�res con mensaje de acceso
	 * @throws RemoteException
	 * @throws IWServiceException
	 */
	@Path("InsertarAcceso")
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	public String insertaAcceso(@QueryParam("id") Integer id, @QueryParam("idCliente") Integer idCliente)
					throws RemoteException, IWServiceException{
		try {
			accesoservice.guardarAcceso(id, idCliente);
		} catch (IWDaoException e) {
			return e.getMessage();
//			logger.getAppender(e.getMessage());
			//logger.error("no se guardo DAO" + e);
		} catch (IWServiceException e) {
			return e.getMessage();
			//logger.error("no se guardo SERVICE" + e);
		}
		return "Se guard� el acceso";
	}
	/**
	 * M�todo para obtener la lista de todos los accesos
	 * @return lista de todos los accesos
	 * @throws IWDaoException
	 * @throws IWServiceException
	 */
	@Path("ListaAccesos")
	@Produces(MediaType.APPLICATION_XML)
	@GET
	public List<Acceso> obtener() throws RemoteException, IWServiceException{ //List<ClienteWS>
		List<Acceso> listaAccesos= null;
		try {
			listaAccesos=accesoservice.obtener();	
		} catch (IWDaoException e) {
			throw new RemoteException(e.getMessage());
		}catch (IWServiceException e) {
			throw new IWServiceException(e.getMessage());
		}	
		return listaAccesos;
	}
	/**
	 * M�todo para para obtener una lista de accesos filtrada por tipo de cliente
	 * @param tipo tipo de cliente
	 * @return lista de accesos por tipo de cliente
	 * @throws IWDaoException
	 * @throws IWServiceException
	 */
	@Path("ObtenerByTipo")
	@Produces(MediaType.APPLICATION_XML)
	@GET
	public List<Acceso> obtenerByTipo(@QueryParam("tipo") String tipo) throws RemoteException, IWServiceException{ //List<ClienteWS>
		List<Acceso> listaAccesos= null;
		try {
			listaAccesos=accesoservice.obtenerPorTipoC(tipo);
		} catch (IWDaoException e) {
			throw new RemoteException(e.getMessage());
		}catch (IWServiceException e) {
			throw new IWServiceException(e.getMessage());
		}
		return listaAccesos;
	}
	/**
	 * M�todo para obtener la lista de accesos de un cliente determinado
	 * @param cliente n�mero de ficho del cliente
	 * @return lista de accesos de un cliente
	 * @throws IWDaoException
	 * @throws IWServiceException
	 */
	@Path("ObtenerByCliente")
	@Produces(MediaType.APPLICATION_XML)
	@GET
	public List<Acceso> obtenerByCliente(@QueryParam("cliente") Integer cliente) throws RemoteException, IWServiceException{ //List<ClienteWS>
		List<Acceso> listaAccesos= null;
		try {
			listaAccesos=accesoservice.obtenerPorCliente(cliente);
		} catch (IWDaoException e) {
			throw new RemoteException(e.getMessage());
		}catch (IWServiceException e) {
			throw new IWServiceException(e.getMessage());
		}
		return listaAccesos;
	}

}

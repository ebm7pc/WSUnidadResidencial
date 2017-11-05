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

@Path("Acceso")
@Component
public class ServicioAcceso {
	
	private Logger logger = Logger.getRootLogger();

	@Autowired
	private AccesoService accesoservice;
	
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
		return "Se guardó el acceso";
	}
	
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

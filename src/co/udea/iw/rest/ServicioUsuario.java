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
import co.udea.iw.dto.Usuario;
import co.udea.iw.exception.IWDaoException;
import co.udea.iw.exception.IWServiceException;
import co.udea.iw.service.ClienteService;
import co.udea.iw.service.UsuarioService;


@Path("Usuario")
@Component
public class ServicioUsuario {
	
	@Autowired
	private UsuarioService usuarioservice;
	
	@Path("ObtenerUsuario")
	@Produces(MediaType.APPLICATION_XML)
	@GET
	public Usuario obtenerUsuario(@QueryParam("nombre") String nombre) throws RemoteException, IWServiceException{ //List<ClienteWS>
		Usuario usuario = null;
		try {
			usuario=usuarioservice.obtenerUsuario(nombre);
		} catch (IWDaoException e) {
			throw new RemoteException(e.getMessage());
		} catch (IWServiceException e) {
			throw new IWServiceException(e.getMessage());
		}
		return usuario;
	}
	
	@Path("InsertarUsuario")
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	public String insertaUsuario(@QueryParam("nombre") String nombre, @QueryParam("pwd") String pwd, @QueryParam("tipo") String tipo) 
					throws RemoteException, IWServiceException{ 
		try {
			usuarioservice.guardarUsuario(nombre, pwd, tipo);
		} catch (IWDaoException e) {
			return e.getMessage();
		} catch (IWServiceException e) {
			return e.getMessage();
		}
		return "Se guardó";
	}
	
	@Path("ActualizarUsuario")
	@Produces(MediaType.TEXT_PLAIN)
	@PUT
	public String actualizarUsuario(@QueryParam("nombre") String nombre, @QueryParam("pwd") String pwd) 
					throws RemoteException, IWServiceException{ 
		try {
			usuarioservice.modificarUsuario(nombre, pwd);
		} catch (IWDaoException e) {
			return e.getMessage();
		} catch (IWServiceException e) {
			return e.getMessage();
		}
		return "Se actualizo el usuario";
	}
	
	@Path("EliminarUsuario")
	@Produces(MediaType.TEXT_PLAIN)
	@PUT
	public String eliminarUsuario(@QueryParam("nombre") String nombre) throws RemoteException, IWServiceException{ //List<ClienteWS>
		
		try {
			usuarioservice.eliminarUsuario(nombre);
		} catch (IWDaoException e) {
			return e.getMessage();
		} catch (IWServiceException e) {
			return e.getMessage();
		}
		return "Usuario eliminado";
	}
	
	@Path("LoginUsuario")
	@Produces(MediaType.TEXT_PLAIN)
	@GET
	public String loginUsuario(@QueryParam("nombre") String nombre, @QueryParam("pwd") String pwd) 
					throws RemoteException, IWServiceException{ 
		
		String loguin=null;
		try {
			loguin= usuarioservice.iniciarSeccion(nombre, pwd);
		} catch (IWDaoException e) {
			return e.getMessage();
		} catch (IWServiceException e) {
			return e.getMessage();
		}
		return loguin;
	}
}

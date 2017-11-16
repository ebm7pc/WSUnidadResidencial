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

import co.udea.iw.dto.Cliente;
import co.udea.iw.dto.Usuario;
import co.udea.iw.exception.IWDaoException;
import co.udea.iw.exception.IWServiceException;
import co.udea.iw.service.ClienteService;
import co.udea.iw.service.UsuarioService;

/**
 * 
 * @author Eduardo Bedoya, Yesid Montoya
 *Clase para implementar los servicios web del módulo de Usuario
 */
@Path("Usuario")
@Component
public class ServicioUsuario {
	/**
	 * Objeto de tipo Logger para generar los mensajes de eventos de errores y excepciones
	 */
	private Logger logger = Logger.getRootLogger();
	@Autowired
	private UsuarioService usuarioservice;
	/**
     * Método para obtenerun usuario por su nombre
     * @param nombre nombre del usuario
     * @return objeto de tipo Usuario
     * @throws IWDaoException
     * @throws IWServiceException
     */
	@Path("ObtenerUsuario")
	@Produces(MediaType.APPLICATION_XML)
	@GET
	public Usuario obtenerUsuario(@QueryParam("nombre") String nombre) throws IWDaoException, RemoteException, IWServiceException{ //List<ClienteWS>
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
	/**
	 * Método para guardar un usuario
	 * @param nombre nombre del usuario
	 * @param pwd contraseña del usuario
	 * @param tipo tipo de usuario: Administrador o Regular
	 * @return String con mensaje de si usuario se guardó
	 * @throws IWDaoException
	 * @throws IWServiceException
	 */
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
	/**
	 * Método para modificar a contraseña de un usuario
	 * @param nombre nombre de usuario
	 * @param pwd contraseña nueva
	 * @return String con mensaje de actualización correcta
	 * @throws IWDaoException
	 * @throws IWServiceException
	 */
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
	/**
	 * Método para eliminar un usuario
	 * @param nombre nombre del usuario a eliminar
	 * @return String con mensaje de si usuario fue eliminado
	 * @throws IWDaoException
	 * @throws IWServiceException
	 */
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
	/**
	 * Método para verificar un inicio de sesión correcto o no
	 * @param nombre nombre del usuario que inicia sesión
	 * @param pwd contraseña del usuario que inicia sesión
	 * @return cadena de caractéres con mensaje si inició sesión correctamente o no
	 * @throws IWDaoException
	 * @throws IWServiceException
	 */
	@Path("LoginUsuario")
	@Produces(MediaType.TEXT_PLAIN)
	@GET
	public String loginUsuario(@QueryParam("nombre") String nombre, @QueryParam("pwd") String pwd) 
					throws RemoteException, IWServiceException{ 
		
		String login= "Falló el Inicio de Sesión, Compruebe sus Datos";
		try {
			login= usuarioservice.iniciarSeccion(nombre, pwd);
		} catch (IWDaoException e) {
			e.getMessage();
		} catch (IWServiceException e) {
			e.getMessage();
		}
		return login;
	}
}

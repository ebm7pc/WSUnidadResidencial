package co.udea.iw.rest;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.udea.iw.dto.Cliente;
import co.udea.iw.dto.ClienteWS;
import co.udea.iw.exception.IWDaoException;
import co.udea.iw.service.ClienteService;

@Path("Cliente")
@Component
public class ServicioCliente {
	
	@Autowired
	private ClienteService clienteservice;
	
	
	@Produces(MediaType.APPLICATION_XML)
	@GET
	public List<ClienteWS> obtener() throws RemoteException{ //List<ClienteWS>
		
		List<ClienteWS> clientes= new ArrayList<ClienteWS>();
		
		List<Cliente> listaClientes= null;
		try {
			
			listaClientes=clienteservice.obtener(); 
			
			for(Cliente cliente : listaClientes) {
				
				ClienteWS clienteWS= new ClienteWS();
				
				clienteWS.setCedula(cliente.getIdentificacion());
				clienteWS.setApellidos(cliente.getApellido());
				clienteWS.setNombres(cliente.getNombre());
				
				clientes.add(clienteWS);
				System.out.println("agrego el cliente ");
			}
			
		} catch (IWDaoException e) {
			throw new RemoteException(e.getMessage());
		}
		
		return clientes;
	}

}

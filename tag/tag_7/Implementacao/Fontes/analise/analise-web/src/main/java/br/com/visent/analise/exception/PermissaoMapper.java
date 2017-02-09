package br.com.visent.analise.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.visent.analise.util.Messages;

@Provider
public class PermissaoMapper implements ExceptionMapper<PermissaoException> {

	@Override
	public Response toResponse(PermissaoException exception) {
		String message = exception.getMessage();
		if (exception.isMessageKey()) {
			message = Messages.getString(message);
		}
		return Response.status(Status.FORBIDDEN).entity(message).build();
	}

}

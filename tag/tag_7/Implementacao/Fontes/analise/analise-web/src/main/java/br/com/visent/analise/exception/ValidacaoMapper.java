package br.com.visent.analise.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.visent.analise.util.Messages;

@Provider
public class ValidacaoMapper implements ExceptionMapper<ValidacaoException> {

	@Override
	public Response toResponse(ValidacaoException exception) {
		String message = exception.getMessage();
		if (exception.isMessageKey()) {
			message = Messages.getString(message);
		}
		return Response.status(Status.BAD_REQUEST).entity(message).build();
	}

}

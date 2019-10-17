package py.com.global.spm.model.services.rest;

import com.fasterxml.jackson.core.JsonParseException;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomExceptionMapper implements ExceptionMapper<JsonParseException> {

    @Override
    public Response toResponse(final JsonParseException jpe)
    {   JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObjectBuilder builder = factory.createObjectBuilder();
        JsonObject responseBody = builder.add("status","01").build();
        builder.add("descripcion","Json Parse Error").build();
        return Response
                .status(Response.Status.NOT_FOUND).entity(responseBody)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}


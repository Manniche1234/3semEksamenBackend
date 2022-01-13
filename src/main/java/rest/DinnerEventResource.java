package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.DinnerEventDTO;
import facades.DinnerFacade;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/event")
public class DinnerEventResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    @Context
    private UriInfo context;
    private DinnerFacade facade = DinnerFacade.getDinnerFacade(EMF);
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    @Context
    SecurityContext securityContext;


    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllEvents() {
        List<DinnerEventDTO> dinnerEventDTOList = facade.getAllEvents();

        return gson.toJson(dinnerEventDTOList);
    }
}
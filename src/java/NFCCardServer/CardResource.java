/*
 * JAX_RS RESTful resource class for various (GET, POST, PUT, DELETE) requests on NFC Cards
 */
package NFCCardServer;

import java.util.Collection;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Trisha Tan 
 */

@Named
@Path("/NFCCards")
public class CardResource {
    @EJB
    private CardBean cBean; 
    @Context
    private UriInfo context;
    private static final char QUOTE = '\"'; 
    
    //Creates a new instance of CardResource
    public CardResource()
    {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getAllCards()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        buffer.append("<NFCCards uri=").append(QUOTE).append(
            context.getAbsolutePath()).append(QUOTE).append(">");
        Collection<NFCCard> allCards = cBean.getCards(); 
        for (NFCCard card : allCards)
        {
            buffer.append(card.getXMLString());
        }
        buffer.append("</NFCCards>");
        return buffer.toString();
    }
    
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("{cardName}/{cardNumber}")
    public void addNewCard(@PathParam("cardName") String cardName,
        @PathParam("cardNumber") String cardNumber)
    {
        cBean.addCard(cardName, cardNumber);
    }
    //improve pathname when theres time, change parameter from add
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{cardNumber}")
    public void removeCard(@PathParam("cardName") String cardName,
        @PathParam("cardNumber") String cardNumber)
    {
        cBean.removeCard(cardName, cardNumber); 
    }
}

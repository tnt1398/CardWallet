/*
 * JAX-RS class overridden to specifify the
 * application path for RESTful services
 */
package NFCCardServer;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Trisha Tan
 */

@javax.ws.rs.ApplicationPath("NFCCardServer")
public class ApplicationConfig extends Application {
    
    @Override
   public Set<Class<?>> getClasses()
   {
      Set<Class<?>> resources = new java.util.HashSet<>();
      addRestResourceClasses(resources);
      return resources;
   }
    
   private void addRestResourceClasses(Set<Class<?>> resources)
   {
      resources.add(NFCCardServer.CardResource.class);
   } 
}

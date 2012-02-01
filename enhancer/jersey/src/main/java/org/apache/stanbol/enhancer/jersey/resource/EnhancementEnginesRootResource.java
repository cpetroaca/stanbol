package org.apache.stanbol.enhancer.jersey.resource;

import static javax.ws.rs.core.MediaType.TEXT_HTML;
import static org.apache.stanbol.commons.web.base.CorsHelper.addCORSOrigin;
import static org.apache.stanbol.commons.web.base.CorsHelper.enableCORS;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.clerezza.rdf.core.access.TcManager;
import org.apache.clerezza.rdf.core.serializedform.Serializer;
import org.apache.stanbol.commons.web.base.ContextHelper;
import org.apache.stanbol.commons.web.base.resource.BaseStanbolResource;
import org.apache.stanbol.enhancer.servicesapi.Chain;
import org.apache.stanbol.enhancer.servicesapi.ChainManager;
import org.apache.stanbol.enhancer.servicesapi.EnhancementEngine;
import org.apache.stanbol.enhancer.servicesapi.EnhancementEngineManager;
import org.apache.stanbol.enhancer.servicesapi.EnhancementJobManager;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;

import com.sun.jersey.api.view.Viewable;

@Path("/enhancer/engine")
public class EnhancementEnginesRootResource extends BaseStanbolResource {

    
    private final Map<String, Entry<ServiceReference,EnhancementEngine>> engines;

    public EnhancementEnginesRootResource(@Context ServletContext context) {
        // bind the job manager by looking it up from the servlet request context
        EnhancementEngineManager engineManager = ContextHelper.getServiceFromContext(EnhancementEngineManager.class, context);
        if(engineManager == null){
            throw new WebApplicationException(new IllegalStateException(
                "The required EnhancementEngineManager Service is not available!"));
        }
        engines = new HashMap<String,Map.Entry<ServiceReference,EnhancementEngine>>();
        for(String chainName : engineManager.getActiveEngineNames()){
            ServiceReference engineRef = engineManager.getReference(chainName);
            if(engineRef != null){
                EnhancementEngine engine = engineManager.getEngine(engineRef);
                if(engine != null){
                    Map<ServiceReference,EnhancementEngine> m = Collections.singletonMap(engineRef, engine);
                    engines.put(chainName, m.entrySet().iterator().next());
                }
            }
        }
    }
    @OPTIONS
    public Response handleCorsPreflight(@Context HttpHeaders headers){
        ResponseBuilder res = Response.ok();
        enableCORS(servletContext,res, headers);
        return res.build();
    }

    @GET
    @Produces(TEXT_HTML)
    public Response get(@Context HttpHeaders headers) {
        ResponseBuilder res = Response.ok(new Viewable("index", this),TEXT_HTML);
        addCORSOrigin(servletContext,res, headers);
        return res.build();
    }

    public Collection<EnhancementEngine> getEngines(){
        Set<EnhancementEngine> engines = new HashSet<EnhancementEngine>();
        for(Entry<ServiceReference,EnhancementEngine> entry : this.engines.values()){
            engines.add(entry.getValue());
        }
        return engines;
    }
    public String getServicePid(String name){
        Entry<ServiceReference,EnhancementEngine> entry = engines.get(name);
        if(entry != null){
            return (String)entry.getKey().getProperty(Constants.SERVICE_PID);
        } else {
            return null;
        }
    }
    public Integer getServiceRanking(String name){
        Entry<ServiceReference,EnhancementEngine> entry = engines.get(name);
        Integer ranking = null;
        if(entry != null){
            ranking = (Integer)entry.getKey().getProperty(Constants.SERVICE_RANKING);
        }
        if(ranking == null){
            return new Integer(0);
        } else {
            return ranking;
        }
    }
    public Long getServiceId(String name){
        Entry<ServiceReference,EnhancementEngine> entry = engines.get(name);
        if(entry != null){
            return (Long)entry.getKey().getProperty(Constants.SERVICE_ID);
        } else {
            return null;
        }
    }
}

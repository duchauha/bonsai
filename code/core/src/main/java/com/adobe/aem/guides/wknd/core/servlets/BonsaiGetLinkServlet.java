package com.adobe.aem.guides.wknd.core.servlets;


import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet that get the final links
 *  based on role and option dropdown value
 */
@Component(service = { Servlet.class })
@SlingServletResourceTypes(
        resourceTypes=BonsaiGetLinkServlet.RESOURCE_TYPE,
        methods=HttpConstants.METHOD_GET,
        extensions="txt")
@ServiceDescription("Simple Demo Servlet")
public class BonsaiGetLinkServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;
    protected static final String RESOURCE_TYPE = "wknd/components/bonsai";
    private static final Logger LOG = LoggerFactory.getLogger(BonsaiGetLinkServlet.class);

    @Override
    protected void doGet(final SlingHttpServletRequest req,
            final SlingHttpServletResponse resp) throws ServletException, IOException {
        
    	 String bonsaiRole = req.getParameter("bonsaiRole");
         String bonsaiOption = req.getParameter("bonsaiOption");
         String link = StringUtils.EMPTY;
         if(StringUtils.isNotEmpty(bonsaiRole) && StringUtils.isNotEmpty(bonsaiOption)) {
        	 Resource currentResource  = req.getResource();
             Map<String, String> bonsaiMap = getBonsaiConfigMap(currentResource);
             String key = bonsaiRole + bonsaiOption;
             link = bonsaiMap.get(key);
             
         }
         
         resp.setContentType("text/plain");
         resp.getWriter().write(link);
         resp.setStatus(200);
    	  
    }
    
    private Map<String, String> getBonsaiConfigMap(Resource currentResource){
    	Map<String, String> bonsaiMap = null;
    	 try {
             Resource bonsaimapping=currentResource.getChild("bonsaimapping");
             if(bonsaimapping!=null){
            	 bonsaiMap =  new HashMap<String, String>();
                 for (Resource item : bonsaimapping.getChildren()) {
                    
                	 bonsaiMap.put(item.getValueMap().get("bonsairole",String.class)+item.getValueMap().get("bonsaioption",String.class),item.getValueMap().get("link",String.class));
                     
                 }
             }
         }catch (Exception e){
             LOG.info("\n ERROR while getting Book Details {} ",e.getMessage());
         }
    	 
    	 
         LOG.info("\n bonsai SIZE {} ",bonsaiMap);
         
    	return bonsaiMap;
    }
}

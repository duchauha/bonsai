/*
 *  Copyright 2019 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.adobe.aem.guides.wknd.core.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;



/**
 * Represents the Bonsai AEM Component for the WKND Site project.
 **/

@Model(adaptables = Resource.class, resourceType = BonsaiModel.RESOURCE_TYPE, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BonsaiModel {
	
	protected static final String RESOURCE_TYPE = "wknd/components/bonsai";
	
	protected static final String ROLE_LIST = "/etc/acs-commons/lists/bonsairole/jcr:content/list";
	
	protected static final String OPTION_LIST = "/etc/acs-commons/lists/bonsaioption/jcr:content/list";
	
	@SlingObject
    private ResourceResolver resourceResolver;
	
    private Map<String, String> roleMap;
    
    private Map<String, String> optionMap;
    
    //private Map<String, String> bonsaiMap;
    
    @PostConstruct
    protected void init() {
    	roleMap = getMapOfGenericList(ROLE_LIST);
    	optionMap = getMapOfGenericList(OPTION_LIST);
		
	}
    
    private Map<String, String> getMapOfGenericList(String listPath){
    	Map<String, String> listMap = null;
    	@Nullable
		Resource resource = resourceResolver.getResource(listPath);
    	if (Objects.nonNull(resource)) {
    		listMap = new HashMap<String, String>();
    		@NotNull
			Iterable<Resource> children = resource.getChildren();
    		for (Resource childResource : children) {
    			
				String title = childResource.getValueMap().get("jcr:title", String.class);
				String nodevalue = childResource.getValueMap().get("value", String.class);
				listMap.put(nodevalue, title);
				
			}
    		
    		
			
		}
    	return listMap;
    }
    
	public Map<String, String> getRoleMap() {
		return roleMap;
	}
	
	public Map<String, String> getOptionMap() {
		return optionMap;
	}
	
	/*
	 * public Map<String, String> getBonsaiMap() { return roleMap; }
	 * 
	 * public String getMappedLink() { return ""; }
	 */
	
	
	
}
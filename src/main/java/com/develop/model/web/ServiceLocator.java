package com.develop.model.web;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.develop.model.web.spring.DefaultServiceLocator;

public class ServiceLocator extends DefaultServiceLocator { 
	  private static Logger logger = Logger.getLogger(ServiceLocator.class);;
	    /** The context. */
	    private static ApplicationContext context;

	    static {
	        try {
	            context = getApplicationContext(ServiceLocator.class);
	        } catch (Exception e) {
	            logger.error("init spring context error!", e);
	        }
	    }

	    /**
	     * Gets the application context.
	     * 
	     * @return the application context
	     */
	    public static ApplicationContext getApplicationContext() {
	        if (context == null) {
	            throw new RuntimeException("Spring loading error!");
	        }
	        return context;
	    }
}

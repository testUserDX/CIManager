
package com.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;


public class Initializer /*implements WebApplicationInitializer*/ extends AbstractAnnotationConfigDispatcherServletInitializer {
//    private static final String DISPATCHER_SERVLET_NAME="dispatcher";

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { WebAppConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;

    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
//
//    @Override
//    protected Filter[] getServletFilters() {
//        return new Filter[] { new DelegatingFilterProxy("springSecurityFilterChain") };
//    }

//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
//        ctx.register(WebAppConfig.class);
//        servletContext.addListener(new ContextLoaderListener(ctx));
//
//        ctx.setServletContext(servletContext);
//        Dynamic servlet = servletContext.addServlet(DISPATCHER_SERVLET_NAME, new DispatcherServlet(ctx) );
//
//        servlet.addMapping("/");
//        servlet.setLoadOnStartup(1);
//    }

//    public void onStartup(ServletContext servletContext) throws ServletException {
//
//
//        // Create the 'root' Spring application context
//        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
//        rootContext.register(WebAppConfig.class);
//        servletContext.addListener(new ContextLoaderListener(rootContext));
//
//
//        // security filter
//        servletContext.addFilter(
//                "springSecurityFilterChain",
//                new DelegatingFilterProxy("springSecurityFilterChain"))
//                .addMappingForUrlPatterns(null, false, "/*");
//
//        // Manage the lifecycle of the root application context
//        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
//        webContext.register(WebConfig.class);
//        webContext.setServletContext(servletContext);
//
//
//        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(webContext));
//        servlet.addMapping("/");
//        servlet.setLoadOnStartup(1);
//    }
}

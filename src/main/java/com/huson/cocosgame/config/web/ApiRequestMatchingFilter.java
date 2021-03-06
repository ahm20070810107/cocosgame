package com.huson.cocosgame.config.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.util.Strings;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.huson.cocosgame.core.BMDataContext;
import com.huson.cocosgame.util.cache.CacheHelper;

public class ApiRequestMatchingFilter implements Filter {
    private RequestMatcher[] ignoredRequests;
    private RequestMatcher[] exRequests;

    public ApiRequestMatchingFilter(RequestMatcher[] exRequests , RequestMatcher... matcher) {
    	this.exRequests = exRequests ;
        this.ignoredRequests = matcher;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
         HttpServletRequest request = (HttpServletRequest) req;
         boolean matchAnyRoles = false ;
         for(RequestMatcher anyRequest : ignoredRequests ){
        	 if(anyRequest.matches(request)){
        		 matchAnyRoles = true ;
        	 }
         }
         if(exRequests!=null){
	         for(RequestMatcher anyRequest : exRequests ){
	        	 if(anyRequest.matches(request)){
	        		 matchAnyRoles = false ;
	        	 }
	         }
         }
         String authorization = request.getHeader("authorization") ;
         if(matchAnyRoles){
        	 if(!Strings.isBlank(authorization) && CacheHelper.getApiUserCacheBean().getCacheObject(authorization, BMDataContext.SYSTEM_ORGI) != null){
        		 chain.doFilter(req,resp);
        	 }else{
        		 HttpServletResponse response = (HttpServletResponse) resp ;
	        	 response.sendRedirect("/tokens");
        	 }
         }else{
        	 chain.doFilter(req,resp);
         }
    }

	@Override
	public void destroy() {
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
}
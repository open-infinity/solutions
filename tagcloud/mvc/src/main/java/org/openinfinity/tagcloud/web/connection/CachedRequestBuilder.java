package org.openinfinity.tagcloud.web.connection;

/**
 * 
 * @author Kavan Soleimanbeigi
 */

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class CachedRequestBuilder {

	CachedRequest creq = new CachedRequest();

	public CachedRequestBuilder setParameterMap(Map<String, String> parameterMap) {

		this.creq.setParameterMap(parameterMap);
		return this;
	}

	public CachedRequestBuilder setHeaderMap(Map<String, String> headerMap) {

		this.creq.setHeaderMap(headerMap);
		return this;
	}

	public CachedRequestBuilder setAttributeMap(Map<String, Object> attributeMap) {

		this.creq.setAttributeMap(attributeMap);
		return this;
	}

	public CachedRequestBuilder setMethod(String method) {

		this.creq.setMethod(method);
		return this;
	}
	public CachedRequestBuilder setPathInfo(String pathInfo) {

		this.creq.setPathInfo(pathInfo);
		return this;
	}
	
	public CachedRequestBuilder setPathTranslated(String pathTranslated) {

		this.creq.setPathTranslated(pathTranslated);
		return this;
	}
	
	public CachedRequestBuilder setQuerryString(String querryString) {

		this.creq.setQuerryString(querryString);
		return this;
	}
	public CachedRequestBuilder setRequestURI(String requestURI) {

		this.creq.setRequestURI(requestURI);
		return this;
	}
	
	public CachedRequestBuilder setRequestURL(String requestURL) {

		this.creq.setRequestURL(requestURL);
		return this;
	}
	public CachedRequestBuilder setRequestedSessionId(String requestedSessionId) {

		this.creq.setRequestedSessionId(requestedSessionId);
		return this;
	}
	
	public CachedRequestBuilder setServletPath(String servletPath) {
		this.creq.setServletPath(servletPath);
		return this;
	}
	
	public CachedRequestBuilder setContextPath(String contextPath) {
		this.creq.setContextPath(contextPath);
		return this;
	}
	
	public CachedRequest build(){
		return this.creq;
	}
	
	@SuppressWarnings("unchecked")
	public CachedRequest buildCachedRequestFromRequest(HttpServletRequest req) {
		if(req != null){
		return this.setParameterMap(req.getParameterMap()).setHeaderMap(this.getRequestHeaderMap(req)).setAttributeMap(this.getRequestAttributeMap(req))
				.setMethod(req.getMethod()).setPathInfo(req.getPathInfo()).setPathTranslated(req.getPathTranslated())
				.setQuerryString(req.getQueryString()).setRequestURI(req.getRequestURI()).setRequestURL(req.getRequestURL().toString())
				.setRequestedSessionId(req.getRequestedSessionId()).setServletPath(req.getServletPath()).setContextPath(req.getContextPath())
				.build();
		}
		return null;
		
	}
	

	@SuppressWarnings("unchecked")
	public Map<String, String> getRequestHeaderMap(HttpServletRequest req) {
		Map<String, String> hmap = new HashMap<String, String>();
		Enumeration<String> hnames = req.getHeaderNames();
		while (hnames.hasMoreElements()) {
			String hname = hnames.nextElement();
			hmap.put(hname, req.getHeader(hname));
		}
		return hmap;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getRequestAttributeMap(HttpServletRequest req) {
		Map<String, Object> omap = new HashMap<String, Object>();
		Enumeration<String> onames = req.getAttributeNames();
		while (onames.hasMoreElements()) {
			String oname = onames.nextElement();
			omap.put(oname, req.getAttribute(oname));
		}
		return omap;
	}

}

package org.openinfinity.tagcloud.web.connection.entity;

/**
 *
 *
 * @author Kavan Soleimanbeigi
 */

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequestWrapper;

public class CachedRequest{

	private Map<String, String[]> parameterMap;
	private Map<String, String> headerMap;
	private Map<String, Object> attributeMap;
	private String pathInfo;
	private String pathTranslated;
	private String querryString;
	private String requestedSessionId;
	private String requestURI;
	private String requestURL;
	private String servletPath;
	private String method;
	private String contextPath;

	public CachedRequest() {

	}

	public Map<String, String[]> getParameterMap() {
		return parameterMap;
	}

	public void setParameterMap(Map<String, String[]> parameterMap) {
		this.parameterMap = parameterMap;
	}

	public String getParameter(String key) {

		Set<String> keys = this.parameterMap.keySet();
		for (String s : keys) {
			if (s.equalsIgnoreCase(key) && this.parameterMap.get(key) != null) {
				return this.parameterMap.get(key)[0];
			}
		}

		return null;
	}

	public String[] getParameterValues(String key) {

		Set<String> keys = this.parameterMap.keySet();
		for (String s : keys) {
			if (s.equalsIgnoreCase(key) && this.parameterMap.get(key) != null) {
				return this.parameterMap.get(key);
			}
		}

		return null;
	}

	public boolean setParameter(String key, String value) {

		String[] valueAr = { value };
		if (this.parameterMap.containsKey(key)) {
			return false;
		} else {
			this.parameterMap.put(key, valueAr);
			return true;
		}

	}

	public boolean setParameterValues(String key, String[] values) {

		if (this.parameterMap.containsKey(key)) {
			return false;
		} else {
			this.parameterMap.put(key, values);
			return true;
		}

	}

	public Map<String, String> getHeaderMap() {
		return headerMap;
	}

	public void setHeaderMap(Map<String, String> headerMap) {
		this.headerMap = headerMap;
	}

	public String getHeader(String key) {

		Set<String> keys = this.headerMap.keySet();
		for (String s : keys) {
			if (s.equalsIgnoreCase(key)) {
				return this.headerMap.get(key);
			}
		}

		return null;
	}

	public boolean setHeader(String key, String value) {

		if (this.headerMap.containsKey(key)) {
			return false;
		} else {
			this.headerMap.put(key, value);
			return true;
		}

	}

	public Map<String, Object> getAttributeMap() {
		return attributeMap;
	}

	public void setAttributeMap(Map<String, Object> attributeMap) {
		this.attributeMap = attributeMap;
	}

	public Object getAttribute(String key) {

		Set<String> keys = this.attributeMap.keySet();
		for (String s : keys) {
			if (s.equalsIgnoreCase(key)) {
				return this.attributeMap.get(key);
			}
		}

		return null;
	}

	public boolean setAttribute(String key, String value) {

		if (this.attributeMap.containsKey(key)) {
			return false;
		} else {
			this.attributeMap.put(key, value);
			return true;
		}

	}

	public String getPathInfo() {
		return pathInfo;
	}

	public void setPathInfo(String pathInfo) {
		this.pathInfo = pathInfo;
	}

	public String getPathTranslated() {
		return pathTranslated;
	}

	public void setPathTranslated(String pathTranslated) {
		this.pathTranslated = pathTranslated;
	}

	public String getQuerryString() {
		return querryString;
	}

	public void setQuerryString(String querryString) {
		this.querryString = querryString;
	}

	public String getRequestedSessionId() {
		return requestedSessionId;
	}

	public void setRequestedSessionId(String requestedSessionId) {
		this.requestedSessionId = requestedSessionId;
	}

	public String getRequestURI() {
		return requestURI;
	}

	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}

	public String getRequestURL() {
		return requestURL;
	}

	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}

	public String getServletPath() {
		return servletPath;
	}

	public void setServletPath(String servletPath) {
		this.servletPath = servletPath;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	@Override
	public String toString() {
		return "CachedRequest [parameterMap=" + parameterMap + ", headerMap="
				+ headerMap + ", attributeMap=" + attributeMap + ", pathInfo="
				+ pathInfo + ", pathTranslated=" + pathTranslated
				+ ", querryString=" + querryString + ", requestedSessionId="
				+ requestedSessionId + ", requestURI=" + requestURI
				+ ", requestURL=" + requestURL + ", servletPath=" + servletPath
				+ ", method=" + method + ", contextPath=" + contextPath + "]";
	}
}

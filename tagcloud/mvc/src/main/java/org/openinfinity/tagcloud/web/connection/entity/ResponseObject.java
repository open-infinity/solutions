package org.openinfinity.tagcloud.web.connection.entity;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Kavan Soleimanbeigi
 * 
 * 
 */
public class ResponseObject<T> {

	private boolean is_error;
	private String status;
	private String message;
	private String error_code;
	private List<String> error_reasons;
	private List<T> result_list;

	public ResponseObject() {
		init();
	}

	private void init() {
		error_reasons = new LinkedList<String>();
		result_list = new LinkedList<T>();
	}

	public void setSuccess() {
		this.initWithSuccess(null, null);
	}

	public void setSuccess(List<T> results) {

		this.initWithSuccess(null, results);
	}
	public void setSuccess(T result) {
		this.result_list.add(result);
		this.initWithSuccess(null, null);
	}
	public void setSuccess(String message) {
		this.initWithSuccess(message, null);
	}

	public void setSuccess(String message, List<T> results) {
		this.initWithSuccess(message, results);
	}

	public void setSuccess(String message, T result) {
		this.result_list.add(result);
		this.initWithSuccess(message, null);
	}	

	public void addErrorReason(String reason) {
		this.error_reasons.add(reason);
	}

	public void addResultObject(T result) {
		this.result_list.add(result);
	}

	public List<T> getResult_list() {
		return result_list;
	}

	public void setResult_list(List<T> result_list) {
		this.result_list = result_list;
	}

	public boolean isIs_error() {
		return is_error;
	}

	public void setIs_error(boolean is_error) {
		this.is_error = is_error;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public List<String> getError_reasons() {
		return error_reasons;
	}

	public void setError_reasons(List<String> error_reasons) {
		this.error_reasons = error_reasons;
	}
	public List<T> convertToList(Collection<T> col){
		List<T> results = new LinkedList<T>();
		Iterator<T> iter = col.iterator();
		while(iter.hasNext()){
			results.add(iter.next());
		}
		return results;
	}
	private void initWithSuccess(String msg, List<T> results) {
		this.status = "200";
		this.is_error = false;
		this.message = "success";
		if (msg != null) {
			this.message = msg;
		}
		if (results != null) {
			this.result_list = results;
		}
	}

}

package akdmEtkinlikEnvanter.core.utilities.result;

public class Result {

	boolean success;
	String message;
	
	public Result(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	public Result(boolean success) {
		super();
		this.success = success;
	}
	
	public boolean isSuccess() {
		return this.success;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}

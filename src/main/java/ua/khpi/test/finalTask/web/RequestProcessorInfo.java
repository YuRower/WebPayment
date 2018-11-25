package ua.khpi.test.finalTask.web;


public class RequestProcessorInfo {
	public enum ProcessorMode{
		REDIRECT, FORWARD;
	}
	
	private ProcessorMode processorMode;
	private String path;
	
	public RequestProcessorInfo(ProcessorMode processorMode, String path) {
		this.processorMode = processorMode;
		this.path = path;
	}

	public ProcessorMode getProcessorMode() {
		return processorMode;
	}

	public String getPath() {
		return path;
	}
}

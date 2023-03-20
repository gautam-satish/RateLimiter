package pojo;

import java.time.LocalTime;

import utils.AppProperty;

public class Api {
	private static final String PROP_MAXCALLS = AppProperty.getValue("default.maxCalls") ;
	private static final String PROP_STARTTIME = AppProperty.getValue("default.startTime");
	private static final String PROP_ENDTIME = AppProperty.getValue("default.endTime");
	private String name;
	private int availableCalls;
	private int callsMade;
	private LocalTime startTime;
	private LocalTime endTime;

	public Api(String name, int availableCalls, LocalTime startTime, LocalTime endTime) {
		super();
		this.name = name;
		this.availableCalls = availableCalls;
		this.callsMade = 0;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public Api(String name, int availableCalls) {
		super();
		this.name = name;
		this.availableCalls = availableCalls;
		this.callsMade = 0;
		this.startTime = (LocalTime.of(00, 0));
		this.endTime = (LocalTime.of(23, 59));
	}

	// default API properties are set here
	public Api(String name) {
		super();
		String startTime[] = PROP_STARTTIME.split(":");
		String endTime[] = PROP_ENDTIME.split(":");
		this.name = name;
		this.availableCalls = Integer.valueOf(PROP_MAXCALLS);
		this.callsMade = 0;
		this.startTime = (LocalTime.of(Integer.valueOf(startTime[0]), Integer.valueOf(startTime[1])));
		this.endTime = (LocalTime.of(Integer.valueOf(endTime[0]), Integer.valueOf(endTime[1])));
	}


	@Override
	public String toString() {
		return "Api [name=" + name + ", availableCalls=" + availableCalls + ", callsMade=" + callsMade + ", startTime="
				+ startTime + ", endTime=" + endTime + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAvailableCalls() {
		return availableCalls;
	}

	public void setAvailableCalls(int availableCalls) {
		this.availableCalls = availableCalls;
	}

	public int getCallsMade() {
		return callsMade;
	}

	public void setCallsMade(int callsMade) {
		this.callsMade = callsMade;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

}

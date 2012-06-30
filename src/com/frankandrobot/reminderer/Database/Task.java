package com.frankandrobot.reminderer.Database;

import java.sql.Timestamp;

public class Task {

    // private variables
    int timestamp;
    String taskname;
    String duration;
    String location;

    // constructor returns the following database values to calendar app
    public Task(Timestamp timestamp, String taskname, String duration,
	    String location) {
	this.timestamp = timestamp;
	this.taskname = taskname;
	this.duration = duration;
	this.location = location;
    }

    // getting Timestamp
    public Timestamp getID() {
	return this.timestamp;
    }

    // setting Timestamp
    public void setID(Timestamp timestamp) {
	this.timestamp = timestamp;
    }

    // getting Task name
    public String getTaskName() {
	return this.taskname;
    }

    // setting Task name
    public void setName(String taskname) {
	this.taskname = taskname;
    }

    // getting Task Duration
    public String getTaskDuration() {
	return this.duration;
    }

    // setting Task Duration
    public void setTaskDuration(String duration) {
	this.duration = duration;
    }

    // getting Task Location
    public String getTaskLocation() {
	return this.location;
    }

    // setting Task Location
    public void setTaskLocation(String location) {
	this.location = location;
    }
}

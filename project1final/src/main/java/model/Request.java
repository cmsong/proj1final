package model;

import service.RequestService;

public class Request {
	private int r_id;
	private int u_id;
	private double price;
	private int event_type;
	private String location;  
	private String description;
	private String grade_format;
	private String grade_pass;
	private String reported_grade;
	private int approval;
	
	
	public int getApproval() {
		return approval;
	}

	public void setApproval(int approval) {
		RequestService.setApproval(r_id, approval);
		this.approval = approval;
	}

	public Request(int r_id, int u_id, double price, String gpass, int event_type, String location, String description, String grade_format,
			int approval, String reported_grade) {
		super();
		this.r_id = r_id;
		this.u_id = u_id;
		this.price = price;
		this.event_type = event_type;
		this.location = location;
		this.description = description;
		this.grade_format = grade_format;
		this.grade_pass = gpass;
		this.approval = approval;
		this.reported_grade = reported_grade;
	}

	public Request() {
		super();
	}

	public int getR_id() {
		return r_id;
	}

	public void setR_id(int r_id) {
		this.r_id = r_id;
	}

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	public int getEvent_type() {
		return event_type;
	}

	public void setEvent_type(int event_type) {
		this.event_type = event_type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGrade_format() {
		return grade_format;
	}

	public void setGrade_format(String grade_format) {
		this.grade_format = grade_format;
	}

	public String getGrade_pass() {
		return grade_pass;
	}

	public void setGrade_pass(String grade_pass) {
		this.grade_pass = grade_pass;
	}

	public String getReported_grade() {
		return reported_grade;
	}

	public void setReported_grade(String reported_grade) {
		this.reported_grade = reported_grade;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}

package model;

public class User {
    private int u_id;
    private int d_id;
    private String d_name;
    private int d_lvl;
    private double remaining;
    private double pending;
    private String uname;
    private String pword;

    public User(int u_id, int d_id, int d_lvl, double remaining,
            double pending, String uname, String pword) {
        this.u_id = u_id;
        this.d_id = d_id;
        this.d_lvl = d_lvl;
        this.d_name = getDept(d_id);
        this.remaining = remaining;
        this.pending = pending;
        this.uname = uname;
        this.pword = pword;
    }
    public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPword() {
		return pword;
	}
	public void setPword(String pword) {
		this.pword = pword;
	}
	public String getDept(int d_id) {
        switch (d_id){
        case 1:
            return "BenCo";
        case 2:
            return "Biology";
        case 3:
            return "Math";
        case 4:
            return "Geology";    
        }
        return null;
    }
	public String getDLvlName(int d_lvl) {
		switch(d_lvl) {
		case 1:
			return "Employee";
		case 2:
			return "Supervisor";
		case 3:
			return "Department Head";
		}
		return null;
		
	}
    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public int getD_id() {
        return d_id;
    }

    public void setD_id(int d_id) {
        this.d_id = d_id;
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    public int getD_lvl() {
        return d_lvl;
    }

    public void setD_lvl(int d_lvl) {
        this.d_lvl = d_lvl;
    }

    public double getRemaining() {
        return remaining;
    }

    public void setRemaining(double remaining) {
        this.remaining = remaining;
    }

    public double getPending() {
        return pending;
    }

    public void setPending(double pending) {
        this.pending = pending;
    }


}
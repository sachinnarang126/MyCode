package xyz.truehrms.parameters;


public class PunchMiss {

    public String punchDate,employee_id,employeemanager_id,attendanceid;
    public String reason;
    public String punchInTime;
    public String punchOutTime;
    public String ispunchin;
    public String ispunchout;

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public void setEmployeemanager_id(String employeemanager_id) {
        this.employeemanager_id = employeemanager_id;
    }

    public void setAttendanceid(String attendanceid) {
        this.attendanceid = attendanceid;
    }

    public void setIspunchin(String ispunchin) {
        this.ispunchin = ispunchin;
    }

    public void setIspunchout(String ispunchout) {
        this.ispunchout = ispunchout;
    }

    public void setPunchDate(String punchDate) {
        this.punchDate = punchDate;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setPunchInTime(String punchInTime) {
        this.punchInTime = punchInTime;
    }

    public void setPunchOutTime(String punchOutTime) {
        this.punchOutTime = punchOutTime;
    }

}

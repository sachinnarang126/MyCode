package xyz.truehrms.parameters;

public class EditProfile {
    private int id;
    private String fatherName, motherName, bloodgroup, dob, emergencycontactnumber, address1, address2, city, ModifiedBy, sex;

    public void setId(int id) {
        this.id = id;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setEmergencycontactnumber(String emergencycontactnumber) {
        this.emergencycontactnumber = emergencycontactnumber;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setModifiedBy(String modifiedBy) {
        ModifiedBy = modifiedBy;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}

package xyz.truehrms.parameters;

public class EditProfile {
    private int id;
    private String /*employee_id,*/ fatherName, motherName, bloodgroup, officialContact, dob, emergencycontactnumber, address1, address2, city, ModifiedBy, sex;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*public String getEmployeeID() {
        return employee_id;
    }

    public void setEmployeeID(String empcode) {
        this.employee_id = empcode;
    }*/

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getOfficialContact() {
        return officialContact;
    }

    public void setOfficialContact(String officialContact) {
        this.officialContact = officialContact;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmergencycontactnumber() {
        return emergencycontactnumber;
    }

    public void setEmergencycontactnumber(String emergencycontactnumber) {
        this.emergencycontactnumber = emergencycontactnumber;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        ModifiedBy = modifiedBy;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}

package xyz.truehrms.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class EmployeeInfo {

    @SerializedName("StatusCode")
    @Expose
    private float StatusCode;
    @SerializedName("Version")
    @Expose
    private String Version;
    @SerializedName("Result")
    @Expose
    private Result Result;
    @SerializedName("Errors")
    @Expose
    private List<Object> Errors = new ArrayList<Object>();

    /**
     * @return The StatusCode
     */
    public float getStatusCode() {
        return StatusCode;
    }

    /**
     * @param StatusCode The StatusCode
     */
    public void setStatusCode(float StatusCode) {
        this.StatusCode = StatusCode;
    }

    /**
     * @return The Version
     */
    public String getVersion() {
        return Version;
    }

    /**
     * @param Version The Version
     */
    public void setVersion(String Version) {
        this.Version = Version;
    }

    /**
     * @return The Result
     */
    public Result getResult() {
        return Result;
    }

    /**
     * @param Result The Result
     */
    public void setResult(Result Result) {
        this.Result = Result;
    }

    /**
     * @return The Errors
     */
    public List<Object> getErrors() {
        return Errors;
    }

    /**
     * @param Errors The Errors
     */
    public void setErrors(List<Object> Errors) {
        this.Errors = Errors;
    }

    @Generated("org.jsonschema2pojo")
    public class Result {

        @SerializedName("Logintime")
        @Expose
        private String Logintime;
        @SerializedName("LogOutTime")
        @Expose
        private String LogOutTime;
        @SerializedName("avatar")
        @Expose
        private String avatar;
        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("empcode")
        @Expose
        private String empcode;
        @SerializedName("firstname")
        @Expose
        private String firstname;
        @SerializedName("lastname")
        @Expose
        private String lastname;
        @SerializedName("Divisionname")
        @Expose
        private String Divisionname;
        @SerializedName("DesignationDesignation1")
        @Expose
        private String DesignationDesignation1;
        @SerializedName("Shiftname")
        @Expose
        private String Shiftname;
        @SerializedName("Location")
        @Expose
        private String Location;
        @SerializedName("ReportingTo")
        @Expose
        private String ReportingTo;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("officialContact")
        @Expose
        private String officialContact;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("fathername")
        @Expose
        private String fathername;
        @SerializedName("mothername")
        @Expose
        private String mothername;
        @SerializedName("extension")
        @Expose
        private int extension;
        @SerializedName("bloodgroup")
        @Expose
        private String bloodgroup;
        @SerializedName("emergencycontactnumber")
        @Expose
        private String emergencycontactnumber;
        @SerializedName("reportingtome")
        @Expose
        private List<String> reportingtome = new ArrayList<String>();
        @SerializedName("address1")
        @Expose
        private String address1;
        @SerializedName("address2")
        @Expose
        private String address2;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("reportingtomecount")
        @Expose
        private int reportingtomecount;
        @SerializedName("ismanager")
        @Expose
        private boolean ismanager;
        @SerializedName("designation_id")
        @Expose
        private Object designationId;
        @SerializedName("shift_id")
        @Expose
        private int shiftId;
        @SerializedName("division_id")
        @Expose
        private int divisionId;
        @SerializedName("status")
        @Expose
        private Object status;
        @SerializedName("managerId")
        @Expose
        private int managerId;
        @SerializedName("qualilfication")
        @Expose
        private String qualilfication;
        @SerializedName("emergencycontactperson")
        @Expose
        private String emergencycontactperson;
        @SerializedName("referred_by")
        @Expose
        private String referredBy;
        @SerializedName("isfresher")
        @Expose
        private boolean isfresher;
        @SerializedName("role_id")
        @Expose
        private Object roleId;
        @SerializedName("createdby")
        @Expose
        private int createdby;
        @SerializedName("doj")
        @Expose
        private String doj;
        @SerializedName("siblingcount")
        @Expose
        private int siblingcount;
        @SerializedName("certification")
        @Expose
        private String certification;
        @SerializedName("officialemail")
        @Expose
        private String officialemail;
        @SerializedName("personalContact")
        @Expose
        private String personalContact;
        @SerializedName("sex")
        @Expose
        private int sex;
        @SerializedName("isactive")
        @Expose
        private boolean isactive;
        @SerializedName("isdeleted")
        @Expose
        private boolean isdeleted;
        @SerializedName("isconfirmed")
        @Expose
        private boolean isconfirmed;
        @SerializedName("maritalstatus")
        @Expose
        private int maritalstatus;
        @SerializedName("marriedon")
        @Expose
        private String marriedon;
        @SerializedName("confirmedon")
        @Expose
        private String confirmedon;
        @SerializedName("deactivatedon")
        @Expose
        private Object deactivatedon;
        @SerializedName("accessto")
        @Expose
        private String accessto;
        @SerializedName("createdon")
        @Expose
        private String createdon;
        @SerializedName("company_id")
        @Expose
        private int companyId;
        @SerializedName("old_empcode")
        @Expose
        private int oldEmpcode;
        @SerializedName("ManagerFullName")
        @Expose
        private Object ManagerFullName;

        /**
         * @return The Logintime
         */
        public String getLogintime() {
            return validateData(Logintime);
        }

        /**
         * @param Logintime The Logintime
         */
        public void setLogintime(String Logintime) {
            this.Logintime = Logintime;
        }

        /**
         * @return The LogOutTime
         */
        public String getLogOutTime() {
            return validateData(LogOutTime);
        }

        /**
         * @param LogOutTime The LogOutTime
         */
        public void setLogOutTime(String LogOutTime) {
            this.LogOutTime = LogOutTime;
        }

        /**
         * @return The avatar
         */
        public String getAvatar() {
            return validateData(avatar);
        }

        /**
         * @param avatar The avatar
         */
        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        /**
         * @return The id
         */
        public int getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(int id) {
            this.id = id;
        }

        /**
         * @return The empcode
         */
        public String getEmpcode() {
            return empcode;
        }

        /**
         * @param empcode The empcode
         */
        public void setEmpcode(String empcode) {
            this.empcode = empcode;
        }

        /**
         * @return The firstname
         */
        public String getFirstname() {
            return validateData(firstname);
        }

        /**
         * @param firstname The firstname
         */
        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        /**
         * @return The lastname
         */
        public String getLastname() {
            return validateData(lastname);
        }

        /**
         * @param lastname The lastname
         */
        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        /**
         * @return The Divisionname
         */
        public String getDivisionname() {
            return validateData(Divisionname);
        }

        /**
         * @param Divisionname The Divisionname
         */
        public void setDivisionname(String Divisionname) {
            this.Divisionname = Divisionname;
        }

        /**
         * @return The DesignationDesignation1
         */
        public String getDesignationDesignation1() {
            return validateData(DesignationDesignation1);
        }

        /**
         * @param DesignationDesignation1 The DesignationDesignation1
         */
        public void setDesignationDesignation1(String DesignationDesignation1) {
            this.DesignationDesignation1 = DesignationDesignation1;
        }

        /**
         * @return The Shiftname
         */
        public String getShiftname() {
            return validateData(Shiftname);
        }

        /**
         * @param Shiftname The Shiftname
         */
        public void setShiftname(String Shiftname) {
            this.Shiftname = Shiftname;
        }

        /**
         * @return The Location
         */
        public String getLocation() {
            return validateData(Location);
        }

        /**
         * @param Location The Location
         */
        public void setLocation(String Location) {
            this.Location = Location;
        }

        /**
         * @return The ReportingTo
         */
        public String getReportingTo() {
            return validateData(ReportingTo);
        }

        /**
         * @param ReportingTo The ReportingTo
         */
        public void setReportingTo(String ReportingTo) {
            this.ReportingTo = ReportingTo;
        }

        /**
         * @return The email
         */
        public String getEmail() {
            return validateData(email);
        }

        /**
         * @param email The email
         */
        public void setEmail(String email) {
            this.email = email;
        }

        /**
         * @return The officialContact
         */
        public String getOfficialContact() {
            return validateData(officialContact);
        }

        /**
         * @param officialContact The officialContact
         */
        public void setOfficialContact(String officialContact) {
            this.officialContact = officialContact;
        }

        /**
         * @return The dob
         */
        public String getDob() {
            return validateData(dob);
        }

        /**
         * @param dob The dob
         */
        public void setDob(String dob) {
            this.dob = dob;
        }

        /**
         * @return The fathername
         */
        public String getFathername() {
            return validateData(fathername);
        }

        /**
         * @param fathername The fathername
         */
        public void setFathername(String fathername) {
            this.fathername = fathername;
        }

        /**
         * @return The mothername
         */
        public String getMothername() {
            return validateData(mothername);
        }

        /**
         * @param mothername The mothername
         */
        public void setMothername(String mothername) {
            this.mothername = mothername;
        }

        /**
         * @return The extension
         */
        public int getExtension() {
            return extension;
        }

        /**
         * @param extension The extension
         */
        public void setExtension(int extension) {
            this.extension = extension;
        }

        /**
         * @return The bloodgroup
         */
        public String getBloodgroup() {
            return validateData(bloodgroup);
        }

        /**
         * @param bloodgroup The bloodgroup
         */
        public void setBloodgroup(String bloodgroup) {
            this.bloodgroup = bloodgroup;
        }

        /**
         * @return The emergencycontactnumber
         */
        public String getEmergencycontactnumber() {
            return validateData(emergencycontactnumber);
        }

        /**
         * @param emergencycontactnumber The emergencycontactnumber
         */
        public void setEmergencycontactnumber(String emergencycontactnumber) {
            this.emergencycontactnumber = emergencycontactnumber;
        }

        /**
         * @return The reportingtome
         */
        public List<String> getReportingtome() {
            return reportingtome == null ? new ArrayList<String>() : reportingtome;
        }

        /**
         * @param reportingtome The reportingtome
         */
        public void setReportingtome(List<String> reportingtome) {
            this.reportingtome = reportingtome;
        }

        /**
         * @return The address1
         */
        public String getAddress1() {
            return validateData(address1);
        }

        /**
         * @param address1 The address1
         */
        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        /**
         * @return The address2
         */
        public String getAddress2() {
            return validateData(address2);
        }

        /**
         * @param address2 The address2
         */
        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        /**
         * @return The city
         */
        public String getCity() {
            return validateData(city);
        }

        /**
         * @param city The city
         */
        public void setCity(String city) {
            this.city = city;
        }

        /**
         * @return The reportingtomecount
         */
        public int getReportingtomecount() {
            return reportingtomecount;
        }

        /**
         * @param reportingtomecount The reportingtomecount
         */
        public void setReportingtomecount(int reportingtomecount) {
            this.reportingtomecount = reportingtomecount;
        }

        /**
         * @return The ismanager
         */
        public boolean getIsmanager() {
            return ismanager;
        }

        /**
         * @param ismanager The ismanager
         */
        public void setIsmanager(boolean ismanager) {
            this.ismanager = ismanager;
        }

        /**
         * @return The designationId
         */
        public Object getDesignationId() {
            return designationId;
        }

        /**
         * @param designationId The designation_id
         */
        public void setDesignationId(Object designationId) {
            this.designationId = designationId;
        }

        /**
         * @return The shiftId
         */
        public int getShiftId() {
            return shiftId;
        }

        /**
         * @param shiftId The shift_id
         */
        public void setShiftId(int shiftId) {
            this.shiftId = shiftId;
        }

        /**
         * @return The divisionId
         */
        public int getDivisionId() {
            return divisionId;
        }

        /**
         * @param divisionId The division_id
         */
        public void setDivisionId(int divisionId) {
            this.divisionId = divisionId;
        }

        /**
         * @return The status
         */
        public Object getStatus() {
            return status;
        }

        /**
         * @param status The status
         */
        public void setStatus(Object status) {
            this.status = status;
        }

        /**
         * @return The managerId
         */
        public int getManagerId() {
            return managerId;
        }

        /**
         * @param managerId The managerId
         */
        public void setManagerId(int managerId) {
            this.managerId = managerId;
        }

        /**
         * @return The qualilfication
         */
        public String getQualilfication() {
            return validateData(qualilfication);
        }

        /**
         * @param qualilfication The qualilfication
         */
        public void setQualilfication(String qualilfication) {
            this.qualilfication = qualilfication;
        }

        /**
         * @return The emergencycontactperson
         */
        public String getEmergencycontactperson() {
            return validateData(emergencycontactperson);
        }

        /**
         * @param emergencycontactperson The emergencycontactperson
         */
        public void setEmergencycontactperson(String emergencycontactperson) {
            this.emergencycontactperson = emergencycontactperson;
        }

        /**
         * @return The referredBy
         */
        public String getReferredBy() {
            return validateData(referredBy);
        }

        /**
         * @param referredBy The referred_by
         */
        public void setReferredBy(String referredBy) {
            this.referredBy = referredBy;
        }

        /**
         * @return The isfresher
         */
        public boolean getIsfresher() {
            return isfresher;
        }

        /**
         * @param isfresher The isfresher
         */
        public void setIsfresher(boolean isfresher) {
            this.isfresher = isfresher;
        }

        /**
         * @return The roleId
         */
        public Object getRoleId() {
            return roleId;
        }

        /**
         * @param roleId The role_id
         */
        public void setRoleId(Object roleId) {
            this.roleId = roleId;
        }

        /**
         * @return The createdby
         */
        public int getCreatedby() {
            return createdby;
        }

        /**
         * @param createdby The createdby
         */
        public void setCreatedby(int createdby) {
            this.createdby = createdby;
        }

        /**
         * @return The doj
         */
        public String getDoj() {
            return validateData(doj);
        }

        /**
         * @param doj The doj
         */
        public void setDoj(String doj) {
            this.doj = doj;
        }

        /**
         * @return The siblingcount
         */
        public int getSiblingcount() {
            return siblingcount;
        }

        /**
         * @param siblingcount The siblingcount
         */
        public void setSiblingcount(int siblingcount) {
            this.siblingcount = siblingcount;
        }

        /**
         * @return The certification
         */
        public String getCertification() {
            return validateData(certification);
        }

        /**
         * @param certification The certification
         */
        public void setCertification(String certification) {
            this.certification = certification;
        }

        /**
         * @return The officialemail
         */
        public String getOfficialemail() {
            return validateData(officialemail);
        }

        /**
         * @param officialemail The officialemail
         */
        public void setOfficialemail(String officialemail) {
            this.officialemail = officialemail;
        }

        /**
         * @return The personalContact
         */
        public String getPersonalContact() {
            return validateData(personalContact);
        }

        /**
         * @param personalContact The personalContact
         */
        public void setPersonalContact(String personalContact) {
            this.personalContact = personalContact;
        }

        /**
         * @return The sex
         */
        public int getSex() {
            return sex;
        }

        /**
         * @param sex The sex
         */
        public void setSex(int sex) {
            this.sex = sex;
        }

        /**
         * @return The isactive
         */
        public boolean getIsactive() {
            return isactive;
        }

        /**
         * @param isactive The isactive
         */
        public void setIsactive(boolean isactive) {
            this.isactive = isactive;
        }

        /**
         * @return The isdeleted
         */
        public boolean getIsdeleted() {
            return isdeleted;
        }

        /**
         * @param isdeleted The isdeleted
         */
        public void setIsdeleted(boolean isdeleted) {
            this.isdeleted = isdeleted;
        }

        /**
         * @return The isconfirmed
         */
        public boolean getIsconfirmed() {
            return isconfirmed;
        }

        /**
         * @param isconfirmed The isconfirmed
         */
        public void setIsconfirmed(boolean isconfirmed) {
            this.isconfirmed = isconfirmed;
        }

        /**
         * @return The maritalstatus
         */
        public int getMaritalstatus() {
            return maritalstatus;
        }

        /**
         * @param maritalstatus The maritalstatus
         */
        public void setMaritalstatus(int maritalstatus) {
            this.maritalstatus = maritalstatus;
        }

        /**
         * @return The marriedon
         */
        public String getMarriedon() {
            return validateData(marriedon);
        }

        /**
         * @param marriedon The marriedon
         */
        public void setMarriedon(String marriedon) {
            this.marriedon = marriedon;
        }

        /**
         * @return The confirmedon
         */
        public String getConfirmedon() {
            return validateData(confirmedon);
        }

        /**
         * @param confirmedon The confirmedon
         */
        public void setConfirmedon(String confirmedon) {
            this.confirmedon = confirmedon;
        }

        /**
         * @return The deactivatedon
         */
        public Object getDeactivatedon() {
            return deactivatedon;
        }

        /**
         * @param deactivatedon The deactivatedon
         */
        public void setDeactivatedon(Object deactivatedon) {
            this.deactivatedon = deactivatedon;
        }

        /**
         * @return The accessto
         */
        public String getAccessto() {
            return validateData(accessto);
        }

        /**
         * @param accessto The accessto
         */
        public void setAccessto(String accessto) {
            this.accessto = accessto;
        }

        /**
         * @return The createdon
         */
        public String getCreatedon() {
            return validateData(createdon);
        }

        /**
         * @param createdon The createdon
         */
        public void setCreatedon(String createdon) {
            this.createdon = createdon;
        }

        /**
         * @return The companyId
         */
        public int getCompanyId() {
            return companyId;
        }

        /**
         * @param companyId The company_id
         */
        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        /**
         * @return The oldEmpcode
         */
        public int getOldEmpcode() {
            return oldEmpcode;
        }

        /**
         * @param oldEmpcode The old_empcode
         */
        public void setOldEmpcode(int oldEmpcode) {
            this.oldEmpcode = oldEmpcode;
        }

        /**
         * @return The ManagerFullName
         */
        public Object getManagerFullName() {
            return ManagerFullName;
        }

        /**
         * @param ManagerFullName The ManagerFullName
         */
        public void setManagerFullName(Object ManagerFullName) {
            this.ManagerFullName = ManagerFullName;
        }

        private String validateData(String data) {
            return data == null ? "" : data;
        }
    }
}
package xyz.truehrms.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class EditProfile {

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

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("empcode")
        @Expose
        private String empcode;
        @SerializedName("fatherName")
        @Expose
        private String fatherName;
        @SerializedName("motherName")
        @Expose
        private String motherName;
        @SerializedName("bloodgroup")
        @Expose
        private String bloodgroup;
        @SerializedName("officialContact")
        @Expose
        private String officialContact;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("emergencycontactnumber")
        @Expose
        private String emergencycontactnumber;
        @SerializedName("address1")
        @Expose
        private String address1;
        @SerializedName("address2")
        @Expose
        private String address2;
        @SerializedName("city")
        @Expose
        private Object city;
        @SerializedName("ModifiedBy")
        @Expose
        private Object ModifiedBy;
        @SerializedName("ModifiedOn")
        @Expose
        private String ModifiedOn;
        @SerializedName("sex")
        @Expose
        private int sex;

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
         * @return The fatherName
         */
        public String getFatherName() {
            return validateData(fatherName);
        }

        /**
         * @param fatherName The fatherName
         */
        public void setFatherName(String fatherName) {
            this.fatherName = fatherName;
        }

        /**
         * @return The motherName
         */
        public String getMotherName() {
            return validateData(motherName);
        }

        /**
         * @param motherName The motherName
         */
        public void setMotherName(String motherName) {
            this.motherName = motherName;
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
        public Object getCity() {
            return city;
        }

        /**
         * @param city The city
         */
        public void setCity(Object city) {
            this.city = city;
        }

        /**
         * @return The ModifiedBy
         */
        public Object getModifiedBy() {
            return ModifiedBy;
        }

        /**
         * @param ModifiedBy The ModifiedBy
         */
        public void setModifiedBy(Object ModifiedBy) {
            this.ModifiedBy = ModifiedBy;
        }

        /**
         * @return The ModifiedOn
         */
        public String getModifiedOn() {
            return validateData(ModifiedOn);
        }

        /**
         * @param ModifiedOn The ModifiedOn
         */
        public void setModifiedOn(String ModifiedOn) {
            this.ModifiedOn = ModifiedOn;
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

        private String validateData(String data) {
            return data == null ? "" : data;
        }
    }
}
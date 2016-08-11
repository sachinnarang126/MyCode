package xyz.truehrms.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class PersonInCharge {

    @SerializedName("StatusCode")
    @Expose
    private float StatusCode;
    @SerializedName("Version")
    @Expose
    private String Version;
    @SerializedName("Result")
    @Expose
    private List<Result> Result = new ArrayList<Result>();
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
    public List<Result> getResult() {
        return Result;
    }

    /**
     * @param Result The Result
     */
    public void setResult(List<Result> Result) {
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

        @SerializedName("empcode")
        @Expose
        private int empcode;
        @SerializedName("firstname")
        @Expose
        private String firstname;
        @SerializedName("lastname")
        @Expose
        private String lastname;

        @SerializedName("empid")
        @Expose
        private int empid;

        /**
         * @return The empcode
         */
        public int getEmpcode() {
            return empcode;
        }

        /**
         * @param empcode The empcode
         */
        public void setEmpcode(int empcode) {
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

        public int getEmpid() {
            return empid;
        }

        public void setEmpid(int empid) {
            this.empid = empid;
        }

        private String validateData(String data) {
            return data == null ? "" : data;
        }
    }
}
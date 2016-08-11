package xyz.truehrms.bean;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class EmployeeAttendance {

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
        @SerializedName("PunchDate")
        @Expose
        private String PunchDate;
        @SerializedName("punchIN")
        @Expose
        private String punchIN;
        @SerializedName("punchOUT")
        @Expose
        private String punchOUT;
        @SerializedName("Logintime")
        @Expose
        private String Logintime;
        @SerializedName("Logouttime")
        @Expose
        private String Logouttime;

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
         * @return The PunchDate
         */
        public String getPunchDate() {
            return validateData(PunchDate);
        }

        /**
         * @param PunchDate The PunchDate
         */
        public void setPunchDate(String PunchDate) {
            this.PunchDate = PunchDate;
        }

        /**
         * @return The punchIN
         */
        public String getPunchIN() {
            return validateData(punchIN);
        }

        /**
         * @param punchIN The punchIN
         */
        public void setPunchIN(String punchIN) {
            this.punchIN = punchIN;
        }

        /**
         * @return The punchOUT
         */
        public String getPunchOUT() {
            return validateData(punchOUT);
        }

        /**
         * @param punchOUT The punchOUT
         */
        public void setPunchOUT(String punchOUT) {
            this.punchOUT = punchOUT;
        }

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
         * @return The Logouttime
         */
        public String getLogouttime() {
            return validateData(Logouttime);
        }

        /**
         * @param Logouttime The Logouttime
         */
        public void setLogouttime(String Logouttime) {
            this.Logouttime = Logouttime;
        }

        private String validateData(String data) {
            return data == null ? "" : data;
        }

    }
}
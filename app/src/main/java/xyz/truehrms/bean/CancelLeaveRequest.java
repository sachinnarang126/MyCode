package xyz.truehrms.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class CancelLeaveRequest {

    @SerializedName("StatusCode")
    @Expose
    private float statusCode;
    @SerializedName("Version")
    @Expose
    private String version;
    @SerializedName("Result")
    @Expose
    private Result result;
    @SerializedName("Errors")
    @Expose
    private List<Object> errors = new ArrayList<Object>();

    /**
     * @return The statusCode
     */
    public float getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode The StatusCode
     */
    public void setStatusCode(float statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return The version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version The Version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return The result
     */
    public Result getResult() {
        return result;
    }

    /**
     * @param result The Result
     */
    public void setResult(Result result) {
        this.result = result;
    }

    /**
     * @return The errors
     */
    public List<Object> getErrors() {
        return errors;
    }

    /**
     * @param errors The Errors
     */
    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    @Generated("org.jsonschema2pojo")
    public class Result {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("requestedon")
        @Expose
        private String requestedon;
        @SerializedName("fromdate")
        @Expose
        private String fromdate;
        @SerializedName("todate")
        @Expose
        private String todate;
        @SerializedName("contactnumber")
        @Expose
        private String contactnumber;
        @SerializedName("personincharge")
        @Expose
        private String personincharge;
        @SerializedName("leavedays")
        @Expose
        private float leavedays;
        @SerializedName("empcode")
        @Expose
        private int empcode;
        @SerializedName("typeofleave")
        @Expose
        private int typeofleave;
        @SerializedName("emp_name")
        @Expose
        private String empName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("rejectionreason")
        @Expose
        private String rejectionreason;
        @SerializedName("manager")
        @Expose
        private Object manager;

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
         * @return The requestedon
         */
        public String getRequestedon() {
            return requestedon;
        }

        /**
         * @param requestedon The requestedon
         */
        public void setRequestedon(String requestedon) {
            this.requestedon = requestedon;
        }

        /**
         * @return The fromdate
         */
        public String getFromdate() {
            return fromdate;
        }

        /**
         * @param fromdate The fromdate
         */
        public void setFromdate(String fromdate) {
            this.fromdate = fromdate;
        }

        /**
         * @return The todate
         */
        public String getTodate() {
            return todate;
        }

        /**
         * @param todate The todate
         */
        public void setTodate(String todate) {
            this.todate = todate;
        }

        /**
         * @return The contactnumber
         */
        public String getContactnumber() {
            return contactnumber;
        }

        /**
         * @param contactnumber The contactnumber
         */
        public void setContactnumber(String contactnumber) {
            this.contactnumber = contactnumber;
        }

        /**
         * @return The personincharge
         */
        public String getPersonincharge() {
            return personincharge;
        }

        /**
         * @param personincharge The personincharge
         */
        public void setPersonincharge(String personincharge) {
            this.personincharge = personincharge;
        }

        /**
         * @return The leavedays
         */
        public float getLeavedays() {
            return leavedays;
        }

        /**
         * @param leavedays The leavedays
         */
        public void setLeavedays(float leavedays) {
            this.leavedays = leavedays;
        }

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
         * @return The typeofleave
         */
        public int getTypeofleave() {
            return typeofleave;
        }

        /**
         * @param typeofleave The typeofleave
         */
        public void setTypeofleave(int typeofleave) {
            this.typeofleave = typeofleave;
        }

        /**
         * @return The empName
         */
        public String getEmpName() {
            return empName;
        }

        /**
         * @param empName The emp_name
         */
        public void setEmpName(String empName) {
            this.empName = empName;
        }

        /**
         * @return The email
         */
        public String getEmail() {
            return email;
        }

        /**
         * @param email The email
         */
        public void setEmail(String email) {
            this.email = email;
        }

        /**
         * @return The rejectionreason
         */
        public String getRejectionreason() {
            return rejectionreason;
        }

        /**
         * @param rejectionreason The rejectionreason
         */
        public void setRejectionreason(String rejectionreason) {
            this.rejectionreason = rejectionreason;
        }

        /**
         * @return The manager
         */
        public Object getManager() {
            return manager;
        }

        /**
         * @param manager The manager
         */
        public void setManager(Object manager) {
            this.manager = manager;
        }
    }
}

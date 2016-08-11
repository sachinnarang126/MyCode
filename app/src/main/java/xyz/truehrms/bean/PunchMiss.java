package xyz.truehrms.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class PunchMiss {

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

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("punchdate")
        @Expose
        private String punchdate;
        @SerializedName("punchtime")
        @Expose
        private String punchtime;
        @SerializedName("punchdescription")
        @Expose
        private String punchdescription;
        @SerializedName("requestedby_id")
        @Expose
        private String requestedbyId;
        @SerializedName("requestedto_id")
        @Expose
        private String requestedtoId;
        @SerializedName("createdon")
        @Expose
        private String createdon;
        @SerializedName("ispunchIn")
        @Expose
        private boolean ispunchIn;
        @SerializedName("punchtype")
        @Expose
        private int punchtype;
        @SerializedName("isactive")
        @Expose
        private boolean isactive;
        @SerializedName("isapproved")
        @Expose
        private String isapproved;
        @SerializedName("approvedon")
        @Expose
        private Object approvedon;

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
         * @return The punchdate
         */
        public String getPunchdate() {
            return validateData(punchdate);
        }

        /**
         * @param punchdate The punchdate
         */
        public void setPunchdate(String punchdate) {
            this.punchdate = punchdate;
        }

        /**
         * @return The punchtime
         */
        public String getPunchtime() {
            return validateData(punchtime);
        }

        /**
         * @param punchtime The punchtime
         */
        public void setPunchtime(String punchtime) {
            this.punchtime = punchtime;
        }

        /**
         * @return The punchdescription
         */
        public String getPunchdescription() {
            return validateData(punchdescription);
        }

        /**
         * @param punchdescription The punchdescription
         */
        public void setPunchdescription(String punchdescription) {
            this.punchdescription = punchdescription;
        }

        /**
         * @return The requestedbyId
         */
        public String getRequestedbyId() {
            return requestedbyId;
        }

        /**
         * @param requestedbyId The requestedby_id
         */
        public void setRequestedbyId(String requestedbyId) {
            this.requestedbyId = requestedbyId;
        }

        /**
         * @return The requestedtoId
         */
        public String getRequestedtoId() {
            return requestedtoId;
        }

        /**
         * @param requestedtoId The requestedto_id
         */
        public void setRequestedtoId(String requestedtoId) {
            this.requestedtoId = requestedtoId;
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
         * @return The ispunchIn
         */
        public boolean getIspunchIn() {
            return ispunchIn;
        }

        /**
         * @param ispunchIn The ispunchIn
         */
        public void setIspunchIn(boolean ispunchIn) {
            this.ispunchIn = ispunchIn;
        }

        /**
         * @return The punchtype
         */
        public int getPunchtype() {
            return punchtype;
        }

        /**
         * @param punchtype The punchtype
         */
        public void setPunchtype(int punchtype) {
            this.punchtype = punchtype;
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
         * @return The isapproved
         */
        public String getIsapproved() {
            return validateData(isapproved);
        }

        /**
         * @param isapproved The isapproved
         */
        public void setIsapproved(String isapproved) {
            this.isapproved = isapproved;
        }

        /**
         * @return The approvedon
         */
        public Object getApprovedon() {
            return approvedon;
        }

        /**
         * @param approvedon The approvedon
         */
        public void setApprovedon(Object approvedon) {
            this.approvedon = approvedon;
        }

        private String validateData(String data) {
            return data == null ? "" : data;
        }
    }
}
package xyz.truehrms.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class LeaveSummary {

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

        @SerializedName("LeaveType")
        @Expose
        private String LeaveType;
        @SerializedName("CarriedOver")
        @Expose
        private float CarriedOver;
        @SerializedName("Entitled")
        @Expose
        private float Entitled;
        @SerializedName("Taken")
        @Expose
        private float Taken;
        @SerializedName("Balance")
        @Expose
        private float Balance;

        /**
         * @return The LeaveType
         */
        public String getLeaveType() {
            return validateData(LeaveType);
        }

        /**
         * @param LeaveType The LeaveType
         */
        public void setLeaveType(String LeaveType) {
            this.LeaveType = LeaveType;
        }

        /**
         * @return The CarriedOver
         */
        public float getCarriedOver() {
            return CarriedOver;
        }

        /**
         * @param CarriedOver The CarriedOver
         */
        public void setCarriedOver(float CarriedOver) {
            this.CarriedOver = CarriedOver;
        }

        /**
         * @return The Entitled
         */
        public float getEntitled() {
            return Entitled;
        }

        /**
         * @param Entitled The Entitled
         */
        public void setEntitled(float Entitled) {
            this.Entitled = Entitled;
        }

        /**
         * @return The Taken
         */
        public float getTaken() {
            return Taken;
        }

        /**
         * @param Taken The Taken
         */
        public void setTaken(float Taken) {
            this.Taken = Taken;
        }

        /**
         * @return The Balance
         */
        public float getBalance() {
            return Balance;
        }

        /**
         * @param Balance The Balance
         */
        public void setBalance(float Balance) {
            this.Balance = Balance;
        }

        private String validateData(String data) {
            return data == null ? "" : data;
        }
    }
}
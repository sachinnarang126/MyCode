package xyz.truehrms.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class LeaveDayTypes {

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

        @SerializedName("Id")
        @Expose
        private int Id;
        @SerializedName("DayType")
        @Expose
        private String DayType;
        @SerializedName("DatTypeValue")
        @Expose
        private float DatTypeValue;

        /**
         * @return The Id
         */
        public int getId() {
            return Id;
        }

        /**
         * @param Id The Id
         */
        public void setId(int Id) {
            this.Id = Id;
        }

        /**
         * @return The DayType
         */
        public String getDayType() {
            return validateData(DayType);
        }

        /**
         * @param DayType The DayType
         */
        public void setDayType(String DayType) {
            this.DayType = DayType;
        }

        /**
         * @return The DatTypeValue
         */
        public float getDatTypeValue() {
            return DatTypeValue;
        }

        /**
         * @param DatTypeValue The DatTypeValue
         */
        public void setDatTypeValue(float DatTypeValue) {
            this.DatTypeValue = DatTypeValue;
        }

        private String validateData(String data) {
            return data == null ? "" : data;
        }
    }

}
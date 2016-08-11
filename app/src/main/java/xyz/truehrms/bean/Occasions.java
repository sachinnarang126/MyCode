package xyz.truehrms.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Occasions {

    @SerializedName("StatusCode")
    @Expose
    private float statusCode;
    @SerializedName("Version")
    @Expose
    private String version;
    @SerializedName("Result")
    @Expose
    private List<Result> result = new ArrayList<Result>();
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
    public List<Result> getResult() {
        return result;
    }

    /**
     * @param result The Result
     */
    public void setResult(List<Result> result) {
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
        @SerializedName("empcode")
        @Expose
        private int empcode;
        @SerializedName("emp_name")
        @Expose
        private String empName;
        @SerializedName("occation_type")
        @Expose
        private int occationType;
        @SerializedName("occ_origDate")
        @Expose
        private String occOrigDate;
        @SerializedName("occation_date")
        @Expose
        private String occationDate;
        @SerializedName("emp_image")
        @Expose
        private String empImage;
        @SerializedName("empDesignation")
        @Expose
        private String empDesignation;

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
         * @return The empName
         */
        public String getEmpName() {
            return validateData(empName);
        }

        /**
         * @param empName The emp_name
         */
        public void setEmpName(String empName) {
            this.empName = empName;
        }

        /**
         * @return The occationType
         */
        public int getOccationType() {
            return occationType;
        }

        /**
         * @param occationType The occation_type
         */
        public void setOccationType(int occationType) {
            this.occationType = occationType;
        }

        /**
         * @return The occOrigDate
         */
        public String getOccOrigDate() {
            return validateData(occOrigDate);
        }

        /**
         * @param occOrigDate The occ_origDate
         */
        public void setOccOrigDate(String occOrigDate) {
            this.occOrigDate = occOrigDate;
        }

        /**
         * @return The occationDate
         */
        public String getOccationDate() {
            return validateData(occationDate);
        }

        /**
         * @param occationDate The occation_date
         */
        public void setOccationDate(String occationDate) {
            this.occationDate = occationDate;
        }

        /**
         * @return The empImage
         */
        public String getEmpImage() {
            return validateData(empImage);
        }

        /**
         * @param empImage The emp_image
         */
        public void setEmpImage(String empImage) {
            this.empImage = empImage;
        }

        /**
         * @return The empDesignation
         */
        public String getEmpDesignation() {
            return validateData(empDesignation);
        }

        /**
         * @param empDesignation The empDesignation
         */
        public void setEmpDesignation(String empDesignation) {
            this.empDesignation = empDesignation;
        }

        private String validateData(String data) {
            return data == null ? "" : data;
        }
    }


}
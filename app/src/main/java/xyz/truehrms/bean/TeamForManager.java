package xyz.truehrms.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class TeamForManager {

    @SerializedName("StatusCode")
    @Expose
    private float statusCode;
    @SerializedName("Version")
    @Expose
    private String version;
    @SerializedName("Result")
    @Expose
    private List<Result> result = new ArrayList<>();
    @SerializedName("Errors")
    @Expose
    private List<Object> errors = new ArrayList<>();

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
        @SerializedName("empCode")
        @Expose
        private String empcode;
        @SerializedName("firstname")
        @Expose
        private String firstname;
        @SerializedName("lastname")
        @Expose
        private String lastname;
        @SerializedName("designation")
        @Expose
        private String designation;
        @SerializedName("avatar")
        @Expose
        private String avatar;
        @SerializedName("fullname")
        @Expose
        private String fullname;

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
         * @return The designation
         */
        public String getDesignation() {
            return validateData(designation);
        }

        /**
         * @param designation The designation
         */
        public void setDesignation(String designation) {
            this.designation = designation;
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
         * @return The fullname
         */
        public String getFullname() {
            return validateData(fullname);
        }

        /**
         * @param fullname The fullname
         */
        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        private String validateData(String data) {
            return data == null ? "" : data;
        }

    }
}
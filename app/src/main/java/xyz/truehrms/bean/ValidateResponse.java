package xyz.truehrms.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ValidateResponse {

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
        @SerializedName("user_id")
        @Expose
        private int userID;
        @SerializedName("empcode")
        @Expose
        private String empcode;
        @SerializedName("firstname")
        @Expose
        private String firstname;
        @SerializedName("lastname")
        @Expose
        private String lastname;
        @SerializedName("managerId")
        @Expose
        private int managerId;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("role_id")
        @Expose
        private int roleId;
        @SerializedName("createdby")
        @Expose
        private Object createdby;
        @SerializedName("isactive")
        @Expose
        private boolean isactive;
        @SerializedName("isdeleted")
        @Expose
        private boolean isdeleted;
        @SerializedName("isconfirmed")
        @Expose
        private Object isconfirmed;
        @SerializedName("ismanager")
        @Expose
        private boolean ismanager;
        @SerializedName("avatar")
        @Expose
        private String avatar;
        @SerializedName("managercode")
        @Expose
        private String managercode;
        @SerializedName("manageremail")
        @Expose
        private Object manageremail;
        @SerializedName("company_id")
        @Expose
        private int companyId;
        @SerializedName("shift_id")
        @Expose
        private int shiftId;
        @SerializedName("rolename")
        @Expose
        private String rolename;
        @SerializedName("thumbImage")
        @Expose
        private String thumbImage;

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

        public int getUserID() {
            return userID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
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
         * @return The roleId
         */
        public int getRoleId() {
            return roleId;
        }

        /**
         * @param roleId The role_id
         */
        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        /**
         * @return The createdby
         */
        public Object getCreatedby() {
            return createdby;
        }

        /**
         * @param createdby The createdby
         */
        public void setCreatedby(Object createdby) {
            this.createdby = createdby;
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
        public Object getIsconfirmed() {
            return isconfirmed;
        }

        /**
         * @param isconfirmed The isconfirmed
         */
        public void setIsconfirmed(Object isconfirmed) {
            this.isconfirmed = isconfirmed;
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
         * @return The managercode
         */
        public String getManagercode() {
            return managercode;
        }

        /**
         * @param managercode The managercode
         */
        public void setManagercode(String managercode) {
            this.managercode = managercode;
        }

        /**
         * @return The manageremail
         */
        public Object getManageremail() {
            return manageremail;
        }

        /**
         * @param manageremail The manageremail
         */
        public void setManageremail(Object manageremail) {
            this.manageremail = manageremail;
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
         * @return The rolename
         */
        public String getRolename() {
            return validateData(rolename);
        }

        /**
         * @param rolename The rolename
         */
        public void setRolename(String rolename) {
            this.rolename = rolename;
        }

        /**
         * @return The thumbImage
         */
        public String getThumbImage() {
            return validateData(thumbImage);
        }

        /**
         * @param thumbImage The thumbImage
         */
        public void setThumbImage(String thumbImage) {
            this.thumbImage = thumbImage;
        }

        private String validateData(String data) {
            return data == null ? "" : data;
        }
    }
}
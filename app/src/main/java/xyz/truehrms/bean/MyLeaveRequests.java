package xyz.truehrms.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class MyLeaveRequests {

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

        @SerializedName("LeaveListResult")
        @Expose
        private List<LeaveListResult> LeaveListResult = new ArrayList<LeaveListResult>();
        @SerializedName("PageSize")
        @Expose
        private int PageSize;
        @SerializedName("PageIndex")
        @Expose
        private int PageIndex;
        @SerializedName("Sort")
        @Expose
        private Object Sort;
        @SerializedName("Count")
        @Expose
        private int Count;

        /**
         * @return The LeaveListResult
         */
        public List<LeaveListResult> getLeaveListResult() {
            return LeaveListResult;
        }

        /**
         * @param LeaveListResult The LeaveListResult
         */
        public void setLeaveListResult(List<LeaveListResult> LeaveListResult) {
            this.LeaveListResult = LeaveListResult;
        }

        /**
         * @return The PageSize
         */
        public int getPageSize() {
            return PageSize;
        }

        /**
         * @param PageSize The PageSize
         */
        public void setPageSize(int PageSize) {
            this.PageSize = PageSize;
        }

        /**
         * @return The PageIndex
         */
        public int getPageIndex() {
            return PageIndex;
        }

        /**
         * @param PageIndex The PageIndex
         */
        public void setPageIndex(int PageIndex) {
            this.PageIndex = PageIndex;
        }

        /**
         * @return The Sort
         */
        public Object getSort() {
            return Sort;
        }

        /**
         * @param Sort The Sort
         */
        public void setSort(Object Sort) {
            this.Sort = Sort;
        }

        /**
         * @return The Count
         */
        public int getCount() {
            return Count;
        }

        /**
         * @param Count The Count
         */
        public void setCount(int Count) {
            this.Count = Count;
        }

        @Generated("org.jsonschema2pojo")
        public class LeaveListResult {

            @SerializedName("id")
            @Expose
            private int id;
            @SerializedName("employee_id")
            @Expose
            private Object employeeId;
            @SerializedName("empcode")
            @Expose
            private int empcode;
            @SerializedName("typeofleave")
            @Expose
            private int typeofleave;
            @SerializedName("leavefrom")
            @Expose
            private String fromdate;
            @SerializedName("leaveto")
            @Expose
            private String todate;
            @SerializedName("reason")
            @Expose
            private String reason;
            @SerializedName("address")
            @Expose
            private String address;
            @SerializedName("contactnumber")
            @Expose
            private String contactnumber;
            @SerializedName("personincharge")
            @Expose
            private String personincharge;
            @SerializedName("requestedon")
            @Expose
            private String requestedon;
            @SerializedName("firstD_Type")
            @Expose
            private int firstDType;
            @SerializedName("lastD_Type")
            @Expose
            private int lastDType;
            @SerializedName("complimentary_against")
            @Expose
            private String complimentaryAgainst;
            @SerializedName("leavedays")
            @Expose
            private float leavedays;
            @SerializedName("isactive")
            @Expose
            private Object isactive;
            @SerializedName("isapproved")
            @Expose
            private int isapproved;
            @SerializedName("lastupdatedon")
            @Expose
            private Object lastupdatedon;
            @SerializedName("rejectionreason")
            @Expose
            private Object rejectionreason;
            @SerializedName("certificate")
            @Expose
            private Object certificate;
            @SerializedName("clientname")
            @Expose
            private Object clientname;
            @SerializedName("clientaddress")
            @Expose
            private Object clientaddress;
            @SerializedName("department")
            @Expose
            private String department;
            @SerializedName("empname")
            @Expose
            private String empname;
            @SerializedName("leavetype")
            @Expose
            private String leavetype;
            @SerializedName("empemail")
            @Expose
            private Object empemail;
            @SerializedName("managerName")
            @Expose
            private String managerName;

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
             * @return The employeeId
             */
            public Object getEmployeeId() {
                return employeeId;
            }

            /**
             * @param employeeId The employee_id
             */
            public void setEmployeeId(Object employeeId) {
                this.employeeId = employeeId;
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
             * @return The fromdate
             */
            public String getFromdate() {
                return validateData(fromdate);
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
                return validateData(todate);
            }

            /**
             * @param todate The todate
             */
            public void setTodate(String todate) {
                this.todate = todate;
            }

            /**
             * @return The reason
             */
            public String getReason() {
                return validateData(reason);
            }

            /**
             * @param reason The reason
             */
            public void setReason(String reason) {
                this.reason = reason;
            }

            /**
             * @return The address
             */
            public String getAddress() {
                return validateData(address);
            }

            /**
             * @param address The address
             */
            public void setAddress(String address) {
                this.address = address;
            }

            /**
             * @return The contactnumber
             */
            public String getContactnumber() {
                return validateData(contactnumber);
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
             * @return The requestedon
             */
            public String getRequestedon() {
                return validateData(requestedon);
            }

            /**
             * @param requestedon The requestedon
             */
            public void setRequestedon(String requestedon) {
                this.requestedon = requestedon;
            }

            /**
             * @return The firstDType
             */
            public int getFirstDType() {
                return firstDType;
            }

            /**
             * @param firstDType The firstD_Type
             */
            public void setFirstDType(int firstDType) {
                this.firstDType = firstDType;
            }

            /**
             * @return The lastDType
             */
            public int getLastDType() {
                return lastDType;
            }

            /**
             * @param lastDType The lastD_Type
             */
            public void setLastDType(int lastDType) {
                this.lastDType = lastDType;
            }

            /**
             * @return The complimentaryAgainst
             */
            public String getComplimentaryAgainst() {
                return validateData(complimentaryAgainst);
            }

            /**
             * @param complimentaryAgainst The complimentary_against
             */
            public void setComplimentaryAgainst(String complimentaryAgainst) {
                this.complimentaryAgainst = complimentaryAgainst;
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
             * @return The isactive
             */
            public Object getIsactive() {
                return isactive;
            }

            /**
             * @param isactive The isactive
             */
            public void setIsactive(Object isactive) {
                this.isactive = isactive;
            }

            /**
             * @return The isapproved
             */
            public int getIsapproved() {
                return isapproved;
            }

            /**
             * @param isapproved The isapproved
             */
            public void setIsapproved(int isapproved) {
                this.isapproved = isapproved;
            }

            /**
             * @return The lastupdatedon
             */
            public Object getLastupdatedon() {
                return lastupdatedon;
            }

            /**
             * @param lastupdatedon The lastupdatedon
             */
            public void setLastupdatedon(Object lastupdatedon) {
                this.lastupdatedon = lastupdatedon;
            }

            /**
             * @return The rejectionreason
             */
            public Object getRejectionreason() {
                return rejectionreason;
            }

            /**
             * @param rejectionreason The rejectionreason
             */
            public void setRejectionreason(Object rejectionreason) {
                this.rejectionreason = rejectionreason;
            }

            /**
             * @return The certificate
             */
            public Object getCertificate() {
                return certificate;
            }

            /**
             * @param certificate The certificate
             */
            public void setCertificate(Object certificate) {
                this.certificate = certificate;
            }

            /**
             * @return The clientname
             */
            public Object getClientname() {
                return clientname;
            }

            /**
             * @param clientname The clientname
             */
            public void setClientname(Object clientname) {
                this.clientname = clientname;
            }

            /**
             * @return The clientaddress
             */
            public Object getClientaddress() {
                return clientaddress;
            }

            /**
             * @param clientaddress The clientaddress
             */
            public void setClientaddress(Object clientaddress) {
                this.clientaddress = clientaddress;
            }

            /**
             * @return The department
             */
            public String getDepartment() {
                return validateData(department);
            }

            /**
             * @param department The department
             */
            public void setDepartment(String department) {
                this.department = department;
            }

            /**
             * @return The empname
             */
            public String getEmpname() {
                return validateData(empname);
            }

            /**
             * @param empname The empname
             */
            public void setEmpname(String empname) {
                this.empname = empname;
            }

            /**
             * @return The leavetype
             */
            public String getLeavetype() {
                return validateData(leavetype);
            }

            /**
             * @param leavetype The leavetype
             */
            public void setLeavetype(String leavetype) {
                this.leavetype = leavetype;
            }

            /**
             * @return The empemail
             */
            public Object getEmpemail() {
                return empemail;
            }

            /**
             * @param empemail The empemail
             */
            public void setEmpemail(Object empemail) {
                this.empemail = empemail;
            }

            /**
             * @return The managerName
             */
            public String getManagerName() {
                return validateData(managerName);
            }

            /**
             * @param managerName The managerName
             */
            public void setManagerName(String managerName) {
                this.managerName = managerName;
            }

            private String validateData(String data) {
                return data == null ? "" : data;
            }

        }
    }
}
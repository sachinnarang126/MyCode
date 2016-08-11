package xyz.truehrms.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class AllAttendance {

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

        @SerializedName("recordsTotal")
        @Expose
        private int recordsTotal;
        @SerializedName("recordsFiltered")
        @Expose
        private int recordsFiltered;
        @SerializedName("aaData")
        @Expose
        private List<AaDatum> aaData = new ArrayList<AaDatum>();
        @SerializedName("error")
        @Expose
        private Object error;

        /**
         * @return The recordsTotal
         */
        public int getRecordsTotal() {
            return recordsTotal;
        }

        /**
         * @param recordsTotal The recordsTotal
         */
        public void setRecordsTotal(int recordsTotal) {
            this.recordsTotal = recordsTotal;
        }

        /**
         * @return The recordsFiltered
         */
        public int getRecordsFiltered() {
            return recordsFiltered;
        }

        /**
         * @param recordsFiltered The recordsFiltered
         */
        public void setRecordsFiltered(int recordsFiltered) {
            this.recordsFiltered = recordsFiltered;
        }

        /**
         * @return The aaData
         */
        public List<AaDatum> getAaData() {
            return aaData;
        }

        /**
         * @param aaData The aaData
         */
        public void setAaData(List<AaDatum> aaData) {
            this.aaData = aaData;
        }

        /**
         * @return The error
         */
        public Object getError() {
            return error;
        }

        /**
         * @param error The error
         */
        public void setError(Object error) {
            this.error = error;
        }

        @Generated("org.jsonschema2pojo")
        public class AaDatum {

            @SerializedName("id")
            @Expose
            private int id;
            @SerializedName("empcode")
            @Expose
            private int empcode;
            @SerializedName("logintime")
            @Expose
            private String logintime;
            @SerializedName("logouttime")
            @Expose
            private String logouttime;
            @SerializedName("punchdate")
            @Expose
            private String punchdate;
            @SerializedName("totalhrs")
            @Expose
            private String totalhrs;
            @SerializedName("punchintime")
            @Expose
            private String punchintime;
            @SerializedName("punchouttime")
            @Expose
            private String punchouttime;


            public String getPunchintime() {
                return validateData(punchintime);
            }

            public void setPunchintime(String punchintime) {
                this.punchintime = punchintime;
            }

            public String getPunchouttime() {
                return validateData(punchouttime);
            }

            public void setPunchouttime(String punchouttime) {
                this.punchouttime = punchouttime;
            }

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
             * @return The logintime
             */
            public String getLogintime() {
                return validateData(logintime);
            }

            /**
             * @param logintime The logintime
             */
            public void setLogintime(String logintime) {
                this.logintime = logintime;
            }

            /**
             * @return The logouttime
             */
            public String getLogouttime() {
                return validateData(logouttime);
            }

            /**
             * @param logouttime The logouttime
             */
            public void setLogouttime(String logouttime) {
                this.logouttime = logouttime;
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
             * @return The totalhrs
             */
            public String getTotalhrs() {
                return validateData(totalhrs);
            }

            /**
             * @param totalhrs The totalhrs
             */
            public void setTotalhrs(String totalhrs) {
                this.totalhrs = totalhrs;
            }

            private String validateData(String data) {
                return data == null ? "" : data;
            }

        }
    }


}
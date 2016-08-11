package xyz.truehrms.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class AddComment {

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
    private List<Object> Errors = new ArrayList<>();

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
        @SerializedName("isactive")
        @Expose
        private boolean isactive;
        @SerializedName("isdeleted")
        @Expose
        private boolean isdeleted;
        @SerializedName("commentfor")
        @Expose
        private int commentfor;
        @SerializedName("emp_name")
        @Expose
        private String empName;
        @SerializedName("publiccomment")
        @Expose
        private String publiccomment;
        @SerializedName("commentby")
        @Expose
        private int commentby;
        @SerializedName("commentdate")
        @Expose
        private String commentdate;
        @SerializedName("emp_image")
        @Expose
        private String empImage;
        @SerializedName("commentdate_string")
        @Expose
        private Object commentdateString;
        @SerializedName("commentlikes")
        @Expose
        private String commentlikes;
        @SerializedName("comment_likesname")
        @Expose
        private String commentLikesname;
        @SerializedName("likeCounts")
        @Expose
        private int likeCounts;
        @SerializedName("tag")
        @Expose
        private String tag;
        @SerializedName("taggedEmployees")
        @Expose
        private String taggedEmployees;
        @SerializedName("filetitle")
        @Expose
        private String filetitle;
        @SerializedName("postedfile")
        @Expose
        private String postedfile;
        @SerializedName("fileType")
        @Expose
        private String fileType;
        @SerializedName("recerance")
        @Expose
        private int recerance;
        @SerializedName("editedon")
        @Expose
        private String editedon;

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
         * @return The commentfor
         */
        public int getCommentfor() {
            return commentfor;
        }

        /**
         * @param commentfor The commentfor
         */
        public void setCommentfor(int commentfor) {
            this.commentfor = commentfor;
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
         * @return The publiccomment
         */
        public String getPubliccomment() {
            return validateData(publiccomment);
        }

        /**
         * @param publiccomment The publiccomment
         */
        public void setPubliccomment(String publiccomment) {
            this.publiccomment = publiccomment;
        }

        /**
         * @return The commentby
         */
        public int getCommentby() {
            return commentby;
        }

        /**
         * @param commentby The commentby
         */
        public void setCommentby(int commentby) {
            this.commentby = commentby;
        }

        /**
         * @return The commentdate
         */
        public String getCommentdate() {
            return validateData(commentdate);
        }

        /**
         * @param commentdate The commentdate
         */
        public void setCommentdate(String commentdate) {
            this.commentdate = commentdate;
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
         * @return The commentdateString
         */
        public Object getCommentdateString() {
            return commentdateString;
        }

        /**
         * @param commentdateString The commentdate_string
         */
        public void setCommentdateString(Object commentdateString) {
            this.commentdateString = commentdateString;
        }

        /**
         * @return The commentlikes
         */
        public String getCommentlikes() {
            return validateData(commentlikes);
        }

        /**
         * @param commentlikes The commentlikes
         */
        public void setCommentlikes(String commentlikes) {
            this.commentlikes = commentlikes;
        }

        /**
         * @return The commentLikesname
         */
        public String getCommentLikesname() {
            return validateData(commentLikesname);
        }

        /**
         * @param commentLikesname The comment_likesname
         */
        public void setCommentLikesname(String commentLikesname) {
            this.commentLikesname = commentLikesname;
        }

        /**
         * @return The likeCounts
         */
        public int getLikeCounts() {
            return likeCounts;
        }

        /**
         * @param likeCounts The likeCounts
         */
        public void setLikeCounts(int likeCounts) {
            this.likeCounts = likeCounts;
        }

        /**
         * @return The tag
         */
        public String getTag() {
            return validateData(tag);
        }

        /**
         * @param tag The tag
         */
        public void setTag(String tag) {
            this.tag = tag;
        }

        /**
         * @return The taggedEmployees
         */
        public String getTaggedEmployees() {
            return validateData(taggedEmployees);
        }

        /**
         * @param taggedEmployees The taggedEmployees
         */
        public void setTaggedEmployees(String taggedEmployees) {
            this.taggedEmployees = taggedEmployees;
        }

        /**
         * @return The filetitle
         */
        public String getFiletitle() {
            return filetitle;
        }

        /**
         * @param filetitle The filetitle
         */
        public void setFiletitle(String filetitle) {
            this.filetitle = filetitle;
        }

        /**
         * @return The postedfile
         */
        public String getPostedfile() {
            return validateData(postedfile);
        }

        /**
         * @param postedfile The postedfile
         */
        public void setPostedfile(String postedfile) {
            this.postedfile = postedfile;
        }

        /**
         * @return The fileType
         */
        public String getFileType() {
            return validateData(fileType);
        }

        /**
         * @param fileType The fileType
         */
        public void setFileType(String fileType) {
            this.fileType = fileType;
        }

        /**
         * @return The recerance
         */
        public int getRecerance() {
            return recerance;
        }

        /**
         * @param recerance The recerance
         */
        public void setRecerance(int recerance) {
            this.recerance = recerance;
        }

        /**
         * @return The editedon
         */
        public String getEditedon() {
            return validateData(editedon);
        }

        /**
         * @param editedon The editedon
         */
        public void setEditedon(String editedon) {
            this.editedon = editedon;
        }

        private String validateData(String data) {
            return data == null ? "" : data;
        }
    }
}
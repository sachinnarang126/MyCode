package xyz.truehrms.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class CommentDelete {

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
        private Object empName;
        @SerializedName("emp_code")
        @Expose
        private int empCode;
        @SerializedName("publiccomment")
        @Expose
        private Object publiccomment;
        @SerializedName("commentby")
        @Expose
        private int commentby;
        @SerializedName("commentdate")
        @Expose
        private String commentdate;
        @SerializedName("emp_image")
        @Expose
        private Object empImage;
        @SerializedName("commentdate_string")
        @Expose
        private Object commentdateString;
        @SerializedName("commentlikes")
        @Expose
        private String commentlikes;
        @SerializedName("comment_likesname")
        @Expose
        private Object commentLikesname;
        @SerializedName("likeCounts")
        @Expose
        private int likeCounts;
        @SerializedName("tag")
        @Expose
        private String tag;
        @SerializedName("taggedEmployees")
        @Expose
        private Object taggedEmployees;
        @SerializedName("filetitle")
        @Expose
        private Object filetitle;
        @SerializedName("postedfile")
        @Expose
        private Object postedfile;
        @SerializedName("fileType")
        @Expose
        private Object fileType;
        @SerializedName("recerance")
        @Expose
        private int recerance;
        @SerializedName("editedon")
        @Expose
        private String editedon;
        @SerializedName("isCommentLike")
        @Expose
        private boolean isCommentLike;
        @SerializedName("isShowDelete")
        @Expose
        private boolean isShowDelete;

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
        public Object getEmpName() {
            return empName;
        }

        /**
         * @param empName The emp_name
         */
        public void setEmpName(Object empName) {
            this.empName = empName;
        }

        /**
         * @return The empCode
         */
        public int getEmpCode() {
            return empCode;
        }

        /**
         * @param empCode The emp_code
         */
        public void setEmpCode(int empCode) {
            this.empCode = empCode;
        }

        /**
         * @return The publiccomment
         */
        public Object getPubliccomment() {
            return publiccomment;
        }

        /**
         * @param publiccomment The publiccomment
         */
        public void setPubliccomment(Object publiccomment) {
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
            return commentdate;
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
        public Object getEmpImage() {
            return empImage;
        }

        /**
         * @param empImage The emp_image
         */
        public void setEmpImage(Object empImage) {
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
            return commentlikes;
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
        public Object getCommentLikesname() {
            return commentLikesname;
        }

        /**
         * @param commentLikesname The comment_likesname
         */
        public void setCommentLikesname(Object commentLikesname) {
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
            return tag;
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
        public Object getTaggedEmployees() {
            return taggedEmployees;
        }

        /**
         * @param taggedEmployees The taggedEmployees
         */
        public void setTaggedEmployees(Object taggedEmployees) {
            this.taggedEmployees = taggedEmployees;
        }

        /**
         * @return The filetitle
         */
        public Object getFiletitle() {
            return filetitle;
        }

        /**
         * @param filetitle The filetitle
         */
        public void setFiletitle(Object filetitle) {
            this.filetitle = filetitle;
        }

        /**
         * @return The postedfile
         */
        public Object getPostedfile() {
            return postedfile;
        }

        /**
         * @param postedfile The postedfile
         */
        public void setPostedfile(Object postedfile) {
            this.postedfile = postedfile;
        }

        /**
         * @return The fileType
         */
        public Object getFileType() {
            return fileType;
        }

        /**
         * @param fileType The fileType
         */
        public void setFileType(Object fileType) {
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
            return editedon;
        }

        /**
         * @param editedon The editedon
         */
        public void setEditedon(String editedon) {
            this.editedon = editedon;
        }

        /**
         * @return The isCommentLike
         */
        public boolean getIsCommentLike() {
            return isCommentLike;
        }

        /**
         * @param isCommentLike The isCommentLike
         */
        public void setIsCommentLike(boolean isCommentLike) {
            this.isCommentLike = isCommentLike;
        }

        /**
         * @return The isShowDelete
         */
        public boolean getIsShowDelete() {
            return isShowDelete;
        }

        /**
         * @param isShowDelete The isShowDelete
         */
        public void setIsShowDelete(boolean isShowDelete) {
            this.isShowDelete = isShowDelete;
        }

    }
}
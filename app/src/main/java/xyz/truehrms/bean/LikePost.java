package xyz.truehrms.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class LikePost {

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
        @SerializedName("postedby")
        @Expose
        private Object postedby;
        @SerializedName("emp_name")
        @Expose
        private Object empName;
        @SerializedName("emp_image")
        @Expose
        private Object empImage;
        @SerializedName("posteddate")
        @Expose
        private Object posteddate;
        @SerializedName("posted_on_string")
        @Expose
        private Object postedOnString;
        @SerializedName("postcontent")
        @Expose
        private Object postcontent;
        @SerializedName("location")
        @Expose
        private Object location;
        @SerializedName("likes")
        @Expose
        private Object likes;
        @SerializedName("likesname")
        @Expose
        private Object likesname;
        @SerializedName("likeCounts")
        @Expose
        private Object likeCounts;
        @SerializedName("tags")
        @Expose
        private Object tags;
        @SerializedName("taggedEmployees")
        @Expose
        private Object taggedEmployees;
        @SerializedName("commentCounts")
        @Expose
        private Object commentCounts;
        @SerializedName("postedfiles")
        @Expose
        private Object postedfiles;
        @SerializedName("postedFilesTitle")
        @Expose
        private Object postedFilesTitle;
        @SerializedName("isactive")
        @Expose
        private boolean isactive;
        @SerializedName("isdeleted")
        @Expose
        private boolean isdeleted;
        @SerializedName("company_id")
        @Expose
        private int companyId;
        @SerializedName("files_list")
        @Expose
        private Object filesList;
        @SerializedName("files")
        @Expose
        private Object files;
        @SerializedName("PostComments")
        @Expose
        private Object postComments;
        @SerializedName("PostFiles")
        @Expose
        private Object postFiles;
        @SerializedName("totalRecord")
        @Expose
        private int totalRecord;
        @SerializedName("isCurrentEmpLike")
        @Expose
        private boolean isCurrentEmpLike;

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
         * @return The postedby
         */
        public Object getPostedby() {
            return postedby;
        }

        /**
         * @param postedby The postedby
         */
        public void setPostedby(Object postedby) {
            this.postedby = postedby;
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
         * @return The posteddate
         */
        public Object getPosteddate() {
            return posteddate;
        }

        /**
         * @param posteddate The posteddate
         */
        public void setPosteddate(Object posteddate) {
            this.posteddate = posteddate;
        }

        /**
         * @return The postedOnString
         */
        public Object getPostedOnString() {
            return postedOnString;
        }

        /**
         * @param postedOnString The posted_on_string
         */
        public void setPostedOnString(Object postedOnString) {
            this.postedOnString = postedOnString;
        }

        /**
         * @return The postcontent
         */
        public Object getPostcontent() {
            return postcontent;
        }

        /**
         * @param postcontent The postcontent
         */
        public void setPostcontent(Object postcontent) {
            this.postcontent = postcontent;
        }

        /**
         * @return The location
         */
        public Object getLocation() {
            return location;
        }

        /**
         * @param location The location
         */
        public void setLocation(Object location) {
            this.location = location;
        }

        /**
         * @return The likes
         */
        public Object getLikes() {
            return likes;
        }

        /**
         * @param likes The likes
         */
        public void setLikes(Object likes) {
            this.likes = likes;
        }

        /**
         * @return The likesname
         */
        public Object getLikesname() {
            return likesname;
        }

        /**
         * @param likesname The likesname
         */
        public void setLikesname(Object likesname) {
            this.likesname = likesname;
        }

        /**
         * @return The likeCounts
         */
        public Object getLikeCounts() {
            return likeCounts;
        }

        /**
         * @param likeCounts The likeCounts
         */
        public void setLikeCounts(Object likeCounts) {
            this.likeCounts = likeCounts;
        }

        /**
         * @return The tags
         */
        public Object getTags() {
            return tags;
        }

        /**
         * @param tags The tags
         */
        public void setTags(Object tags) {
            this.tags = tags;
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
         * @return The commentCounts
         */
        public Object getCommentCounts() {
            return commentCounts;
        }

        /**
         * @param commentCounts The commentCounts
         */
        public void setCommentCounts(Object commentCounts) {
            this.commentCounts = commentCounts;
        }

        /**
         * @return The postedfiles
         */
        public Object getPostedfiles() {
            return postedfiles;
        }

        /**
         * @param postedfiles The postedfiles
         */
        public void setPostedfiles(Object postedfiles) {
            this.postedfiles = postedfiles;
        }

        /**
         * @return The postedFilesTitle
         */
        public Object getPostedFilesTitle() {
            return postedFilesTitle;
        }

        /**
         * @param postedFilesTitle The postedFilesTitle
         */
        public void setPostedFilesTitle(Object postedFilesTitle) {
            this.postedFilesTitle = postedFilesTitle;
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
         * @return The filesList
         */
        public Object getFilesList() {
            return filesList;
        }

        /**
         * @param filesList The files_list
         */
        public void setFilesList(Object filesList) {
            this.filesList = filesList;
        }

        /**
         * @return The files
         */
        public Object getFiles() {
            return files;
        }

        /**
         * @param files The files
         */
        public void setFiles(Object files) {
            this.files = files;
        }

        /**
         * @return The postComments
         */
        public Object getPostComments() {
            return postComments;
        }

        /**
         * @param postComments The PostComments
         */
        public void setPostComments(Object postComments) {
            this.postComments = postComments;
        }

        /**
         * @return The postFiles
         */
        public Object getPostFiles() {
            return postFiles;
        }

        /**
         * @param postFiles The PostFiles
         */
        public void setPostFiles(Object postFiles) {
            this.postFiles = postFiles;
        }

        /**
         * @return The totalRecord
         */
        public int getTotalRecord() {
            return totalRecord;
        }

        /**
         * @param totalRecord The totalRecord
         */
        public void setTotalRecord(int totalRecord) {
            this.totalRecord = totalRecord;
        }

        /**
         * @return The isCurrentEmpLike
         */
        public boolean getIsCurrentEmpLike() {
            return isCurrentEmpLike;
        }

        /**
         * @param isCurrentEmpLike The isCurrentEmpLike
         */
        public void setIsCurrentEmpLike(boolean isCurrentEmpLike) {
            this.isCurrentEmpLike = isCurrentEmpLike;
        }

        private String validateData(String data) {
            return data == null ? "" : data;
        }
    }
}
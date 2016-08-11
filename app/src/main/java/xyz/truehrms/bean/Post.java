package xyz.truehrms.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Post {

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
        @SerializedName("postedby")
        @Expose
        private String postedby;
        @SerializedName("emp_name")
        @Expose
        private String empName;
        @SerializedName("emp_image")
        @Expose
        private String empImage;
        @SerializedName("posteddate")
        @Expose
        private String posteddate;
        @SerializedName("posted_on_string")
        @Expose
        private String postedOnString;
        @SerializedName("postcontent")
        @Expose
        private String postcontent;
        @SerializedName("postcontentDesp")
        @Expose
        private String postcontentDesp;
        @SerializedName("location")
        @Expose
        private Object location;
        @SerializedName("likes")
        @Expose
        private String likes;
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
        private String commentCounts;
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
        public String getPostedby() {
            return validateData(postedby);
        }

        /**
         * @param postedby The postedby
         */
        public void setPostedby(String postedby) {
            this.postedby = postedby;
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

        public String getPostcontentDesp() {
            return validateData(postcontentDesp);
        }

        public void setPostcontentDesp(String postcontentDesp) {
            this.postcontentDesp = postcontentDesp;
        }

        /**
         * @return The posteddate
         */
        public String getPosteddate() {
            return validateData(posteddate);
        }

        /**
         * @param posteddate The posteddate
         */
        public void setPosteddate(String posteddate) {
            this.posteddate = posteddate;
        }

        /**
         * @return The postedOnString
         */
        public String getPostedOnString() {
            return validateData(postedOnString);
        }

        /**
         * @param postedOnString The posted_on_string
         */
        public void setPostedOnString(String postedOnString) {
            this.postedOnString = postedOnString;
        }

        /**
         * @return The postcontent
         */
        public String getPostcontent() {
            return validateData(postcontent);
        }

        /**
         * @param postcontent The postcontent
         */
        public void setPostcontent(String postcontent) {
            this.postcontent = postcontent;
        }

        /**
         * @return The location
         */
        public Object getLocation() {
            return validateData(location);
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
        public String getLikes() {
            return validateData(likes);
        }

        /**
         * @param likes The likes
         */
        public void setLikes(String likes) {
            this.likes = likes;
        }

        /**
         * @return The likesname
         */
        public Object getLikesname() {
            return validateData(likesname);
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
            return validateData(likeCounts);
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
            return validateData(tags);
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
            return validateData(taggedEmployees);
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
        public String getCommentCounts() {
            return validateData(commentCounts);
        }

        /**
         * @param commentCounts The commentCounts
         */
        public void setCommentCounts(String commentCounts) {
            this.commentCounts = commentCounts;
        }

        /**
         * @return The postedfiles
         */
        public Object getPostedfiles() {
            return validateData(postedfiles);
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
            return validateData(postedFilesTitle);
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
            return validateData(filesList);
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
            return validateData(postComments);
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
            return validateData(postFiles);
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

        private Object validateData(Object data) {
            return data == null ? "" : data;
        }
    }
}
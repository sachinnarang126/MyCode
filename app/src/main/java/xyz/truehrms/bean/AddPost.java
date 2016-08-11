package xyz.truehrms.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class AddPost {

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
        private Object empImage;
        @SerializedName("posteddate")
        @Expose
        private String posteddate;
        @SerializedName("posted_on_string")
        @Expose
        private Object postedOnString;
        @SerializedName("postcontent")
        @Expose
        private String postcontent;
        @SerializedName("location")
        @Expose
        private String location;
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
        private String tags;
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
        private Object PostComments;
        @SerializedName("PostFiles")
        @Expose
        private Object PostFiles;

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
            return empName;
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
        public String getLocation() {
            return validateData(location);
        }

        /**
         * @param location The location
         */
        public void setLocation(String location) {
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
        public String getTags() {
            return validateData(tags);
        }

        /**
         * @param tags The tags
         */
        public void setTags(String tags) {
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
         * @return The PostComments
         */
        public Object getPostComments() {
            return PostComments;
        }

        /**
         * @param PostComments The PostComments
         */
        public void setPostComments(Object PostComments) {
            this.PostComments = PostComments;
        }

        /**
         * @return The PostFiles
         */
        public Object getPostFiles() {
            return PostFiles;
        }

        /**
         * @param PostFiles The PostFiles
         */
        public void setPostFiles(Object PostFiles) {
            this.PostFiles = PostFiles;
        }

        private String validateData(String data) {
            return data == null ? "" : data;
        }

    }
}
package xyz.truehrms.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;


@Generated("org.jsonschema2pojo")
public class CommentLikeList {

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
    public static class Result {

        @SerializedName("commentList")
        @Expose
        private List<CommentList> commentList = new ArrayList<CommentList>();
        @SerializedName("likeList")
        @Expose
        private List<LikeList> likeList = new ArrayList<LikeList>();

        /**
         * @return The commentList
         */
        public List<CommentList> getCommentList() {
            return commentList == null ? new ArrayList<CommentList>() : commentList;
        }

        /**
         * @param commentList The commentList
         */
        public void setCommentList(List<CommentList> commentList) {
            this.commentList = commentList;
        }

        /**
         * @return The likeList
         */
        public List<LikeList> getLikeList() {
            return likeList == null ? new ArrayList<LikeList>() : likeList;
        }

        /**
         * @param likeList The likeList
         */
        public void setLikeList(List<LikeList> likeList) {
            this.likeList = likeList;
        }

        @Generated("org.jsonschema2pojo")
        public class CommentList {

            @SerializedName("commentId")
            @Expose
            private int commentId;
            @SerializedName("comment")
            @Expose
            private String comment;
            @SerializedName("employee_id")
            @Expose
            private int empId;
            @SerializedName("empName")
            @Expose
            private String empName;
            @SerializedName("empImage")
            @Expose
            private String empImage;
            @SerializedName("commentDate")
            @Expose
            private String commentDate;

            /**
             * @return The commentId
             */
            public int getCommentId() {
                return commentId;
            }

            /**
             * @param commentId The commentId
             */
            public void setCommentId(int commentId) {
                this.commentId = commentId;
            }

            /**
             * @return The comment
             */
            public String getComment() {
                return validateData(comment);
            }

            /**
             * @param comment The comment
             */
            public void setComment(String comment) {
                this.comment = comment;
            }

            /**
             * @return The empId
             */
            public int getEmpId() {
                return empId;
            }

            /**
             * @param empId The empId
             */
            public void setEmpId(int empId) {
                this.empId = empId;
            }

            /**
             * @return The empName
             */
            public String getEmpName() {
                return validateData(empName);
            }

            /**
             * @param empName The empName
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
             * @param empImage The empImage
             */
            public void setEmpImage(String empImage) {
                this.empImage = empImage;
            }

            /**
             * @return The commentDate
             */
            public String getCommentDate() {
                return validateData(commentDate);
            }

            /**
             * @param commentDate The commentDate
             */
            public void setCommentDate(String commentDate) {
                this.commentDate = commentDate;
            }

            private String validateData(String data) {
                return data == null ? "" : data;
            }

        }

        @Generated("org.jsonschema2pojo")
        public class LikeList {

            @SerializedName("empId")
            @Expose
            private int empId;
            @SerializedName("empName")
            @Expose
            private String empName;
            @SerializedName("empImage")
            @Expose
            private String empImage;

            /**
             * @return The empId
             */
            public int getEmpId() {
                return empId;
            }

            /**
             * @param empId The empId
             */
            public void setEmpId(int empId) {
                this.empId = empId;
            }

            /**
             * @return The empName
             */
            public String getEmpName() {
                return validateData(empName);
            }

            /**
             * @param empName The empName
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
             * @param empImage The empImage
             */
            public void setEmpImage(String empImage) {
                this.empImage = empImage;
            }

            private String validateData(String data) {
                return data == null ? "" : data;
            }
        }
    }
}


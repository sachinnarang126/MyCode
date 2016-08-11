package xyz.truehrms.parameters;

public class AddComment {
    private String postid, commentcontent, postedfiles, postedFilesTitle, commentby;

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getCommentcontent() {
        return commentcontent;
    }

    public void setCommentcontent(String commentcontent) {
        this.commentcontent = commentcontent;
    }

    public String getPostedfiles() {
        return postedfiles;
    }

    public void setPostedfiles(String postedfiles) {
        this.postedfiles = postedfiles;
    }

    public String getPostedFilesTitle() {
        return postedFilesTitle;
    }

    public void setPostedFilesTitle(String postedFilesTitle) {
        this.postedFilesTitle = postedFilesTitle;
    }

    public String getCommentby() {
        return commentby;
    }

    public void setCommentby(String commentby) {
        this.commentby = commentby;
    }
}

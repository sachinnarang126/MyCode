package xyz.truehrms.parameters;

public class AddComment {
    private String postid, commentcontent, postedfiles, postedFilesTitle, commentby;

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public void setCommentcontent(String commentcontent) {
        this.commentcontent = commentcontent;
    }

    public void setPostedfiles(String postedfiles) {
        this.postedfiles = postedfiles;
    }

    public void setPostedFilesTitle(String postedFilesTitle) {
        this.postedFilesTitle = postedFilesTitle;
    }

    public void setCommentby(String commentby) {
        this.commentby = commentby;
    }
}

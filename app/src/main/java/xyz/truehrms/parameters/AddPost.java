package xyz.truehrms.parameters;

public class AddPost {
    private String postedby;
    private String postcontent, company_id, postedfiles, postcontentDesp;

    public String getPostcontentDesp() {
        return postcontentDesp;
    }

    public void setPostcontentDesp(String postcontentDesp) {
        this.postcontentDesp = postcontentDesp;
    }

    public String getPostedby() {
        return postedby;
    }

    public void setPostedby(String postedby) {
        this.postedby = postedby;
    }

    public String getPostcontent() {
        return postcontent;
    }

    public void setPostcontent(String postcontent) {
        this.postcontent = postcontent;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getPostedfiles() {
        return postedfiles;
    }

    public void setPostedfiles(String postedfiles) {
        this.postedfiles = postedfiles;
    }
}

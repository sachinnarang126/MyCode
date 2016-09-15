package xyz.truehrms.parameters;

public class AddPost {
    private String postedby,VisibleTo;
    private String postcontent, company_id, postcontentDesp;

    public void setPostcontentDesp(String postcontentDesp) {
        this.postcontentDesp = postcontentDesp;
    }

    public void setVisibleTo(String visibleTo) {
        VisibleTo = visibleTo;
    }

    public void setPostedby(String postedby) {
        this.postedby = postedby;
    }

    public void setPostcontent(String postcontent) {
        this.postcontent = postcontent;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

}

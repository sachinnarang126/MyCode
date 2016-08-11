package xyz.truehrms.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ApproveRequestAttendance {

    @SerializedName("StatusCode")
    @Expose
    private float StatusCode;
    @SerializedName("Version")
    @Expose
    private String Version;
    @SerializedName("Result")
    @Expose
    private Object Result;
    @SerializedName("Errors")
    @Expose
    private List<String> Errors = new ArrayList<String>();

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
        return validateData(Version);
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
    public Object getResult() {
        return Result;
    }

    /**
     * @param Result The Result
     */
    public void setResult(Object Result) {
        this.Result = Result;
    }

    /**
     * @return The Errors
     */
    public List<String> getErrors() {
        return Errors;
    }

    /**
     * @param Errors The Errors
     */
    public void setErrors(List<String> Errors) {
        this.Errors = Errors;
    }

    private String validateData(String data) {
        return data == null ? "" : data;
    }

}
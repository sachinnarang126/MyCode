package xyz.truehrms.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class TokenData {

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

        @SerializedName("TokenId")
        @Expose
        private String TokenId;
        @SerializedName("Token")
        @Expose
        private String Token;
        @SerializedName("IssuedOn")
        @Expose
        private String IssuedOn;
        @SerializedName("ExpiresOn")
        @Expose
        private String ExpiresOn;
        @SerializedName("Userid")
        @Expose
        private String Userid;

        /**
         * @return The TokenId
         */
        public String getTokenId() {
            return validateData(TokenId);
        }

        /**
         * @param TokenId The TokenId
         */
        public void setTokenId(String TokenId) {
            this.TokenId = TokenId;
        }

        /**
         * @return The Token
         */
        public String getToken() {
            return validateData(Token);
        }

        /**
         * @param Token The Token
         */
        public void setToken(String Token) {
            this.Token = Token;
        }

        /**
         * @return The IssuedOn
         */
        public String getIssuedOn() {
            return validateData(IssuedOn);
        }

        /**
         * @param IssuedOn The IssuedOn
         */
        public void setIssuedOn(String IssuedOn) {
            this.IssuedOn = IssuedOn;
        }

        /**
         * @return The ExpiresOn
         */
        public String getExpiresOn() {
            return validateData(ExpiresOn);
        }

        /**
         * @param ExpiresOn The ExpiresOn
         */
        public void setExpiresOn(String ExpiresOn) {
            this.ExpiresOn = ExpiresOn;
        }

        /**
         * @return The Userid
         */
        public String getUserid() {
            return validateData(Userid);
        }

        /**
         * @param Userid The Userid
         */
        public void setUserid(String Userid) {
            this.Userid = Userid;
        }

        private String validateData(String data) {
            return data == null ? "" : data;
        }
    }
}

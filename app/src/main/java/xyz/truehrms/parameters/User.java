package xyz.truehrms.parameters;

public class User {

    private String Username,user_email,ip_address, Password,token,mac_address,user_agent;
    private String DeviceType;
    private String AccountNumber;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMac_address() {
        return mac_address;
    }

    public void setMac_address(String mac_address) {
        this.mac_address = mac_address;
    }

    public String getUser_agent() {
        return user_agent;
    }

    public void setUser_agent(String user_agent) {
        this.user_agent = user_agent;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(String deviceType) {
        DeviceType = deviceType;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }


}

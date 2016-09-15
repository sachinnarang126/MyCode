package xyz.truehrms.parameters;

public class User {

    private String Username, user_email, ip_address, Password, token, mac_address, user_agent;
    private String DeviceType;
    private String AccountNumber;

    public void setUsername(String username) {
        Username = username;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setMac_address(String mac_address) {
        this.mac_address = mac_address;
    }

    public void setUser_agent(String user_agent) {
        this.user_agent = user_agent;
    }

    public void setDeviceType(String deviceType) {
        DeviceType = deviceType;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }
}

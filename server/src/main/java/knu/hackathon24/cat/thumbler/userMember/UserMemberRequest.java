package knu.hackathon24.cat.thumbler.userMember;

public class UserMemberRequest {
    private String name;
    private String phone;
    private String nickname;
    private String userId;
    private String password;
    private String bank;
    private String account;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getBank() { return bank; }
    public void setBank(String bank) { this.bank = bank; }

    public String getAccount() { return account; }
    public void setAccount(String account) { this.account = account; }
}

class UserMemberResponse {
    private String nickname;
    private String userId;

    public UserMemberResponse(String nickname, String userId) {
        this.nickname = nickname;
        this.userId = userId;
    }

    // Getters
    public String getNickname() { return nickname; }
    public String getUserId() { return userId; }
}
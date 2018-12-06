package e.a2727.project;

import com.google.firebase.database.IgnoreExtraProperties;
//UserProfile을 데이터베이스에 저장하거나 꺼낼 때 사용한다.
@IgnoreExtraProperties
public class UserProfile {
    //유저 이름과 이메일을 대상으로 저장
    public String userName;
    public String userEmail;

    //아무것도 없으면 아무것도 없음
    public UserProfile(){
    }

    //그러나 Parameter를 저렇게 두 개 주면 두 개 입력해줌
    public UserProfile(String userEmail, String userName) {
        this.userEmail = userEmail;
        this.userName = userName;
    }

    //과거 자바 handler처럼 캡슐화 쓰듯이 get과 set을 이용하여 각각의 값을 전달하거나 전달받음
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
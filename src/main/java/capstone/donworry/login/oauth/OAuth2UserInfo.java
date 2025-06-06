package capstone.donworry.login.oauth;

public interface OAuth2UserInfo {
    String getProvider();
    String getId();
    String getName();
}

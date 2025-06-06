package capstone.donworry.login.oauth;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class KakaoOAuth2UserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }


    @Override
    public String getName() {
        return (String) ((Map) attributes.get("properties")).get("nickname");
    }
}




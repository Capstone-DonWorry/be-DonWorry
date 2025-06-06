package capstone.donworry.login.oauth;

import capstone.donworry.login.common.CustomUserDetails;
import capstone.donworry.login.jwt.JwtUtil;
import capstone.donworry.member.domain.Member;
import capstone.donworry.member.domain.MemberRole;
import capstone.donworry.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    private final Map<String, Function<Map<String, Object>, OAuth2UserInfo>> userInfoFactory = Map.of(
            "kakao", KakaoOAuth2UserInfo::new
    );

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = userInfoFactory.getOrDefault(provider, attrs -> {
            throw new OAuth2AuthenticationException("지원하지 않는 provider: " + provider);
        }).apply(oAuth2User.getAttributes());

        String loginId = generateLoginId(oAuth2UserInfo);
        String name = oAuth2UserInfo.getName();

        Member member = memberRepository.findByLoginId(loginId);

        if (member == null) {
            member = Member.builder()
                    .loginId(loginId)
                    .name(name)
                    .role(MemberRole.USER)
                    .provider(provider)
                    .build();
            memberRepository.save(member);
        }
        String token = jwtUtil.generateToken(member.getLoginId(), member.getRole().name());

        return new CustomUserDetails(member, oAuth2User.getAttributes(), token);
    }

    private String generateLoginId(OAuth2UserInfo userInfo) {
        return userInfo.getProvider() + "_" + userInfo.getId();
    }
}

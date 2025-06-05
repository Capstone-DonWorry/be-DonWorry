package capstone.donworry.login.common;

import capstone.donworry.member.domain.Member;
import capstone.donworry.member.domain.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private String loginId;
    private String name;
    private MemberRole role;
    private String nickname;
    private Long goalAmount;

    public static LoginResponseDTO from(Member member, String token) {
        return new LoginResponseDTO(
                token,
                member.getLoginId(),
                member.getName(),
                member.getRole(),
                member.getNickname(),
                member.getGoalAmount()
        );
    }
}


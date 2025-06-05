package capstone.donworry.member.dto;

import capstone.donworry.member.domain.Member;
import capstone.donworry.member.domain.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponseDTO {
    private String loginId;
    private String name;
    private String nickname;
    private String ageGroup;
    private Long goalAmount;
    private String phoneNumber;
    private MemberRole role;

    public static MemberResponseDTO from(Member member, Long goalAmount) {
        return new MemberResponseDTO(
                member.getLoginId(),
                member.getName(),
                member.getNickname(),
                member.getAgeGroup(),
                goalAmount,
                member.getPhoneNumber(),
                member.getRole()
        );
    }
}


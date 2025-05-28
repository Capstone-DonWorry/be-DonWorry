package capstone.donworry.member.dto;

import capstone.donworry.member.domain.Member;
import capstone.donworry.member.domain.MemberRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberJoinRequestDTO {
    private String loginId;

    private String password;
    private String passwordCheck;

    private String name;
    private String phoneNumber;
    private Long goalAmount;
    private String ageGroup;
    private String nickname;

    public Member toEntity() {
        return Member.builder()
                .loginId(this.loginId)
                .password(this.password)
                .name(this.name)
                .phoneNumber(this.phoneNumber)
                .goalAmount(this.goalAmount)
                .ageGroup(this.ageGroup)
                .nickname(this.nickname)
                .role(MemberRole.USER)
                .build();
    }
}

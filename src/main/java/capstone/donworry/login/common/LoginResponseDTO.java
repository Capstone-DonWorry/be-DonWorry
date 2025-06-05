package capstone.donworry.login.common;

import capstone.donworry.member.domain.Member;
import capstone.donworry.member.domain.MemberRole;
import capstone.donworry.monthlyExpenseGoals.domain.MonthlyExpenseGoal;
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
    private Long monthGoal;

    public static LoginResponseDTO from(Member member, String token, MonthlyExpenseGoal monthlyExpenseGoal) {
        return new LoginResponseDTO(
                token,
                member.getLoginId(),
                member.getName(),
                member.getRole(),
                member.getNickname(),
                monthlyExpenseGoal.getGoalAmount()
        );
    }
}


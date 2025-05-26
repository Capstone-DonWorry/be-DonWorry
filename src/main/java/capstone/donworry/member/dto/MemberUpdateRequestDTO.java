package capstone.donworry.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberUpdateRequestDTO {
    private String name;
    private String nickname;
    private String ageGroup;
    private Long goalAmount;
    private String phoneNumber;


}

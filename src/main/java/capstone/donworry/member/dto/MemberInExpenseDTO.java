package capstone.donworry.member.dto;

import capstone.donworry.member.domain.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberInExpenseDTO {
    private Long id;
    private String username;


    public static MemberInExpenseDTO from(Member member) {
        MemberInExpenseDTO dto = new MemberInExpenseDTO();
        dto.setId(member.getId());
        dto.setUsername(member.getName());
        return dto;
    }
}

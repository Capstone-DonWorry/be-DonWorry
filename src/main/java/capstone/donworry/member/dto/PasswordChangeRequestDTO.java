package capstone.donworry.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PasswordChangeRequestDTO {

    private String currentPassword;
    private String newPassword;
}
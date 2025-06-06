package capstone.donworry.member.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    private String loginId;
    private String password;
    private String name;
    private String nickname;
    private String ageGroup;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    // oauth
    private String provider;

    public void updateInfo(String name, String nickname, String ageGroup,
                           String phoneNumber) {
        this.name = name;
        this.nickname = nickname;
        this.ageGroup = ageGroup;
        this.phoneNumber = phoneNumber;
    }

}

package capstone.donworry.login.jwt;

import capstone.donworry.global.response.DataResponseDTO;
import capstone.donworry.global.response.ErrorCode;
import capstone.donworry.global.response.ResponseDTO;
import capstone.donworry.login.common.LoginRequestDTO;
import capstone.donworry.login.common.LoginResponseDTO;
import capstone.donworry.member.domain.Member;
import capstone.donworry.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jwt")
public class JwtLoginController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        Member member = memberService.getLoginMemberByLoginId(loginRequestDTO.getLoginId());

        if (member == null || !memberService.checkPassword(loginRequestDTO.getPassword(), member.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ResponseDTO(
                            ErrorCode.UNAUTHORIZED.getStatus(),
                            ErrorCode.UNAUTHORIZED.getHttpStatus(),
                            "아이디 또는 비밀번호가 틀렸습니다."
                    )
            );
        }

        String token = jwtUtil.generateToken(member.getLoginId(), member.getRole().name());

        LoginResponseDTO responseDTO = LoginResponseDTO.from(member, token);

        return ResponseEntity.ok(DataResponseDTO.success(responseDTO));
    }
}


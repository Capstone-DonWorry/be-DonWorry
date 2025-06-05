package capstone.donworry.member.service;
import capstone.donworry.login.common.LoginRequestDTO;
import capstone.donworry.member.domain.Member;
import capstone.donworry.member.dto.MemberJoinRequestDTO;
import capstone.donworry.member.dto.MemberUpdateRequestDTO;
import capstone.donworry.member.dto.PasswordChangeRequestDTO;
import capstone.donworry.member.repository.MemberRepository;
import capstone.donworry.monthlyExpenseGoals.domain.MonthlyExpenseGoal;
import capstone.donworry.monthlyExpenseGoals.repository.MonthlyExpenseGoalRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.YearMonth;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MonthlyExpenseGoalRepository monthlyExpenseGoalRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    @Transactional
    public void join(MemberJoinRequestDTO memberJoinRequestDTO){
        if (checkLoginIdDuplicate(memberJoinRequestDTO.getLoginId())) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }

        Member member = memberJoinRequestDTO.toEntity();
        String encodedPassword = bCryptPasswordEncoder.encode(memberJoinRequestDTO.getPassword());
        member.setPassword(encodedPassword);
        memberRepository.save(member);

        YearMonth now = YearMonth.now();
        MonthlyExpenseGoal monthlyExpenseGoal = new MonthlyExpenseGoal(
                member,
                now.getYear(),
                now.getMonthValue(),
                memberJoinRequestDTO.getGoalAmount()
        );
        monthlyExpenseGoalRepository.save(monthlyExpenseGoal);
    }

    @Transactional
    public Member login(LoginRequestDTO loginRequest) {
  
        Member findMember = memberRepository.findByLoginId(loginRequest.getLoginId());

        if (findMember == null || !bCryptPasswordEncoder.matches(loginRequest.getPassword(), findMember.getPassword())) {
            throw new RuntimeException("아이디 또는 비밀번호가 틀렸습니다.");
        }

        return findMember;
    }

    @Transactional
    public Member updateMember(Long memberId, MemberUpdateRequestDTO request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        member.updateInfo(
                request.getName(),
                request.getNickname(),
                request.getAgeGroup(),
                request.getPhoneNumber()
        );

        if (request.getGoalAmount() != null) {
            YearMonth now = YearMonth.now();
            MonthlyExpenseGoal monthlyExpenseGoal = monthlyExpenseGoalRepository
                    .findByMemberIdAndYearAndMonth(memberId, now.getYear(), now.getMonthValue())
                    .orElse(null);

            if (monthlyExpenseGoal == null) {
                monthlyExpenseGoal = new MonthlyExpenseGoal(member, now.getYear(), now.getMonthValue(), request.getGoalAmount());
            } else {
                monthlyExpenseGoal.setGoalAmount(request.getGoalAmount());
            }

            monthlyExpenseGoalRepository.save(monthlyExpenseGoal);
        }

        return member;
    }

    @Transactional
    public void deleteMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new EntityNotFoundException("회원이 존재하지 않습니다.");
        }
        memberRepository.deleteById(memberId);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }

    @Transactional
    public void changePassword(Long memberId, PasswordChangeRequestDTO request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));


        if (!bCryptPasswordEncoder.matches(request.getCurrentPassword(), member.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        // 새 비밀번호 인코딩 후 저장
        String encodedNewPassword = bCryptPasswordEncoder.encode(request.getNewPassword());
        member.setPassword(encodedNewPassword);
    }


    public boolean checkLoginIdDuplicate(String loginId){

        return memberRepository.existsByLoginId(loginId);
    }


    public Member getLoginMemberByLoginId(String loginId){
        if (loginId == null) throw new IllegalArgumentException("loginId가 null입니다.");
        return memberRepository.findByLoginId(loginId);
    }


}
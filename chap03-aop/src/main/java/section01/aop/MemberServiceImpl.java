package section01.aop;

import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {


    @Override
    public String processMember(boolean error) {
        System.out.println("[Target] 회원처리");
        if (error){
            throw new IllegalStateException();
        }
        return "회원 처리 완료";
    }

    @Override
    public void selectMember() {
        System.out.println("[selectMember] 회원처리");
    }

    @Override
    public void selectMembers() {
        System.out.println("[selectMembers] 회원처리");
    }

    @Override
    public void checkMember() {
        System.out.println("[checkMember] 회원처리");
    }

    @Override
    @AopLog
    public void registerMember(String name, int age) {
        System.out.println("[registerMember] 회원처리");
    }

    @Override
    @AopLog
    public void updateMember() {
        System.out.println("[updateMember] 회원처리");
    }
}

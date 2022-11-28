package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
public class MemberService {


    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    //reason that we are using interface than the typical MemoryMemberRepository class is that it is better for us to implement with
    //using abstract method so we could change or inherit other class in the future.

    /*sign up*/
    public Long join(Member member){
        //no duplicate member
        validateDuplicateMember(member); //duplicate member checking
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
        .ifPresent(m -> { //this is possible because findByname is optional already.
            throw new IllegalStateException("Already exists member");
        });
    }

    //looking up all members
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}

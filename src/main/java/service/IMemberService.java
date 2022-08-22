package service;

import dto.Member;

public interface IMemberService {
   Member getMemberLogin(String id, String pw);
   int insertMember(Member member);

}

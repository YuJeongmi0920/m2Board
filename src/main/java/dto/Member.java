package dto;

public class Member {
    private String memberId;
    private String memberPw;
    private int memberLevel;
    private String memberName;
    private String memberAddr;
    private String memberDetailAddr;

    public String getMemberAddr() {
        return memberAddr;
    }

    public void setMemberAddr(String memberAddr) {
        this.memberAddr = memberAddr;
    }

    public String getMemberDetailAddr() {
        return memberDetailAddr;
    }

    public void setMemberDetailAddr(String memberDetailAddr) {
        this.memberDetailAddr = memberDetailAddr;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberPw() {
        return memberPw;
    }

    public void setMemberPw(String memberPw) {
        this.memberPw = memberPw;
    }

    public int getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(int memberLevel) {
        this.memberLevel = memberLevel;
    }


    @Override
    public String toString() {
        return "Member{" +
                "memberId='" + memberId + '\'' +
                ", memberPw='" + memberPw + '\'' +
                ", memberLevel=" + memberLevel +
                ", memberName='" + memberName + '\'' +
                ", memberAddr='" + memberAddr + '\'' +
                ", memberDetailAddr='" + memberDetailAddr + '\'' +
                '}';
    }
}

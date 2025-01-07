package spring;

import java.time.LocalDateTime;

/**
 * class         : MemberRegisterService
 * date          : 25-01-02
 * description   : 회원 등록 서비스 클래스. 새로운 회원을 데이터베이스에 추가하는 기능 제공.
 */

public class MemberRegisterService {
	private MemberDao memberDao;

	/**
	 * method        : MemberRegisterService
	 * date          : 25-01-02
	 */
	public MemberRegisterService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	/**
	 * method        : regist
	 * date          : 25-01-02
	 * param         : RegisterRequest req - 등록 요청 데이터 객체
	 * return        : Long - 등록된 회원의 ID
	 * description   : 주어진 이메일로 기존 회원을 조회하고, 중복이 없을 경우 새로운 회원을 등록.
	 *                 등록된 회원 객체의 ID를 반환하며, 중복된 이메일인 경우 예외를 발생.
	 */
	public Long regist(RegisterRequest req) {
		Member member = memberDao.selectByEmail(req.getEmail());
		if (member != null) {
			throw new DuplicateMemberException("dup email " + req.getEmail());
		}
		Member newMember = new Member(
				req.getEmail(), req.getPassword(), req.getName(), 
				LocalDateTime.now());
		memberDao.insert(newMember);
		return newMember.getId();
	}
}

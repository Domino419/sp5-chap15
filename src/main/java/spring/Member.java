package spring;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore ;

public class Member {

	private Long id;
	private String email;
	@JsonIgnore
	private String password;
	private String name;
//	@JsonFormat(shape = JsonFormat.Shape.STRING)  // 2024-12-19T23:56:34
//	@JsonFormat(pattern = "yyyyMMddHHmmss")       // 20241219235634
	private LocalDateTime registerDateTime;


	public Member(String email, String password, 
			String name, LocalDateTime regDateTime) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.registerDateTime = regDateTime;
	}

	void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public LocalDateTime getRegisterDateTime() {
		return registerDateTime;
	}

	public void changePassword(String oldPassword, String newPassword) {
		if (!password.equals(oldPassword))
			throw new WrongIdPasswordException();
		this.password = newPassword;
	}

	/**
	 * method        : matchPassword
	 * date          : 25-01-02
	 * param         : String password - 비교할 비밀번호
	 * return        : Boolean - 비밀번호가 일치하면 true, 그렇지 않으면 false
	 * description   : 현재 객체의 비밀번호와 주어진 비밀번호를 비교하여 일치 여부를 반환한다.
	 * (sp-chap13.354page)
	 */
	public Boolean matchPassword(String password) {
		return this.password.equals(password);
	}

}

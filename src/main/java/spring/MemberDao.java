package spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


/**
 * class         : MemberDao
 * date          : 25-01-06
 * description   : 데이터베이스의 MEMBER 테이블을 관리하는 DAO 클래스
 */
public class MemberDao {

	private JdbcTemplate jdbcTemplate;


	/**
	 * memberRowMapper : ResultSet에서 Member 객체를 생성하는 RowMapper 구현체
	 * date           : 2025-01-07
	 * description    : 데이터베이스에서 조회한 결과(ResultSet)를 Member 객체로 매핑하는 RowMapper
	 */
	private RowMapper<Member> memberRowMapper =
			new RowMapper<Member>() {
				@Override
				public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
					Member member = new Member(
							rs.getString("EMAIL"),
							rs.getString("PASSWORD"),
							rs.getString("NAME"),
							rs.getTimestamp("REGDATE").toLocalDateTime());
					member.setId(rs.getLong("ID"));
					return member;
				}
			} ;

	public MemberDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * method        : selectByEmail
	 * date          : 25-01-06
	 * return        : Member - 이메일로 조회한 회원 정보 (없을 경우 null 반환)
	 */
	public Member selectByEmail(String email) {
		List<Member> results = jdbcTemplate.query(
				"select * from MEMBER where EMAIL = ?",memberRowMapper, email);
		return results.isEmpty() ? null : results.get(0);
	}


	/**
	 * method        : insert
	 * date          : 25-01-06
	 * return        : void
	 * description   : 새로운 회원 정보를 MEMBER 테이블에 삽입하며, 생성된 ID를 설정
	 */
	public void insert(Member member) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				// 파라미터로 전달받은 Connection을 이용해서 PreparedStatement 생성
				PreparedStatement pstmt = con.prepareStatement(
						"insert into MEMBER (EMAIL, PASSWORD, NAME, REGDATE) " +
						"values (?, ?, ?, ?)",
						new String[] { "ID" });
				// 인덱스 파라미터 값 설정
				pstmt.setString(1, member.getEmail());
				pstmt.setString(2, member.getPassword());
				pstmt.setString(3, member.getName());
				pstmt.setTimestamp(4,
						Timestamp.valueOf(member.getRegisterDateTime()));
				// 생성한 PreparedStatement 객체 리턴
				return pstmt;
			}
		}, keyHolder);
		Number keyValue = keyHolder.getKey();
		member.setId(keyValue.longValue());
	}

	/**
	 * method        : update
	 * date          : 25-01-06
	 * return        : void
	 * description   : 기존 회원의 이름과 비밀번호를 업데이트
	 */
	public void update(Member member) {
		jdbcTemplate.update(
				"update MEMBER set NAME = ?, PASSWORD = ? where EMAIL = ?",
				member.getName(), member.getPassword(), member.getEmail());
	}

	/**
	 * method        : selectAll
	 * date          : 25-01-06
	 * return        : List<Member> - 모든 회원 정보를 리스트로 반환
	 */
	public List<Member> selectAll() {
		List<Member> results = jdbcTemplate.query("select * from MEMBER",memberRowMapper);
		return results;
	}

	/**
	 * method        : count
	 * date          : 25-01-06
	 * return        : int - MEMBER 테이블에 존재하는 회원 수
	 */
	public int count() {
		Integer count = jdbcTemplate.queryForObject(
				"select count(*) from MEMBER", Integer.class);
		return count;
	}

	/**
	 * method        : selectByRegdate
	 * date          : 25-01-06
	 * return        : List<Member> - 특정 기간 내 회원 정보를 리스트로 반환
	 */
	public List<Member> selectByRegdate(LocalDateTime from, LocalDateTime to) {
		List<Member> results = jdbcTemplate.query(
				"select * from MEMBER where REGDATE between ? and ? order by REGDATE desc",
				memberRowMapper,
				from, to );
		return results;
	}

	/**
	 * method        : selectById
	 * date          : 25-01-07
	 * return        : Member - 주어진 ID에 해당하는 회원 정보를 반환. 회원이 존재하지 않으면 null을 반환.
	 */
	public Member selectById(Long memId) {
		List<Member> results = jdbcTemplate.query(
				"select * from MEMBER where ID = ?",
				memberRowMapper, memId);
		return results.isEmpty() ? null : results.get(0);
	}


}

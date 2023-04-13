package ezenweb.web.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

// 시큐리티 + 일반 DTO
// 시큐리티[UserDetails] + 소셜 회원 [OAuth2User]
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto implements UserDetails, OAuth2User { //MemberService 의 loadUserByUsername메서드에서 UserDetails를 반환하기 위해

    //1. 회원번호
    private int mno;
    //2. 회원 아이디 [이메일]
    private String memail;
    //3. 회원 비밀번호
    private String mpassword;
    //4. 회원 이름
    private String mname;
    //5. 회원 전화번호
    private String mphone;
    //6. 회원 등급 / 권한 명
    private String mrole; // [가입용]
    
    private Set<GrantedAuthority> rolesList; // 인증용
    private Map<String, Object> attribute; //Oauth2 회원의 소셜 회원 정보

    private LocalDateTime cdate; //등록 날짜/시간
    private LocalDateTime udate; //수정 날짜/시간

    //toEntity
    public MemberEntity toEntity(){ //저장용도
        return MemberEntity.builder()
              .mno(this.mno)
              .memail(this.memail)
              .mpassword(this.mpassword)
              .mname(this.mname)
              .mphone(this.mphone)
              .mrole(this.getMrole()) // 넣어야 DB처리가 가능하다.
              .build();
    }

    /* -------------------------- USERDETAILS -------------------------- */
    @Override //인증 권한 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.rolesList;
    }

    @Override //패스워드 반환
    public String getPassword() {
        return this.mpassword;
    }

    @Override // 계정 반환
    public String getUsername() {
        return this.memail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /* -------------------------- OAuth2User -------------------------- */
    @Override //Oauth2 회원 정보
    public Map<String, Object> getAttributes() {
        return this.attribute;
    }

    @Override //Oauth2  아이디
    public String getName() {
        return this.memail;
    }
}

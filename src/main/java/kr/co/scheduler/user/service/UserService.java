package kr.co.scheduler.user.service;

import kr.co.scheduler.user.dtos.UserReqDTO;
import kr.co.scheduler.user.entity.User;
import kr.co.scheduler.user.repository.UserRepository;
import kr.co.scheduler.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    @Transactional
    public void signUp(UserReqDTO userReqDTO) throws Exception {

        if(userRepository.findByEmail(userReqDTO.getEmail()).isPresent()){
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if(!userReqDTO.getPassword().equals(userReqDTO.getCheckedPassword())){
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        User user = userRepository.save(userReqDTO.toEntity());
        user.encodePassword(passwordEncoder);

        user.addUserAuthority();
    }

    public String login(UserReqDTO userReqDTO) {

        User user = userRepository.findByEmail(userReqDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 Email 입니다."));

        String password = userReqDTO.getPassword();
        if (!user.matchPassword(passwordEncoder, password)) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        String role = user.getRole().name();

        return jwtTokenProvider.createToken(user.getUsername(), role);
    }
}

package JobBoard.service;

import JobBoard.Repository.CustomUserRepository;
import JobBoard.model.CustomUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CustomUserServiceTest {

    @Mock
    CustomUserRepository customUserRepository;

    @InjectMocks
    CustomUserService customUserService;

    private CustomUser customUser;

    @BeforeEach
    void setup(){
        customUser = new CustomUser("user","123");
    }

    @Test
    public void givenUsername_whenGetByUsername_thenReturnCustomUser(){

        given(customUserRepository.findByUsername("user")).willReturn(customUser);

        CustomUser savedCustomUser = (CustomUser) customUserService.loadUserByUsername("user");

        assertThat(savedCustomUser).isNotNull();

    }
}
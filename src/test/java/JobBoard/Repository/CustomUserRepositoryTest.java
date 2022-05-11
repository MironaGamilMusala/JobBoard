package JobBoard.Repository;

import JobBoard.model.CustomUser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomUserRepositoryTest {
    
    @Autowired
    CustomUserRepository customUserRepository;
    
    CustomUser customUser;

    @BeforeEach
    void setup(){
        customUser = new CustomUser("user","123");
    }

    @Test
    public void givenCandidateObject_whenSave_thenReturnSavedUser(){
        CustomUser savedCustomUser = customUserRepository.save(customUser);
        assertThat(savedCustomUser).isNotNull();
        assertThat(savedCustomUser.getId()).isGreaterThan(0);
    }

    @Test
    public void givenCustomUsers_whenFindAll_thenGetCustomUsersList(){
        CustomUser customUser2 = new CustomUser("user2", "123");

        customUserRepository.save(customUser);
        customUserRepository.save(customUser2);

        List<CustomUser> customUserList = customUserRepository.findAll();

        assertThat(customUserList.size()).isEqualTo(2);
    }

    @Test
    public void givenCustomUser_whenGetById_thenGetCustomUser(){
        customUserRepository.save(customUser);

        CustomUser customUserById = customUserRepository.getById(customUser.getId());

        assertThat(customUserById).isNotNull();
        assertThat(customUserById.getUsername()).isEqualTo("user");
    }

    @Test
    public void givenCustomUser_whenFindByUsername_thenGetCustomUser(){

        customUserRepository.save(customUser);

        CustomUser customUserByUsername = customUserRepository.findByUsername(customUser.getUsername());

        assertThat(customUserByUsername).isNotNull();
        assertThat(customUserByUsername.getUsername()).isEqualTo("user");

    }

    @Test
    public void givenCustomUser_whenUpdate_thenGetUpdatedInfo(){
        customUserRepository.save(customUser);

        customUser.setUsername("updatedUser");
        customUserRepository.save(customUser);
        CustomUser updatedCustomUser = customUserRepository.getById(customUser.getId());

        assertThat(updatedCustomUser).isNotNull();
        assertThat(updatedCustomUser.getUsername()).isEqualTo("updatedUser");
    }

    @Test
    public void givenCustomUser_whenDelete_thenRemoved(){
        customUserRepository.save(customUser);

        customUserRepository.deleteById(customUser.getId());
        Optional<CustomUser> savedCustomUser = customUserRepository.findById(customUser.getId());

        assertThat(savedCustomUser).isEmpty();
    }
}
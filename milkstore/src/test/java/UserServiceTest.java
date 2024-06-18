
import com.cookswp.milkstore.pojo.entities.User;
import com.cookswp.milkstore.repository.UserRepository.UserRepository;
import com.cookswp.milkstore.service.RoleService;
import com.cookswp.milkstore.service.UserService;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testng.annotations.Test;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RoleService roleService;

    @InjectMocks
    private UserService userService;


    // T1: Add Staff Account - Email must be unique
    @Test
    public void testAddStaffAccountEmailUnique_HappyCase() {
        when(userRepository.findByEmailAddress(anyString())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(new User());

        User user = new User();
        user.setEmailAddress("test@domain.com");
        user.setPassword("password123");
        user.setRole(roleService.getRoleByRoleName("SELLER"));

        User savedUser = userService.registerStaff(user);
        assertNotNull(savedUser);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddStaffAccountEmailUnique_BadCase() {
        when(userRepository.findByEmailAddress(anyString())).thenReturn(new User());

        User user = new User();
        user.setEmailAddress("test@domain.com");

        userService.registerStaff(user);
    }

    // T12: Change Password - Old Password must match the current password
    @Test
    public void testChangePasswordOldPasswordMatch_HappyCase() {
        User user = new User();
        user.setPassword("encodedOldPassword");

        when(userRepository.findByUserId(anyInt())).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        userService.updateUserPassword(1, "newPassword");

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangePasswordOldPasswordMatch_BadCase() {
        User user = new User();
        user.setPassword("encodedOldPassword");

        when(userRepository.findByUserId(anyInt())).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        userService.updateUserPassword(1, "newPassword");
    }

    // T16: Register - Email must be unique and not associated with any other account
    @Test
    public void testRegisterEmailUnique_HappyCase() {
        when(userRepository.findByEmailAddress(anyString())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(new User());

        User user = new User();
        user.setEmailAddress("newuser@domain.com");
        user.setPassword("password123");

        User savedUser = userService.registerUser(user);
        assertNotNull(savedUser);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRegisterEmailUnique_BadCase() {
        when(userRepository.findByEmailAddress(anyString())).thenReturn(new User());

        User user = new User();
        user.setEmailAddress("existing@domain.com");

        userService.registerUser(user);
    }

    // T19: Edit Profile - Email must not be changeable
    @Test
    public void testEditProfileEmailNotChangeable_HappyCase() {
        User existingUser = new User();
        existingUser.setEmailAddress("existing@domain.com");

        when(userRepository.findByUserId(anyInt())).thenReturn(existingUser);
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User updateUser = new User();
        updateUser.setUsername("newUsername");
        updateUser.setPhoneNumber("1234567890");

        userService.updateUserBasicInformation(1, updateUser);

        assertEquals(existingUser.getEmailAddress(), "existing@domain.com");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEditProfileEmailNotChangeable_BadCase() {
        User existingUser = new User();
        existingUser.setEmailAddress("existing@domain.com");

        when(userRepository.findByUserId(anyInt())).thenReturn(existingUser);

        User updateUser = new User();
        updateUser.setEmailAddress("newemail@domain.com");

        userService.updateUserBasicInformation(1, updateUser);
    }

    // T22: Login - Email must exist in the system
    @Test
    public void testLoginEmailExists_HappyCase() {
        when(userRepository.findByEmailAddress(anyString())).thenReturn(new User());

        User user = userService.getUserByEmail("test@domain.com");
        assertNotNull(user);
        verify(userRepository, times(1)).findByEmailAddress(anyString());
    }

    @Test
    public void testLoginEmailExists_BadCase() {
        when(userRepository.findByEmailAddress(anyString())).thenReturn(null);

        User user = userService.getUserByEmail("nonexistent@domain.com");
        assertNull(user);
        verify(userRepository, times(1)).findByEmailAddress(anyString());
    }

    // T9: Ban User Account - Banned users must not be able to log in
    @Test
    public void testBanUserAccount_HappyCase() {
        User user = new User();
        user.setProhibitStatus(false);

        when(userRepository.findByUserId(anyInt())).thenReturn(user);

        userService.banMemberUser(1);

        assertTrue(user.isProhibitStatus());
        verify(userRepository, times(1)).save(user);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBanUserAccount_BadCase() {
        when(userRepository.findByUserId(anyInt())).thenReturn(null);

        userService.banMemberUser(1);
    }
}

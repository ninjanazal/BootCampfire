package com.dev.server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dev.server.constants.user.Gender;
import com.dev.server.constants.user.Role;
import com.dev.server.dto.request.CreateUserDto;
import com.dev.server.entity.User;
import com.dev.server.exeptions.types.InvalidDataException;
import com.dev.server.repository.IUserRepository;
import com.dev.server.security.SecurityService;
import com.dev.server.service.user.UserService;

/**
 * This class contains unit tests for the UserService class, specifically
 * for the method registerUser. The test covers the registration of a user
 * with valid data and ensures that the user is correctly saved and encoded.
 */
public class UserServiceTest {
	@InjectMocks
	private UserService userService;

	@Mock
	private IUserRepository userRepository;

	@Mock
	private SecurityService securityService;

	/**
	 * Sets up the test environment before each test method execution.
	 * Initializes mocks annotated with @Mock.
	 */
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Tests the registration of a user with valid data.
	 * Ensures the user is saved and the password is encoded correctly.
	 * 
	 * @throws InvalidDataException if the data provided is invalid.
	 */
	@Test
	public void testRegisterUserValid() throws InvalidDataException {
		CreateUserDto userDto = new CreateUserDto() {
			{
				setEmail("test@test.com");
				setName("John Doe");
				setAge(30);
				setPassword("password");
				setGender("male");
			}
		};

		when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);
		when(userRepository.save(any(User.class))).thenReturn(new User() {
			{
				setRole(Role.DEFAULT);
				setGender(Gender.MALE);
				setEmail(userDto.getEmail());
				setName(userDto.getName());
				setAge(userDto.getAge());
				setHsh_scrt("hashed_password");
			}
		});
		when(securityService.EncodeData(userDto.getPassword())).thenReturn("hashed_password");

		User registeredUser = userService.registerUser(userDto);

		assertNotNull(registeredUser);
		assertEquals(userDto.getEmail(), registeredUser.getEmail());
		assertEquals("hashed_password", registeredUser.getHsh_scrt());

		verify(userRepository, times(1)).existsByEmail(userDto.getEmail());
		verify(userRepository, times(1)).save(any(User.class));
		verify(securityService, times(1)).EncodeData(userDto.getPassword());
	}

}

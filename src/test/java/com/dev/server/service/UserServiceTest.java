package com.dev.server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.dev.server.dto.request.CreateUserDto;
import com.dev.server.entity.User;
import com.dev.server.exeptions.types.InvalidDataException;
import com.dev.server.repository.IUserRepository;
import com.dev.server.security.SecurityService;
import com.dev.server.service.user.UserService;

public class UserServiceTest {
	@InjectMocks
	private UserService userService;

	@Mock
	private IUserRepository userRepository;

	@Mock
	private SecurityService securityService;

	@Test
	public void testRegisterUserValid() throws InvalidDataException {
		CreateUserDto userDto = new CreateUserDto() {
			{
				setName("John Doe");

			}
		};

		// Mock userRepository behavior
		when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);
		when(userRepository.save(any(User.class))).thenReturn(new User());

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

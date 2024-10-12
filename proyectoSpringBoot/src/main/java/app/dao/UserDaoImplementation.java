package app.dao;

import app.dao.interfaces.UserDao;
import app.dao.repository.UserRepository;
import app.dto.UserDto;
import app.helpers.Helper;
import app.model.Person;
import app.model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@Service

public class UserDaoImplementation implements UserDao {
    
        @Autowired
        UserRepository userRepository;

	@Override
	public UserDto findByUserName(UserDto userDto) throws Exception {
                User user = userRepository.findByUserName(userDto.getUserName());
                return Helper.parse(user);
	}

	@Override
	public boolean existsByUserName(UserDto userDto) throws Exception {
		return userRepository.existsByUserName(userDto.getUserName());
                
		}

	@Override
	public void createUser(UserDto userDto) throws Exception {
		User user = Helper.parse(userDto);
		userRepository.save(user);
                userDto.setId(user.getId());
		}
        
        @Override
        public void updateUser (UserDto userDto) throws Exception {
            User user = Helper.parse(userDto);
            userRepository.save(user);
        }
}
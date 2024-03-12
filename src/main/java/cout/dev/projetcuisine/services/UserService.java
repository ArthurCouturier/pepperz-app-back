package cout.dev.projetcuisine.services;

import cout.dev.projetcuisine.dtos.LoginDTO;
import cout.dev.projetcuisine.dtos.UserDTO;
import cout.dev.projetcuisine.models.User;
import cout.dev.projetcuisine.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Service
@RequestMapping("/api/users")
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/create")
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        System.out.println("Request to create a new user.");
        User user = User.fromDTO(userDTO);
        return userRepository.save(user).toDTO();
    }

    @PostMapping("/login")
    public UserDTO login(@RequestBody LoginDTO loginDTO) {
        System.out.println("Request to login.");
        boolean isLoginAnEmail = LoginDTO.isLoginAnEmail(loginDTO.getLogin());
        User user = isLoginAnEmail ? userRepository.findByEmail(loginDTO.getLogin())
                : userRepository.findByName(loginDTO.getLogin());

        if (user == null) {
            throw new RuntimeException("Invalid login or password.");
        }
        System.out.println("User found.");

        if (user.getHashedPassword().equals(loginDTO.getPassword())) {
            return user.toDTO();
        } else {
            throw new RuntimeException("Invalid login or password.");
        }
    }

    @GetMapping("/getAll")
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    @DeleteMapping("/deleteByName/{name}")
    public String deleteByName(@PathVariable String name) {
        User user = userRepository.findByName(name);
        userRepository.delete(user);
        return "User deleted successfully";
    }
}

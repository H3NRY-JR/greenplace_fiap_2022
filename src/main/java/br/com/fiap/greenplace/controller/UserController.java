package br.com.fiap.greenplace.controller;

import br.com.fiap.greenplace.entity.Address;
import br.com.fiap.greenplace.entity.User;
import br.com.fiap.greenplace.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user){
            return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping("/findById/{userId}")
    public ResponseEntity<User> findUserById(@PathVariable long userId) {return userRepository.findById(userId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());}

    @GetMapping("/findByUserName/{username}")
    public ResponseEntity<User> findUserById(@PathVariable String username) {return userRepository.findByUsername(username).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());}

    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<User> findUserByEmail(@PathVariable String email) {return userRepository.findByEmail(email).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());}

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable long userId, @RequestBody User user) {
        User existingUser = userRepository.findById(userId).orElse(null);

        if (existingUser == null){
            return ResponseEntity.notFound().build();
        }else{
            BeanUtils.copyProperties(user, existingUser, "id");

            existingUser = userRepository.save(user);
            return ResponseEntity.ok(existingUser);
        }
    }

    @GetMapping("/getAddressess/{userId}")
    public ResponseEntity<List<Address>> getAddresses(@PathVariable long userId) {return userRepository.findById(userId).map(user ->ResponseEntity.ok(user.getAddresses())).orElse(ResponseEntity.notFound().build());}

    @PutMapping("/addAddress/{userId}")
    public Object addAddress(@PathVariable long userId, @RequestBody Address address){return userRepository.findById(userId).map(user -> ResponseEntity.ok(user.getAddresses().add(address))).orElse(ResponseEntity.notFound().build());}


    @GetMapping
    public Page<User> findAllUsers() {return userRepository.findAll(PageRequest.of(0, 10));}
}

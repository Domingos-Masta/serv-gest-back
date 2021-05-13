package com.isysdcore.sigs.auth;

import com.isysdcore.sigs.exceptions.EntityNotFoundException;
import com.isysdcore.sigs.security.UserInformation;
import com.isysdcore.sigs.user.User;
import com.isysdcore.sigs.user.UserModelAssembler;
import com.isysdcore.sigs.user.UserPasswordUpdater;
import com.isysdcore.sigs.user.UserRepository;
import com.isysdcore.sigs.util.ApiError;
import com.isysdcore.sigs.util.Constants;
import com.isysdcore.sigs.util.JwtTokenUtil;
import com.isysdcore.sigs.util.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 *
 * @author domingos.fernando
 */
@RestController
@RequestMapping(Constants.DEFAULT_APP_URL_BASE + Constants.DEFAULT_APP_API_VERSION + Constants.APP_NAME)
public class AuthenticationController implements Serializable
{

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    private Map<String, String> mapValue = new HashMap<>();
    private Map<String, String> userDbMap = new HashMap<>();

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserModelAssembler assembler;

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public ResponseEntity<?> userLogin(@RequestBody @NotNull UserLoginDTO userLoginDTO) throws AuthenticationException, UnsupportedEncodingException
    {
        LOGGER.info("userLogin() method call...");
        if (null == userLoginDTO.getEmail() || userLoginDTO.getEmail().isEmpty()) {
            return new ResponseEntity<>("User email or phone is required", HttpStatus.BAD_REQUEST);
        }

        //set database parameter
        User currentUser = userRepository.findByCred(userLoginDTO.getEmail());
        if (null == currentUser || !currentUser.getEnabled()) {
            throw new RuntimeException("Please contact service provider.");
        }
        //Entry Client Wise value dbName store into bean.
        loadCurrentDatabaseInstance(userLoginDTO.getEmail());

        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String token = jwtTokenUtil.generateToken(userDetails.getUsername(), userDetails.getAuthorities().toString());
        //Map the value into applicationScope bean
        setMetaDataAfterLogin();
        return ResponseEntity.ok(new AuthResponse(userDetails.getUsername(), token));
    }

    @GetMapping("/user/currentProfile")
    public EntityModel<User> loadCurrentUser()
    {
        User user = loadCurrentLogedUser();
        return assembler.toModel(user);
    }

    private void loadCurrentDatabaseInstance(String userName)
    {
        mapValue.put(userName, "");
    }

    @Bean(name = "userProfileInfo")
    @ApplicationScope
    public UserInformation setMetaDataAfterLogin()
    {
        UserInformation profileInformation = new UserInformation();
        if (mapValue.size() > 0) {
            mapValue.keySet().forEach(key -> {
                if (null == userDbMap.get(key)) {
                    //Here Assign putAll due to all time one come.
                    userDbMap.putAll(mapValue);
                }
                else {
                    userDbMap.put(key, mapValue.get(key));
                }
            });
            mapValue = new HashMap<>();
        }
        profileInformation.setMap(userDbMap);
        return profileInformation;
    }

    private User loadCurrentLogedUser()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails local = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByCred(local.getUsername());
        return user;
    }

    /**
     * Methods to make umpdates on my profile
     *
     * @return
     */
    @GetMapping("/auth/currentProfile")
    public EntityModel<User> loadCurrentUserAuth()
    {
        User user = loadCurrentLogedUser();
        return assembler.toModel(user);
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> signUp(@RequestBody User user)
    {
        user.setEnabled(true);
        user.setPassword(new PasswordEncoder().getPasswordEncoder().encode(user.getPassword()));
        EntityModel<User> entityModel = assembler.toModel(userRepository.save(user));

        return ResponseEntity //
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
            .body(entityModel);

    }

    @PutMapping("/auth/updateProfile")
    public ResponseEntity<?> update(@RequestBody User newUser)
    {
        User updatedUser = userRepository.findById(loadCurrentLogedUser().getId()) //
            .map(user -> {
                user.setName(newUser.getName());
                user.setPhone(newUser.getPhone());
                user.setEmail(newUser.getEmail());
                return userRepository.save(user);
            }) //
            .orElseGet(() -> {
                newUser.setId(loadCurrentLogedUser().getId());
                return userRepository.save(newUser);
            });

        EntityModel<User> entityModel = assembler.toModel(updatedUser);

        return ResponseEntity //
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
            .body(entityModel);
    }

    @PutMapping("/auth/updateMyPassword")
    public ResponseEntity<?> updateMyPassword(@RequestBody UserPasswordUpdater newUserPasswd)
    {
        User user = userRepository.findById(loadCurrentLogedUser().getId()) //
            .orElseThrow(() -> new EntityNotFoundException(new User(), loadCurrentLogedUser().getId()));

        ApiError apiError;
        if (new PasswordEncoder().getPasswordEncoder().matches(newUserPasswd.getOldPassword(), user.getPassword())) {
            user.setPassword(new PasswordEncoder().getPasswordEncoder().encode(newUserPasswd.getNewPassword()));
            userRepository.save(user);
            apiError = new ApiError(HttpStatus.OK, "A sua palavra passe foi atualizada com sucesso de hoje em diante passará a usar a nova palavra passe", "Operação efetuada com sucesso");
        }
        else {
            apiError = new ApiError(HttpStatus.UNAUTHORIZED, "Erro ao tentar atualizar a password, confirme sua password anterior.", "Passwords diferentes.");
        }
        return ResponseEntity.ok(apiError);
    }
}

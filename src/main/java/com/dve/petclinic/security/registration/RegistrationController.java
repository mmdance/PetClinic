package com.dve.petclinic.security.registration;

import com.dve.petclinic.generalExceptions.ConflictException;
import com.dve.petclinic.generalExceptions.InvalidCredentialsException;
import com.dve.petclinic.security.registration.userRegistration.CommonUserRegistrationModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class RegistrationController {
    private final
    RegistrationService registrationService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new CommonUserRegistrationModel());

        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid @ModelAttribute("userForm") CommonUserRegistrationModel userModel,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        try {
            registrationService.register(userModel);
        } catch (ConflictException exception) {
            model.addAttribute("usernameError", exception.getMessage());
            return "registration";
        } catch (InvalidCredentialsException exception) {
            model.addAttribute("invalidCredentials", exception.getMessage());
            return "registration";
        }
        return "redirect:/login";
    }
}

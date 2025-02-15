package youduck.highton.controller.auth

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import youduck.highton.controller.auth.dto.LoginHttpDto
import youduck.highton.controller.auth.dto.RegisterHttpDto
import youduck.highton.service.auth.AuthService

@RestController
@RequestMapping("/api/v1")
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/login")
    fun login(
        @RequestBody loginRequest: LoginHttpDto.Request,
    ): ResponseEntity<LoginHttpDto.Response> {
        return ResponseEntity.ok(authService.login(loginRequest))
    }

    @PostMapping("/signup")
    fun signup(
        @RequestBody registerRequest: RegisterHttpDto.Request,
    ): ResponseEntity<RegisterHttpDto.Response> {
        return ResponseEntity.ok(authService.register(registerRequest))
    }
}

package nl.hand.made.vending.machine.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ValidationExceptionHandler {

    @ExceptionHandler(ValidationException::class)
    fun handleAllExceptions(ex: ValidationException) = ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
}

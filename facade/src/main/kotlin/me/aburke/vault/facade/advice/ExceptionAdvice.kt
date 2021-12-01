package me.aburke.vault.facade.advice

import me.aburke.vault.core.exceptions.DataError
import me.aburke.vault.core.exceptions.InvalidRequestException
import me.aburke.vault.core.exceptions.NotAuthorizedException
import me.aburke.vault.core.exceptions.NotFoundException
import me.aburke.vault.facade.advice.dto.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.Instant
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class ExceptionAdvice {

    @ExceptionHandler(InvalidRequestException::class)
    fun handleInvalidRequest(ex: InvalidRequestException, req: HttpServletRequest): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(400)
            .body(
                ErrorResponse(
                    status = 400,
                    path = req.servletPath,
                    error = ex.message ?: "Invalid Request",
                    timestamp = Instant.now(),
                )
            )
    }

    @ExceptionHandler(NotAuthorizedException::class)
    fun handleNotAuthorized(ex: NotAuthorizedException, req: HttpServletRequest): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(403)
            .body(
                ErrorResponse(
                    status = 403,
                    path = req.servletPath,
                    error = "Not Authorized",
                    timestamp = Instant.now(),
                )
            )
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(ex: NotFoundException, req: HttpServletRequest): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(404)
            .body(
                ErrorResponse(
                    status = 404,
                    path = req.servletPath,
                    error = ex.message ?: "Resource Not Found",
                    timestamp = Instant.now(),
                )
            )
    }

    @ExceptionHandler(DataError::class)
    fun handleDataError(ex: DataError, req: HttpServletRequest): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(500)
            .body(
                ErrorResponse(
                    status = 500,
                    path = req.servletPath,
                    error = ex.message ?: "Data Error Encountered",
                    timestamp = Instant.now(),
                )
            )
    }
}

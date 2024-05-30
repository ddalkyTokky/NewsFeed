package com.noreabang.strawberryrabbit.domain.member.controller

import com.noreabang.strawberryrabbit.domain.member.dto.MemberCreateRequest
import com.noreabang.strawberryrabbit.domain.member.dto.MemberResponse
import com.noreabang.strawberryrabbit.domain.member.dto.MemberUpdateRequest
import com.noreabang.strawberryrabbit.domain.member.dto.SigninRequest
import com.noreabang.strawberryrabbit.domain.member.service.MemberService
import com.noreabang.strawberryrabbit.infra.amazon.FileUploadService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/members")
class MemberController (
    private  val memberService: MemberService,
    private val fileUploadService: FileUploadService
){
    private val log = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/signup")
    fun createMember(@RequestPart("file") file: MultipartFile?,
                     @Valid @RequestPart memberCreateRequest: MemberCreateRequest): ResponseEntity<MemberResponse> {
        val image = if(file==null) null else fileUploadService.UploadFile(file)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(memberService.createMember(memberCreateRequest,image))
    }

    // Swagger-ui에 보여주기 위함, 실제 login 처리는 Spring Security로 처리
    @PostMapping("/signin")
    fun singin (@RequestBody signinRequest: SigninRequest) {
        log.info("signin ${signinRequest}")
    }

    @PostMapping("/refresh")
    fun refresh(
        @RequestHeader("Authorization") authHeader: String,
        @RequestBody refreshToken: String
    ): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.refresh(authHeader, refreshToken))
    }

    @PutMapping()
    fun updateMember(@RequestPart("file") file: MultipartFile?,
                     @RequestPart @Valid memberUpdateRequest: MemberUpdateRequest
    ): ResponseEntity<MemberResponse> {
        val image = if(file==null) null else fileUploadService.UploadFile(file)
        val memberId = memberService.getMemberDetails()?.getMemberId()
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(memberService.updateMember(memberUpdateRequest, memberId!!, image))
    }

    @DeleteMapping
    fun deleteMember(): ResponseEntity<Unit> {
        val memberId = memberService.getMemberDetails()?.getMemberId()
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(memberService.deleteMember(memberId!!))
    }
}
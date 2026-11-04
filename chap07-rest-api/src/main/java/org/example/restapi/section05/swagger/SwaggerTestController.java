package org.example.restapi.section05.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface SwaggerTestController {

    //특정 회원의 정보를 전체 수정.
    //put?
    @Operation(summary = "모든 사용자 조회", description = "모든 사용자를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = ResponseMesssage.class))),
            @ApiResponse(responseCode = "500", description = "조회 실패", content = @Content(schema = @Schema(hidden = true)))

    })
    public ResponseEntity<?> updateUser(@PathVariable int userNo, @RequestBody UserDTO user);

    @Parameter(name = "userNo", description = "조회할 사용자", required = true) //userNo값이 무조건 있어야 함.
    public ResponseEntity<ResponseMesssage> findUserByNo(int userNo);



}

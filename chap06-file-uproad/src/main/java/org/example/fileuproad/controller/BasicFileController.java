package org.example.fileuproad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class BasicFileController {

    @GetMapping("/basic-file-uproad")
    public String basicFileUproadPage(){
        return "redirect:/";
    }


    //multipart file
    /*
          String 에서 업로드 파일을 다루는 인터페이스
          파일명, 크기 , 타입 내용으르 확인할 수 있ㅋ다.
          저장을 위해서는 원하는 저장경로로 옮겨야 한다.

     */
    @PostMapping("/basic-file-uproad")
    public String uproadFile(@RequestParam(value = "file", required = false)MultipartFile file, Model model) throws IOException {

//        //경로
//
//        Path uproadDirectory1 = Paths.get("uploads").toAbsolutePath(); //src와 같은 라인의 업로드 파일을 의미. //
//        System.out.println();                                                  //absolute는 고정 경로로 삼곘다.
//        Path uproadDirectory2 = Paths.get("uploads").toAbsolutePath(); //src와 같은 라인의 업로드 파일을 의미. //
//        System.out.println("uproadDirectory2 = " + uproadDirectory2);
////
//        Path uproadDirectory3 = Paths.get("uploads/./images/cat.jpg").normalize(); //src와 같은 라인의 업로드 파일을 의미. //
        //. (점 하나): 현재 내가 있는 위치(디렉토리)를 뜻합니다.
        //.. (점 두 개): 상위 디렉토리(바로 위 부모 폴더)를 뜻합니다.
        // 코드 흐름은 upload 주소에서 멈춰서, image 파일 찾아 들어가 cat.jap파일을 가져오라.
//        System.out.println("uproadDirectory3 = " + uproadDirectory3);     //nomalize : 정리된 경로를 반환.


        Path uproadDirectory = Paths.get("uploads").normalize(); //깔끔하고 쉽게 풀어주겠다.
        System.out.println("uproadDirectory = " + uproadDirectory);

        //uploads 폴더 생성. //있다면 아무 작업 일어나지 않음.
        Files.createDirectories(uproadDirectory); //
        
        String originalFile = file.getOriginalFilename(); //파일 이름 가져오기.
        System.out.println("originalFile = " + originalFile);

        Path savedFile = uproadDirectory.resolve(originalFile); //경로 붙이기. 경로와 파일명 합침.
        System.out.println("savedFile = " + savedFile);

        /*
            transgerto : maltipartFile의 실제  내용을 지정한 경로에 저장.
         */
        file.transferTo(savedFile);

        //model에 저장.
        model.addAttribute("message", "파일 업로드성공!");
        model.addAttribute("originalFileName", originalFile);
        model.addAttribute("savedPath", savedFile);

        return "main";
    }

    /*
         여기서 문제점 :
         파일 경로가 java코드에 직접 작성되어있음.
         -> 운영체제나 실행환경마다 다를 때 코드를 수정해야하는 문제.
         -> yaml에 os별 경로를 설정해서 @value 로 주입하는 방식으로 해결.

         같은 이름의 파일.
         -> 여러 사용자가 같은 파일명을 업로드하면 덮어씌워져, 기존 파일이 삭제될 수 있음.
         -> uuid 기반의 고유한 저장 파일명을 생성

         화면에 파일을 보여줄 수가 없음. 저장한 파일을 브라우저에서 조회 불가.
         -> 파일 시스템경로는 브라우저가 조회할 수 없기에,
         -> webMVCConfigure의 addResourcehandler()로 url과 실제 저장경로를 연결해서 보여줄 것.

     */


}

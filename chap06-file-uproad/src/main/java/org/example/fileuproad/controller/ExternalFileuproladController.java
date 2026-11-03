package org.example.fileuproad.controller;

import org.example.fileuproad.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//외부로 파일 보내려는 클래스.
//value로 야밀 파일 꺼내옴.

@Controller
public class ExternalFileuproladController {

    private final FileUploadUtils fileUploadUtils;

    public ExternalFileuproladController(FileUploadUtils fileUploadUtils) {
        this.fileUploadUtils = fileUploadUtils;
    }

    @PostMapping("external-single-file")  //여러 파일을 가져오고 싶으면 DTO로 하면 됨
    public String externalSingleFileUpload(@RequestParam MultipartFile singleFile,
                                           @RequestParam String singleFileDescription,
                                           RedirectAttributes redirectAttributes) throws IOException {

        String filePath = fileUploadUtils.getUploadPath("img/single");
        String originalFileName = singleFile.getOriginalFilename();

        /*
         * UUID 생성
         * - 파일명의 UUID를 만들어 변환해 저장
         * */
        String savedName = createSavedName(originalFileName);

        // 경로로 해당 파일을 저장한다.
        singleFile.transferTo(new File(filePath, savedName));

        redirectAttributes.addFlashAttribute("message", "외부 단일 파일 업로드 성공!");
        // img : 서버로 이미지에 대한 요청을 보낼주소
        redirectAttributes.addFlashAttribute("img", "/uploads/img/single/" + savedName);

        redirectAttributes.addFlashAttribute("singleFileDescription", singleFileDescription);

        return "redirect:/result";
    }

    //DTO.
    //try Catch -> database에 저장되는 것, 실제 파일이 저장되는 것을 둘 다 성공했을 시에만 완료 처리가 되야한다.
    //쓸데 없는 파일이 생겨나 용량 잡아먹으면 안되니까.

    @PostMapping("external-multi-file")
    public String externalMultiFileUpload(@RequestParam List<MultipartFile> multiFiles,
                                          @RequestParam String multiFileDescription,
                                          RedirectAttributes redirectAttributes) {
        String uploadPath = fileUploadUtils.getUploadPath("img/multi");

        //LIST로 받아서 이렇게 쓰겠다.
        //구분하는 이유는 분리해서 관리하려고. 리스트로 나눠서. 리스트에 몽땅 다 넣고.
        //여러 파일들도 하나씩 저장해서 MultiPartFile 과정 반복.
        List<FileDTO> files = new ArrayList<>(); // 파일 자체는 dto에 저장. 원본이름은 데이터베이스에.
                                                //데이터베이스 주소 알려주고, 사용자는 주소에 들어가서 파일을 알 수 있도록??
        List<String> savedFilesPaths = new ArrayList<>();

        try {
            for (MultipartFile multiFile : multiFiles) {
                String originalFileName = multiFile.getOriginalFilename();
                String savedName = createSavedName(originalFileName); //uuid 만들기.

                //dto list에 추가.
                files.add(new FileDTO(originalFileName, savedName, uploadPath, multiFileDescription));
                multiFile.transferTo(new File(uploadPath, savedName)); //파일을 저장.
                savedFilesPaths.add("/uploads/img/multi/" + savedName); //경로 list에 담기.
                                                                        //파일 입출력 -> 오류 ->
            }

            redirectAttributes.addFlashAttribute("message", "[Success] 외부 경로에 다중 파일 업로드 성공!");
            redirectAttributes.addFlashAttribute("imgs", savedFilesPaths);
            redirectAttributes.addFlashAttribute("multiFileDescription", multiFileDescription);
        } catch (Exception exception) {
            for (FileDTO file : files) {
                new File(uploadPath, file.getSavedName()).delete();  //예외 발생시 삭제. 이유: 오류났는데 삭제 안하면 데이터베이스에는 값이 없는 파일이 생성될 수 있어서.
            }
            redirectAttributes.addFlashAttribute("message", "[Failed] 외부 경로에 다중 파일 업로드 실패!!");
        }

        return "redirect:/result";
    }



    private String createSavedName(String originalFileName) {
        String extenstion = originalFileName.substring(originalFileName.lastIndexOf("."));// 확장자

        // UUID 문자열로 변경후 "-" 지우고 확장자 붙이기
        return UUID.randomUUID().toString().replace("-", "") + extenstion;
    }




}



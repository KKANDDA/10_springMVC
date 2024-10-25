package com.ohgiraffers.chap07fileupload;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class FileUploadController {

    @PostMapping("single-file")
    public String singleFile(@RequestParam MultipartFile singleFile,
                             String singleFileDescription, Model model) {
        System.out.println("singleFile = " + singleFile);
        /*
        * StandardMultipartHttpServletRequest
        *   - spring에서 multipart 요청을 처리하기 위한 클래스
        * $StandardMultipartFile@51b464c9
        *   - 업로드된 파일을 나타내는 객체.
        *   - 실제 파일 데이터에 대한 접근을 제공한다.
        * */
        System.out.println("singleFileDescription = " + singleFileDescription);

        // 파일을 저장할 경로 설정
        String filePath = "C:/uploads/single";
        File fileDir = new File(filePath);

        if (!fileDir.exists()) {
            fileDir.mkdirs(); // 경로가 없으면 경로에 폴더를 생성하는 조건문
        }

        // 사용자가 업로드한 원본파일 이름 // 사용자가 많으면 파일 이름이 중복될 수 있으니 고유하게 보관하는 방법이 있다.
        String originFileName = singleFile.getOriginalFilename();

        // 확장자 추출
        // substring으로 뒤에서 .까지 추출
        String ext = originFileName.substring(originFileName.lastIndexOf("."));

        // UUID.randomUUID() 교유 식별자 생성(고유 이름를 지어주는 메소드), 문자열로, 하이픈 없이, 확장자 붙여서..
        String savedName = UUID.randomUUID().toString().replace("-","")+ext;


        // 업로드된 파일을 지정된 경로와 고유한 파일 이름으로 저장한다.
        try {
            singleFile.transferTo(new File(filePath + "/" + savedName));
            //
            // 여기에 DB 저장 로직 추가하면 됨.
            //
            model.addAttribute("message", "파일 업로드 성공~!~!!!");

            // "/img/single/" 이런 요청이 오면 WebConfig에서 설정한 "file:///C:/uploads/single/"로 대체된다.
            model.addAttribute("img", "/img/single/"+savedName);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "파일 업로드 실패!! ㅠㅠ");

        }
        return "result";
    }

    @PostMapping("multiple-file")
    public String multipleFile(@RequestParam
                                   List<MultipartFile> multipleFiles,
                                   String multipleFileDescriptions, Model model){

        String[] descriptions = multipleFileDescriptions.split(",");
        List<String> pathsAvailable = new ArrayList<>();

        String filePath = "C:/uploads/multiple";
        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
            try {
                for (MultipartFile multipleFile : multipleFiles) {
                    for(String description : descriptions) {
                        System.out.println("description = " + description);
                    }
                    System.out.println("multipleFile = " + multipleFile);
                    String originFileName = multipleFile.getOriginalFilename();
                    String ext = originFileName.substring(originFileName.lastIndexOf("."));
                    String savedName = UUID.randomUUID().toString().replace("-", "") + ext;

                    multipleFile.transferTo(new File(filePath + "/" + savedName));
                    pathsAvailable.add("/img/multiple/" + savedName);
                }
                model.addAttribute("message", "멀티 업로드 성공!~!~!~!");

            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("message", "멀티 업로드 실패.. ㅠㅠ");
            }
        model.addAttribute("imgs", pathsAvailable);
        return "result";
    }
}

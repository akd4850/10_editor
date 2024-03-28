package com.gdu.prj10.service;

import java.io.File;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.prj10.utils.MyFileUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
  
  private final MyFileUtils myFileUtils;

  @Override
  public ResponseEntity<Map<String, Object>> summernoteImageUpload(
      MultipartHttpServletRequest multipartHttpServletRequest) {

    String uploadPath = myFileUtils.getUploadPath();
    File dir = new File(uploadPath);
    if(!dir.exists()) {
      dir.mkdirs();
    }
    
    MultipartFile multipartFile = multipartHttpServletRequest.getFile("image");
    String fileSystemName = myFileUtils.getFilesystemName(multipartFile.getOriginalFilename());
    File file = new File(dir, fileSystemName);
    
    try {
      multipartFile.transferTo(file);
    } catch(Exception e) {
      e.printStackTrace();
    }
    
    Map<String, Object> map = Map.of(
        "src", multipartHttpServletRequest.getContextPath() + uploadPath + "/" + fileSystemName);
    
    return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
  }

}
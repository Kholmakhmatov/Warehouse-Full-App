package uz.kholmakhmatov.warehouseapp.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.kholmakhmatov.warehouseapp.entity.attachment.Attachment;
import uz.kholmakhmatov.warehouseapp.response.ResponseData;
import uz.kholmakhmatov.warehouseapp.service.AttachmentService;


import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/attachment")
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;

    @GetMapping()
    public Page<Attachment> getAllImagesInfo(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return attachmentService.getAllImagesInfo(pageable);
    }

    @GetMapping("/{id}")
    public ResponseData findOne(@PathVariable Long id, HttpServletResponse response) {
        return attachmentService.findOne(id, response);
    }

    @SneakyThrows
    @PostMapping
    public ResponseData add(MultipartHttpServletRequest httpServletRequest) {
        return attachmentService.save(httpServletRequest);
    }

    @PutMapping("/{id}")
    public ResponseData edit(@PathVariable Long id, @RequestBody MultipartFile multipartFile) {
        return attachmentService.edit(id, multipartFile);
    }

    @DeleteMapping("/{id}")
    public ResponseData delete(@PathVariable Long id) {
        return attachmentService.delete(id);
    }

}

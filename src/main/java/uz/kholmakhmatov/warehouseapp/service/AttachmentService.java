package uz.kholmakhmatov.warehouseapp.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.kholmakhmatov.warehouseapp.entity.attachment.Attachment;
import uz.kholmakhmatov.warehouseapp.entity.attachment.AttachmentContent;
import uz.kholmakhmatov.warehouseapp.repository.AttachmentContentRepository;
import uz.kholmakhmatov.warehouseapp.repository.AttachmentRepository;
import uz.kholmakhmatov.warehouseapp.response.ResponseData;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

@Service
public class AttachmentService {

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    public Page<Attachment> getAllImagesInfo(Pageable pageable) {
        return attachmentRepository.findAll(pageable);
    }

    @SneakyThrows
    public ResponseData findOne(Long id, HttpServletResponse response) {
        Optional<Attachment> attachmentOptional = attachmentRepository.findById(id);

        if (attachmentOptional.isPresent()) {
            Attachment attachment = attachmentOptional.get();

            Optional<AttachmentContent> contentOptional = attachmentContentRepository.findAttachmentContentByAttachmentId(id);

            if (contentOptional.isPresent()) {

                response.setContentType(attachment.getContentType());
                response.setHeader("Content-Disposition", "attachment; filename = \"" + attachment.getName() + "\"");

                FileCopyUtils.copy(contentOptional.get().getBytes(), response.getOutputStream());
                return new ResponseData("SuccessDownloaded", true, attachment.getName());
            }
        }
        return new ResponseData("Photo not found", false);
    }

    public ResponseData save(MultipartHttpServletRequest multipartHttpServletRequest) throws IOException {
        Iterator<String> fileNames = multipartHttpServletRequest.getFileNames();

        while (fileNames.hasNext()) {

            MultipartFile multipartFile = multipartHttpServletRequest.getFile(fileNames.next());

            if (multipartFile != null) {

                Attachment attachment = new Attachment();
                attachment.setContentType(multipartFile.getContentType());
                attachment.setName(multipartFile.getOriginalFilename());
                attachment.setSize(multipartFile.getSize());
                Attachment saved = attachmentRepository.save(attachment);

                AttachmentContent attachmentContent = new AttachmentContent();

                attachmentContent.setAttachment(saved);
                attachmentContent.setBytes(multipartFile.getBytes());

                attachmentContentRepository.save(attachmentContent);
            }
        }
        return new ResponseData("Successfully saved", true);
    }

    @SneakyThrows
    public ResponseData edit(Long id, MultipartFile multipartFile) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isEmpty()) {
            return new ResponseData("Attachment does not exist", false);
        }

        if (multipartFile == null)
            return new ResponseData("File is empty", false);

        Optional<AttachmentContent> content = attachmentContentRepository.findAttachmentContentByAttachmentId(id);

        Attachment attachment = optionalAttachment.get();
        attachment.setId(id);
        attachment.setName(multipartFile.getOriginalFilename());
        attachment.setSize(multipartFile.getSize());
        attachment.setContentType(multipartFile.getContentType());

        AttachmentContent attachmentContent = new AttachmentContent();
        attachmentContent.setAttachment(attachment);
        attachmentContent.setId(content.get().getId());
        attachmentContent.setBytes(multipartFile.getBytes());

        attachmentRepository.save(attachment);
        attachmentContentRepository.save(attachmentContent);

        return new ResponseData("Successfully edited", true);
    }

    public ResponseData delete(Long id) {
        Optional<Attachment> attachmentOptional = attachmentRepository.findById(id);

        if (attachmentOptional.isEmpty())
            return new ResponseData("Not found", false);

        Optional<AttachmentContent> content = attachmentContentRepository.findAttachmentContentByAttachmentId(id);
        content.ifPresent(attachmentContent -> attachmentContentRepository.delete(attachmentContent));
        attachmentRepository.deleteById(id);

        return new ResponseData("Successfully deleted", true);
    }

}

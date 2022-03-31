package uz.kholmakhmatov.warehouseapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.kholmakhmatov.warehouseapp.entity.attachment.Attachment;


@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}

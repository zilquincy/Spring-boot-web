package RentalWeb.Rentalin.service;

import RentalWeb.Rentalin.dto.FeedbackDTO;
import RentalWeb.Rentalin.model.Feedback;
import RentalWeb.Rentalin.model.Transaksi;
import RentalWeb.Rentalin.model.User;
import RentalWeb.Rentalin.repository.FeedbackRepository;
import RentalWeb.Rentalin.repository.TransaksiRepository;
import RentalWeb.Rentalin.repository.UserRepository;
import RentalWeb.Rentalin.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransaksiRepository transaksiRepository;

    public FeedbackDTO createFeedback(FeedbackDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        Transaksi transaksi = null;
        if (dto.getTransaksiId() != null) {
            transaksi = transaksiRepository.findById(dto.getTransaksiId())
                    .orElseThrow(() -> new RuntimeException("Transaksi tidak ditemukan"));
        }

        Feedback feedback = MapperUtil.toFeedback(dto, user, transaksi);
        feedback.setId(UUID.randomUUID().toString());
        feedback.setTanggalFeedback(LocalDateTime.now());

        Feedback saved = feedbackRepository.save(feedback);
        return MapperUtil.toFeedbackDTO(saved);
    }

    public List<FeedbackDTO> getAllFeedback() {
        return feedbackRepository.findByDeletedAtIsNull()
                .stream()
                .map(MapperUtil::toFeedbackDTO)
                .collect(Collectors.toList());
    }

    public FeedbackDTO getFeedbackById(String id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback tidak ditemukan"));
        return MapperUtil.toFeedbackDTO(feedback);
    }

    public void deleteFeedback(String id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback tidak ditemukan"));
        feedback.setDeletedAt(LocalDateTime.now());
        feedbackRepository.save(feedback);
    }
}

package RentalWeb.Rentalin.controller;

import RentalWeb.Rentalin.dto.FeedbackDTO;
import RentalWeb.Rentalin.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<FeedbackDTO> createFeedback(@Valid @RequestBody FeedbackDTO dto) {
        FeedbackDTO created = feedbackService.createFeedback(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbacks() {
        return ResponseEntity.ok(feedbackService.getAllFeedback());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDTO> getFeedbackById(@PathVariable String id) {
        return ResponseEntity.ok(feedbackService.getFeedbackById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable String id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.noContent().build();
    }
}

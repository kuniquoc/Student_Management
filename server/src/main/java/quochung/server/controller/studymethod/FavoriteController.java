package quochung.server.controller.studymethod;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import quochung.server.payload.MessageDto;
import quochung.server.service.studymethod.FavoriteService;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> addFavorite(@RequestParam("userId") Long userId,
            @RequestParam("studyMethodId") Long studyMethodId) {
        try {
            favoriteService.addFavorite(userId, studyMethodId);
            return ResponseEntity.status(201)
                    .body(new MessageDto("Thêm phương pháp học vào danh sách yêu thích thành công"));
        } catch (BadRequestException e) {
            return ResponseEntity.status(404).body(new MessageDto(e.getMessage()));
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return ResponseEntity.status(500).body(new MessageDto("Lỗi server"));
        }
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> removeFavorite(@RequestParam("userId") Long userId,
            @RequestParam("studyMethodId") Long studyMethodId) {
        try {
            favoriteService.removeFavorite(userId, studyMethodId);
            return ResponseEntity.noContent().build();
        } catch (BadRequestException e) {
            return ResponseEntity.status(404).body(new MessageDto(e.getMessage()));
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return ResponseEntity.status(500).body(new MessageDto("Lỗi server"));
        }
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getFavoritesByUserId(@RequestParam("userId") Long userId) {
        try {
            String message = "Lấy danh sách phương pháp học yêu thích thành công";
            Object data = favoriteService.getFavoritesByUserId(userId);
            return ResponseEntity.ok(new MessageDto(message, data));
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return ResponseEntity.status(500).body(new MessageDto("Lỗi server"));
        }
    }
}

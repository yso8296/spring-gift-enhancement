package gift.controller.wish;

import gift.common.annotation.LoginUser;
import gift.common.dto.PageResponse;
import gift.common.auth.LoginInfo;
import gift.controller.wish.dto.WishRequest;
import gift.controller.wish.dto.WishResponse;
import gift.service.WishService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wish")
public class WishListController {

    private final WishService wishService;

    public WishListController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping("")
    public ResponseEntity<PageResponse<WishResponse>> getAllWishList(
        @LoginUser LoginInfo user,
        @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PageResponse<WishResponse> responses = wishService.findAllWish(user.id(), pageable);
        return ResponseEntity.ok().body(responses);
    }

    @PostMapping("")
    public ResponseEntity<Void> addWishProduct(@LoginUser LoginInfo user,
        @Valid @RequestBody WishRequest.Create request) {
        Long id = wishService.addWistList(user.id(), request);
        return ResponseEntity.created(URI.create("/api/v1/wish/" + id)).build();
    }

    @PatchMapping("/{wishId}")
    public ResponseEntity<String> updateWishProduct(@PathVariable("wishId") Long wishId,
        @LoginUser LoginInfo user,
        @Valid @RequestBody WishRequest.Update request) {
        wishService.updateWishList(user.id(), wishId, request);
        return ResponseEntity.ok().body("위시리스트에 상품이 수정되었습니다.");
    }

    @DeleteMapping("/{wishId}")
    public ResponseEntity<String> deleteWishProduct(@PathVariable("wishId") Long wishId,
        @LoginUser LoginInfo user) {
        wishService.deleteWishList(user.id(), wishId);
        return ResponseEntity.ok().body("위시리스트에서 상품이 삭제되었습니다.");
    }
}

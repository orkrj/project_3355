package elice.webshopping.domain.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @param receiverName -> 정규식 패턴 유효성 검증 필요
 * @param receiverPhoneNumber -> 정규식 패턴 유효성 검증 필요
 * @param postalCode -> 정규식 패턴 유효성 검증 필요
 * @param address1 -> 정규식 패턴 유효성 검증 필요
 * @param address2 -> 정규식 패턴 유효성 검증 필요
 */

public record ReceiverRequestDto (
        @NotNull @NotEmpty String receiverName,
        @NotNull @NotEmpty String receiverPhoneNumber,
        @NotNull @NotEmpty String postalCode,
        @NotNull @NotEmpty String address1,
        @NotNull @NotEmpty String address2
) {}

package elice.webshopping.domain.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @param name -> 정규식 패턴 유효성 검증 필요
 * @param phoneNumber -> 정규식 패턴 유효성 검증 필요
 * @param zipCode -> 정규식 패턴 유효성 검증 필요
 * @param streetAddress -> 정규식 패턴 유효성 검증 필요
 * @param detailAddress -> 정규식 패턴 유효성 검증 필요
 * @param message -> 길이 제한 필요
 */

public record ReceiverRequestDto (
        @NotNull @NotEmpty String name,
        @NotNull @NotEmpty String phoneNumber,
        @NotNull @NotEmpty String zipCode,
        @NotNull @NotEmpty String streetAddress,
        @NotNull @NotEmpty String detailAddress,
        String message
) {}

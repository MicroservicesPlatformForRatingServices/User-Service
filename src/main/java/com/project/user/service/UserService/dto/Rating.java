package com.project.user.service.UserService.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rating {

    private String userId;
    private String ratingId;
    private int rating;
    private String hotelId;
	private String feedback;
    private Hotel hotel;
}

package com.project.user.service.UserService.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hotel {

    private String hotelId;
    private String name;
    private String location;
    private String about;
}

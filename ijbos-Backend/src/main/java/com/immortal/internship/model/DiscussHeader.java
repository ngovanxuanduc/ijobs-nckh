package com.immortal.internship.model;

import com.immortal.internship.payload.response.DiscussAllResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscussHeader extends DiscussAllResponse {
    private String studentEmail;
    private String studentPhone;
}

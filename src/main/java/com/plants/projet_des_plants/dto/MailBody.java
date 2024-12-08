package com.plants.projet_des_plants.dto;

import lombok.Builder;

@Builder
public record MailBody(String to, String text, String subject) {

}

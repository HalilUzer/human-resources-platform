package com.halil.HumanResourcesPlatform.Authentication.dtos.GetEmailFromLinkedin;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Element(String handle,
                      String type,
                      String primary,

                      @JsonProperty(value = "handle~")
               Handler handler) {
}

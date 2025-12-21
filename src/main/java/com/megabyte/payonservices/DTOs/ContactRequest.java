package com.megabyte.payonservices.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class ContactRequest {
    private List<String> contactNumbers;

}

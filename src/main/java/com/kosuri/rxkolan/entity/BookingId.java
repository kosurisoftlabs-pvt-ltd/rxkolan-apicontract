package com.kosuri.rxkolan.entity;

import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
public class BookingId implements Serializable {

    private String bookingNumber;

    private LocalDate bookingDate;
}

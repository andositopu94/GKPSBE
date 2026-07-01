package com.GKPS.Model.Enum;

public enum EventStatus {
    PENDING("Menunggu"),
    SCHEDULED("Terjadwal"),
    COMPLETED("Selesai"),
    CANCELLED("Dibatalkan"),
    POSTPONED("Ditunda");

    private final String displayName;

    EventStatus(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
}

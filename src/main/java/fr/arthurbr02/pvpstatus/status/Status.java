package fr.arthurbr02.pvpstatus.status;

import org.bukkit.ChatColor;

public class Status {
    private final String status;
    private final String color;
    private final String label;

    public Status(String status, String color, String label) {
        this.status = status;
        this.color = color;
        this.label = label;
    }

    public String getStatus() {
        return status;
    }

    public String getColor() {
        return color;
    }

    public String getLabel() {
        return label;
    }
}

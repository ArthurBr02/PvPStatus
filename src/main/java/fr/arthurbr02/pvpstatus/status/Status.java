package fr.arthurbr02.pvpstatus.status;

import org.bukkit.ChatColor;
import org.bukkit.Color;

public class Status {
    private final String status;
    private final Color color;
    private final String label;

    public Status(String status, Color color, String label) {
        this.status = status;
        this.color = color;
        this.label = label;
    }

    public String getStatus() {
        return status;
    }

    public Color getColor() {
        return color;
    }

    public String getLabel() {
        return label;
    }
}

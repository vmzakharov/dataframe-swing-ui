package io.github.vmzakharov.ecdataframe.ui;

import javax.swing.*;
import java.awt.*;

public class FontSizeAdjustButtons
{
    public static JButton decreaseFontSizeButton(JComponent component)
    {
        JButton decreaseFontSizeButton = new JButton("A-");
        decreaseFontSizeButton.addActionListener(e -> changeFontSize(component, 0.9f));
        return decreaseFontSizeButton;
    }

    public static JButton increaseFontSizeButton(JComponent component)
    {
        JButton increaseFontSizeButton = new JButton("A+");
        increaseFontSizeButton.addActionListener(e -> changeFontSize(component, 1.1f));
        return increaseFontSizeButton;
    }

    static private void changeFontSize(JComponent component, float factor)
    {
        Font currentFont = component.getFont();
        float size = currentFont.getSize2D();
        Font newFont = currentFont.deriveFont(size * factor);
        component.setFont(newFont);
    }
}

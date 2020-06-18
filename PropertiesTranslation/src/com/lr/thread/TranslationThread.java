package com.lr.thread;

import com.lr.frame.window;
import com.lr.tool.TranslationTool;

import javax.swing.*;

public class TranslationThread extends Thread{
    TranslationTool translationTool;
    String FromFilePathText;
    String ToFilePathText;
    String FromLanguageText;
    String ToLanguageText;
    JButton start;
    int IsReverse;
    boolean IsTranslation;
    window window;

    public TranslationThread(TranslationTool translationTool, window window
    ) {
        this.window=window;
        this.translationTool = translationTool;
        this.FromFilePathText = window.getFromFilePathText();
        this.ToFilePathText = window.getToFilePathText();
        this.FromLanguageText = window.getFromLanguageText();
        this.ToLanguageText = window.getToLanguageText();
        this.start = window.getStart();
        this.IsReverse = window.getIsReverse();
        this.IsTranslation = window.isTranslation();
    }

    @Override
    public void run() {
        start.setText("翻译中");
        start.setEnabled(false);
        start.setFocusPainted(false);

        try {
            translationTool.translation(FromFilePathText, ToFilePathText
                    , FromLanguageText, ToLanguageText, IsReverse);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "翻译出错");
        }
        start.setText("开始");
        start.setEnabled(true);
        start.setFocusPainted(true);
        window.setTranslation(false);
        JOptionPane.showMessageDialog(null, "翻译完成");

    }
}

package com.lr.frame;

import com.lr.thread.TranslationThread;
import com.lr.tool.TranslationTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class window extends JFrame implements ActionListener{
    JTextField FromFilePath;
    JLabel FromFileLabel;
    JTextField ToFilePath;
    JLabel ToFileLable;
    JButton start;
    JLabel FromLanguageLabel;
    JTextField FromLanguage;
    JLabel ToLanguageLabel;
    JTextField ToLanguage;
    String FromFilePathText;
    String ToFilePathText;
    String FromLanguageText;
    String ToLanguageText;

    boolean IsTranslation = false;
    JComboBox<Integer> reverse;
    JLabel IsReverseLabel;
    int IsReverse;

    public window() {
        windowInit();
        SetBound();
//        BindClickEvent();
        this.add(IsReverseLabel);
        this.add(ToLanguageLabel);
        this.add(ToLanguage);
        this.add(FromLanguageLabel);
        this.add(FromLanguage);
        this.add(FromFilePath);
        this.add(FromFileLabel);
        this.add(ToFileLable);
        this.add(ToFilePath);
        this.add(start);
        this.add(reverse);
        this.setTitle("properties翻译工具");
        this.setLayout(null);
        this.setSize(300, 340);
        this.setLocation(200, 200);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void windowInit() {
        FromFilePath = new JTextField();
        FromFileLabel = new JLabel("源文件路径:");
        ToFilePath = new JTextField();
        ToFileLable = new JLabel("目标路径：");
        start = new JButton("开始");
        start.addActionListener(this);
        reverse = new JComboBox<Integer>();
        reverse.addItem(0);
        reverse.addItem(1);
        IsReverseLabel=new JLabel("选择KV是否反转");
        FromLanguageLabel = new JLabel("源语言:");
        FromLanguage = new JTextField();
        ToLanguageLabel = new JLabel("目标语言:");
        ToLanguage = new JTextField();
    }

    private void SetBound() {
        FromFileLabel.setBounds(10, 10, 80, 40);
        FromFilePath.setBounds(82, 15, 200, 30);
        ToFileLable.setBounds(10, 50, 80, 40);
        ToFilePath.setBounds(82, 55, 200, 30);
        FromLanguageLabel.setBounds(10, 90, 80, 40);
        FromLanguage.setBounds(82, 95, 200, 30);
        ToLanguageLabel.setBounds(10, 130, 80, 40);
        ToLanguage.setBounds(82, 135, 200, 30);
        start.setBounds(100, 230, 80, 40);
        start.setFont(new Font("宋体", Font.BOLD, 13));
        reverse.setBounds(160, 180, 35, 20);
        IsReverseLabel.setBounds(10, 170, 100, 40);
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(start) && !IsTranslation) {
//            FromFilePathText = FromFilePath.getText().trim();
            FromFilePathText = FromFilePath.getText().trim();
            ToFilePathText = ToFilePath.getText().trim();
            FromLanguageText = FromLanguage.getText().trim();
            ToLanguageText = ToLanguage.getText().trim();
            if (!"".equals(FromFilePathText) &&
                    FromFilePathText != null &&
                    !"".equals(ToFilePathText) &&
                    ToFilePathText != null &&
                    !"".equals(FromLanguageText) &&
                    FromLanguageText != null &&
                    !"".equals(ToLanguageText) &&
                    ToLanguageText != null
                    && !IsTranslation
            ) {
                IsTranslation = true;
                IsReverse = (int)reverse.getSelectedItem();
                TranslationThread t =
                        new TranslationThread(new TranslationTool(),
                                this);
                t.setDaemon(true);
                t.start();
            }
        }
    }

    public JButton getStart() {
        return start;
    }

    public void setStart(JButton start) {
        this.start = start;
    }

    public String getFromFilePathText() {
        return FromFilePathText;
    }

    public void setFromFilePathText(String fromFilePathText) {
        FromFilePathText = fromFilePathText;
    }

    public String getToFilePathText() {
        return ToFilePathText;
    }

    public void setToFilePathText(String toFilePathText) {
        ToFilePathText = toFilePathText;
    }

    public String getFromLanguageText() {
        return FromLanguageText;
    }

    public void setFromLanguageText(String fromLanguageText) {
        FromLanguageText = fromLanguageText;
    }

    public String getToLanguageText() {
        return ToLanguageText;
    }

    public void setToLanguageText(String toLanguageText) {
        ToLanguageText = toLanguageText;
    }

    public boolean isTranslation() {
        return IsTranslation;
    }

    public void setTranslation(boolean translation) {
        IsTranslation = translation;
    }

    public int getIsReverse() {
        return IsReverse;
    }

    public void setIsReverse(int isReverse) {
        IsReverse = isReverse;
    }
    public static void main(String[] args) {
        new window();
    }
}

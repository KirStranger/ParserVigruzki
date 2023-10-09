package ru.kirstranger;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class SimpleGUI extends JFrame {
    static JFrame jFrame = getFrame();
    static JPanel jPanel = new JPanel();
    JButton jButtonSelect;
    JButton jButtonStart;
    JFormattedTextField jStartRow;
    JFormattedTextField jNumberCellDate;
    JFormattedTextField jNumberCellAmount;
    JFormattedTextField jNumberCellID;
    File exelFile;

    public void showView() {
        jButtonSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        if (f.getName().endsWith("xlsx") || f.isDirectory()) {
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public String getDescription() {
                        return "*xlsx";
                    }
                });
                fileChooser.showOpenDialog(jPanel);
                exelFile = fileChooser.getSelectedFile();
            }
        });

        jButtonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (exelFile == null) {
                    JOptionPane.showMessageDialog(null, "Не указан путь к файлу", "Ошибка", JOptionPane.PLAIN_MESSAGE);
                } else {
                    Parser parser = new Parser(Integer.parseInt(jStartRow.getText()),
                            Integer.parseInt(jNumberCellDate.getText()),
                            Integer.parseInt(jNumberCellAmount.getText()),
                            Integer.parseInt(jNumberCellID.getText()));
                    WriteToFile.CreateTxtFiles(exelFile, parser);
                    JOptionPane.showMessageDialog(null, "Было создано записей: " + WriteToFile.getCorrectCounter() +
                            "\n" + "Проблемных строк: " + WriteToFile.getIncorrectCounter(), "Готово", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
        jPanel.revalidate();
    }

    public SimpleGUI() throws HeadlessException {
        super("Из exel в txt");
        jFrame.add(jPanel);
        jPanel.setLayout(new GridLayout(5, 3, 2, 2));
        jButtonSelect = new JButton("Выбрать файл");
        jButtonStart = new JButton("Начать обработку");

        jStartRow = new JFormattedTextField(NumberFormat.getIntegerInstance());
        jNumberCellDate = new JFormattedTextField(NumberFormat.getIntegerInstance());
        jNumberCellAmount = new JFormattedTextField(NumberFormat.getIntegerInstance());
        jNumberCellID = new JFormattedTextField(NumberFormat.getIntegerInstance());

        JLabel jLabelStarRow = new JLabel("Стартовая строка");
        JLabel jLabelCellDate = new JLabel("Колонка даты");
        JLabel jLabelCellAmount = new JLabel("Колонка суммы");
        JLabel jLabelCellID = new JLabel("Колонка ЛС");

        jStartRow.setValue(17);
        jNumberCellDate.setValue(3);
        jNumberCellAmount.setValue(13);
        jNumberCellID.setValue(22);

        jPanel.add(jButtonSelect);
        jPanel.add(jButtonStart);
        jPanel.add(jStartRow);
        jPanel.add(jLabelStarRow);
        jPanel.add(jNumberCellDate);
        jPanel.add(jLabelCellDate);
        jPanel.add(jNumberCellAmount);
        jPanel.add(jLabelCellAmount);
        jPanel.add(jNumberCellID);
        jPanel.add(jLabelCellID);
    }

    static JFrame getFrame() {

        JFrame jFrame = new JFrame() {
        };
        jFrame.setBackground(Color.BLACK);
        jFrame.setVisible(true);
        jFrame.setBounds(500, 250, 300, 200);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        return jFrame;
    }
}

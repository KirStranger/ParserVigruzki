package ru.kirstranger;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.NumberFormat;
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

                    int startRow = Integer.parseInt(jStartRow.getText());
                    int numberCellDate = jNumberCellDate.getText().toLowerCase().charAt(0) - 96;
                    int numberCellAmount = jNumberCellAmount.getText().toLowerCase().charAt(0) - 96;
                    int numberCellID = jNumberCellID.getText().toLowerCase().charAt(0) - 96;
                    if (isCorrect(numberCellDate, numberCellAmount, numberCellID)) {
                        Parser parser = new Parser(startRow, numberCellDate, numberCellAmount, numberCellID);
                        WriteToFile.CreateTxtFiles(exelFile, parser);
                        JOptionPane.showMessageDialog(null, "Было создано записей: " + WriteToFile.getCorrectCounter() +
                                "\n" + "Проблемных строк: " + WriteToFile.getIncorrectCounter(), "Готово", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Неправильно указана информация о колонках, \n проверьте раскладку клавиотуры  ", "Ошибка", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        });
        jPanel.revalidate();
    }

    private boolean isCorrect(int numberCellDate, int numberCellAmount, int numberCellID) {
        boolean result = true;
        int min = 1;
        int max = 26;
        if (numberCellDate < min || numberCellDate > max || numberCellAmount < min || numberCellAmount > max || numberCellID < min || numberCellID > max) {
            result = false;
        }
        return result;
    }

    public SimpleGUI() throws HeadlessException {
        super("Из exel в txt");
        jFrame.add(jPanel);
        jPanel.setLayout(new GridLayout(5, 3, 2, 2));
        jButtonSelect = new JButton("Выбрать файл");
        jButtonStart = new JButton("Начать обработку");

        jStartRow = new JFormattedTextField(NumberFormat.getIntegerInstance());
        jNumberCellDate = new JFormattedTextField();
        jNumberCellAmount = new JFormattedTextField();
        jNumberCellID = new JFormattedTextField();

        JLabel jLabelStarRow = new JLabel("Стартовая строка");
        JLabel jLabelCellDate = new JLabel("Колонка даты");
        JLabel jLabelCellAmount = new JLabel("Колонка суммы");
        JLabel jLabelCellID = new JLabel("Колонка ЛС");

        jStartRow.setValue(17);
        jNumberCellDate.setValue("B");
        jNumberCellAmount.setValue("N");
        jNumberCellID.setValue("U");

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

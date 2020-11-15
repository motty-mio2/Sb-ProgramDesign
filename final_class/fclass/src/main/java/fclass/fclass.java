package fclass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;

class mainFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private JButton RunBtn, ClearBtn;
    private JTextField inputField;
    private JTextArea outArea;
    private JMenuBar menubar;
    private JMenu JM[] = new JMenu[1];
    private JMenuItem JMI[] = new JMenuItem[2];
    private JPanel buttonPanel;
    Container ct = getContentPane();

    private int xsize = 500;
    private int ysize = 300;

    public static String fileToString(File file) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            StringBuffer sb = new StringBuffer();
            int c;
            while ((c = br.read()) != -1) {
                sb.append((char) c);
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }

    public void runExecute(String cmd) {
        inputField.setText("");
        // System.out.println(cmd);
        outArea.append(cmd + "\n");
        ArrayList<String> ret = process.parser(cmd);
        String r1 = ret.get(1);
        if (r1.equals("True")) {
            outArea.append(" <<< " + ret.get(0) + "\n");
        }
    }

    public ActionListener runcommand = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            runExecute(inputField.getText());
        }
    };

    mainFrame() {
        super("なでしこ");
        setSize(xsize, ysize);
        setLayout(new BorderLayout());
        setResizable(false);
        inputField = new JTextField();
        inputField.addActionListener(runcommand);
        add(inputField, BorderLayout.CENTER);

        buttonPanel = new JPanel();
        RunBtn = new JButton("実行");
        RunBtn.addActionListener(runcommand);
        buttonPanel.add(RunBtn);

        ClearBtn = new JButton("削除");
        ClearBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // JOptionPane.showMessageDialog(ClearBtn, "Right : ClearBtn");
                outArea.setText("");
            }
        });
        buttonPanel.add(ClearBtn);

        add(buttonPanel, BorderLayout.EAST);

        outArea = new JTextArea();
        outArea.setEditable(false);
        // add(outArea, BorderLayout.PAGE_END);
        JScrollPane sp = new JScrollPane(outArea);
        sp.setPreferredSize(new Dimension(xsize, ysize - 100));
        add(sp, BorderLayout.SOUTH);

        menubar = new JMenuBar();
        JM[0] = new JMenu("ファイル");
        JMI[0] = new JMenuItem("読み込み");
        JMI[1] = new JMenuItem("書き出し");
        JMI[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // メニュー選択時の処理
                JFileChooser JFC = new JFileChooser();
                // ファイルチューザークラスのインスタンスを生成
                int res = JFC.showOpenDialog(ct);
                // ファイルチューザーダイアログの表示
                if (res == JFileChooser.APPROVE_OPTION) {
                    // 開くボタン押下
                    File fl = JFC.getSelectedFile();
                    String name = fl.getName();
                    String extension = name.substring(name.lastIndexOf(".") + 1);
                    // System.out.println(extension);
                    if (extension.equals("txt")) {
                        outArea.setText(" ~File : " + name + " ~\n");
                        try (BufferedReader reader = new BufferedReader(new FileReader(fl))) {
                            String line = null;
                            while ((line = reader.readLine()) != null) {
                                if (line.length() >= 5) {
                                    if (line.startsWith(" <<< ") || line.startsWith(" ~File : ")) {
                                    } else {
                                        runExecute(line);
                                    }
                                } else {
                                    runExecute(line);
                                }
                            }
                        } catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        } catch (IOException e2) {
                            // failed to read file
                            e2.printStackTrace();
                        }
                    } else {
                        outArea.setText("無効なファイルです。");
                    }
                }
            }
        });

        JMI[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // メニュー選択時の処理
                JFileChooser JFC = new JFileChooser();
                // ファイルチューザークラスのインスタンスを生成
                int res = JFC.showSaveDialog(ct);
                // ファイルチューザーダイアログの表示
                if (res == JFileChooser.APPROVE_OPTION) {
                    // 開くボタン押下
                    File fl = JFC.getSelectedFile();
                    // String name = fl.getName();
                    // outArea.setText("選択したファイル名は " + name + " です");
                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(fl));
                        // System.out.println(outArea.getText());
                        bw.write(outArea.getText());
                        bw.close();
                        JOptionPane.showMessageDialog(getContentPane(), "書き出しが完了しました。");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        JM[0].add(JMI[0]);
        JM[0].add(JMI[1]);
        menubar.add(JM[0]);

        setJMenuBar(menubar);

        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}

public class fclass {
    public static void main(String[] args) {
        mainFrame mf = new mainFrame();
        mf.setLocationRelativeTo(null);
    }
}
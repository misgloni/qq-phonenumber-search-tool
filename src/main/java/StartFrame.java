import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StartFrame {
    private JPanel panel1;
    private JFrame jFrame;
    private JTextField searchTextField;
    private JButton qqToPhoneButton;
    private JButton phoneToQQButton;
    private JLabel ResultLabel;
    private JButton resultCopyButton;
    private JPanel extraPanel;

    public static final String TITLE = "QQ和手机号互搜 工具";

    public StartFrame() {


        //init DAO
        DAO.initialize();

//        //style setting
//        try {
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        qqToPhoneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setText(DAO.qqToPhone(searchTextField.getText()));
                } catch (Exception exception) {
                    ResultLabel.setText("search exception:" + exception.getMessage());
                }
            }
        });
        phoneToQQButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setText(DAO.phoneToQQ(searchTextField.getText()));
                } catch (Exception exception) {
                    ResultLabel.setText("search exception" + exception.getMessage());
                }
            }
        });

        resultCopyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //copy to clipboard base on Windows
                StringSelection selection = new StringSelection(ResultLabel.getText());
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
            }
        });
        create();
    }


    public void setText(List<String> str) {
        StringBuffer buffer = new StringBuffer();

        if (str.size() == 0) {
            ResultLabel.setText("null");
            return;
        }

        for (int i = 0; i < str.size(); i++) {
            buffer.append(str.get(i));
            if (i != str.size() - 1) {
                buffer.append("; ");
            }
        }
        ResultLabel.setText(buffer.toString());
    }

    public void create() {
        jFrame = new JFrame();
        jFrame.setLocationRelativeTo(null);
        jFrame.setContentPane(this.panel1);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}

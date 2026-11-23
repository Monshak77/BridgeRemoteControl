
import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private RemoteControl remote;

    private Device tv = new TV();
    private Device radio = new Radio();

    private JComboBox<String> deviceSelector;

    private JLabel titleLabel;
    private JLabel statusLabel;
    private JLabel powerLabel;

    private JTextArea logArea;

    private JSpinner channelSpinner;

    private Color backgroundColor = new Color(24, 29, 43);
    private Color buttonColor = new Color(48, 59, 82);
    private Color blueColor = new Color(55, 105, 210);

    public Main() {

        remote = new RemoteControl(tv);

        setTitle("Әмбебап қашықтан басқару пульті");
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(backgroundColor);
        setLayout(new BorderLayout(15, 15));

        // Жоғарғы тақырып
        JLabel header = new JLabel(
                "  Әмбебап қашықтан басқару пульті",
                SwingConstants.CENTER
        );

        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setForeground(Color.WHITE);
        header.setOpaque(true);
        header.setBackground(new Color(34, 44, 68));
        header.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        add(header, BorderLayout.NORTH);

        // Негізгі панель
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(backgroundColor);
        mainPanel.setLayout(new GridLayout(1, 2, 20, 10));

        // Сол жақ панель
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(backgroundColor);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JLabel selectLabel = new JLabel("Құрылғыны таңда:");
        selectLabel.setForeground(Color.WHITE);
        selectLabel.setFont(new Font("Arial", Font.BOLD, 18));

        deviceSelector = new JComboBox<>(
                new String[]{"Теледидар", "Радио"}
        );

        deviceSelector.setFont(new Font("Arial", Font.PLAIN, 17));
        deviceSelector.setMaximumSize(new Dimension(300, 40));

        leftPanel.add(selectLabel);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(deviceSelector);

        leftPanel.add(Box.createVerticalStrut(35));

        JLabel remoteTitle = new JLabel("📱 Әмбебап пульт");
        remoteTitle.setForeground(Color.WHITE);
        remoteTitle.setFont(new Font("Arial", Font.BOLD, 22));

        leftPanel.add(remoteTitle);
        leftPanel.add(Box.createVerticalStrut(20));

        statusLabel = new JLabel();
        statusLabel.setForeground(Color.GREEN);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));

        leftPanel.add(statusLabel);

        powerLabel = new JLabel("Құрылғы күйі: Өшірулі");
        powerLabel.setForeground(Color.WHITE);
        powerLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(powerLabel);

        // Оң жақ басқару панелі
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(backgroundColor);
        controlPanel.setLayout(new GridLayout(4, 2, 12, 15));

        JButton onButton = createButton("ҚОСУ", new Color(40, 160, 90));
        JButton offButton = createButton("ӨШІРУ", new Color(190, 65, 65));

        JButton volumeUpButton = createButton("Дыбыс +", buttonColor);
        JButton volumeDownButton = createButton("Дыбыс -", buttonColor);

        JButton previousButton = createButton("Арна -", buttonColor);
        JButton nextButton = createButton("Арна +", buttonColor);

        JButton setChannelButton = createButton("Арнаны орнату", blueColor);

        channelSpinner = new JSpinner(
                new SpinnerNumberModel(1, 1, 999, 1)
        );

        channelSpinner.setFont(new Font("Arial", Font.PLAIN, 18));

        controlPanel.add(onButton);
        controlPanel.add(offButton);

        controlPanel.add(volumeDownButton);
        controlPanel.add(volumeUpButton);

        controlPanel.add(previousButton);
        controlPanel.add(nextButton);

        controlPanel.add(channelSpinner);
        controlPanel.add(setChannelButton);

        // Батырмалардың әрекеттері

        onButton.addActionListener(e -> {
            remote.powerOn();
            log("Құрылғы қосылды");
            updateStatus();
        });

        offButton.addActionListener(e -> {
            remote.powerOff();
            log("Құрылғы өшірілді");
            updateStatus();
        });

        volumeUpButton.addActionListener(e -> {
            remote.volumeUp();
            log("Дыбыс көтерілді");
            updateStatus();
        });

        volumeDownButton.addActionListener(e -> {
            remote.volumeDown();
            log("Дыбыс төмендетілді");
            updateStatus();
        });

        nextButton.addActionListener(e -> {
            remote.changeChannel(getCurrentChannel() + 1);
            channelSpinner.setValue(getCurrentChannel());
            log("Келесі арна");
            updateStatus();
        });

        previousButton.addActionListener(e -> {
            if (getCurrentChannel() > 1) {
                remote.changeChannel(getCurrentChannel() - 1);
                channelSpinner.setValue(getCurrentChannel());
            }
            log("Алдыңғы арна");
            updateStatus();
        });

        setChannelButton.addActionListener(e -> {
            int channel = (int) channelSpinner.getValue();
            remote.changeChannel(channel);
            log("Арна орнатылды: " + channel);
            updateStatus();
        });

        // Құрылғы таңдау
        deviceSelector.addActionListener(e -> {

            if (deviceSelector.getSelectedIndex() == 0) {
                remote.setDevice(tv);
                log("Теледидар таңдалды");
            } else {
                remote.setDevice(radio);
                log("Радио таңдалды");
            }

            updateStatus();
        });

        mainPanel.add(leftPanel);
        mainPanel.add(controlPanel);

        add(mainPanel, BorderLayout.CENTER);

        // Төменгі журнал
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.setBackground(backgroundColor);
        bottomPanel.setBorder(
                BorderFactory.createEmptyBorder(0, 15, 15, 15)
        );

        JLabel logTitle = new JLabel("Әрекеттер журналы:");
        logTitle.setForeground(Color.WHITE);
        logTitle.setFont(new Font("Arial", Font.BOLD, 17));

        logArea = new JTextArea(7, 40);
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        logArea.setBackground(new Color(15, 20, 32));
        logArea.setForeground(new Color(120, 230, 150));

        JScrollPane scrollPane = new JScrollPane(logArea);

        bottomPanel.add(logTitle, BorderLayout.NORTH);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        updateStatus();
        log("Бағдарлама іске қосылды");

        setVisible(true);
    }

    private JButton createButton(String text, Color color) {

        JButton button = new JButton(text);

        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorder(
                BorderFactory.createEmptyBorder(15, 10, 15, 10)
        );

        return button;
    }

    private int getCurrentChannel() {
        return (int) channelSpinner.getValue();
    }

    private void updateStatus() {

        String status = remote.getStatus();

        statusLabel.setText(
                "<html>" + status.replace(" | ", "<br>") + "</html>"
        );

        if (status.contains("Қосулы")) {
            powerLabel.setText("Құрылғы күйі: Қосулы");
            powerLabel.setForeground(Color.GREEN);
        } else {
            powerLabel.setText("Құрылғы күйі: Өшірулі");
            powerLabel.setForeground(Color.RED);
        }
    }

    private void log(String message) {

        logArea.append(
                remote.getDeviceName() + ": " + message + "\n"
        );
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new Main();
        });
    }
}

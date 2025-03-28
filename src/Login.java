import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {
    Font font = new Font("WagleWagle",Font.PLAIN, 110);
    RoundedPanel panel = new RoundedPanel(25, new Color(61, 90, 128));
    JLabel loginLabel = new JLabel("Login");
    JLabel userLabel = new JLabel("ID : ");
    JTextField userText = new JTextField();
    JLabel passLabel = new JLabel("Password : ");
    JPasswordField passText = new JPasswordField();
    JButton loginButton = new RoundedButton("로그인",10);
    JButton signUpButton = new RoundedButton("회원가입",10);
    public Login() {

        // 패널
        panel.setLayout(null);  // Layout manager 비활성화하여 위치 지정
        panel.setBounds(300, 300, 400, 250);  // 패널의 위치와 크기 지정 (프레임 크기에 맞게 설정)

        loginLabel.setFont(font);
        loginLabel.setBounds(410,120,420,79);
        loginLabel.setForeground(Color.decode("#3D5A80"));

        // 사용자 라벨과 입력 필드
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(50, 55, 200, 30);
        userText.setBounds(140, 55, 200, 30);

        // 비밀번호 라벨과 입력 필드
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(50, 125, 200, 30);
        passText.setBounds(140, 125, 200, 30);

        // 회원가입 버튼
        signUpButton.setBounds(70, 190, 100, 30);
        signUpButton.setBackground(Color.decode("#98C1D9"));
        signUpButton.setForeground(Color.BLACK);

        // 로그인 버튼
        loginButton.setBounds(235, 190, 100, 30);
        loginButton.setBackground(Color.decode("#98C1D9"));
        loginButton.setForeground(Color.BLACK);
        loginButton.setBorderPainted(false); // 버튼 테두리 제거
        loginButton.setFocusPainted(false); // 포커스 테두리 제거

        // 버튼에 ActionListener 추가
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String member_id = userText.getText();
                String member_pw = new String(passText.getPassword());

                // DB에서 사용자 인증
                Connection conn = DBConnection.getConnection();
                if (conn != null) {
                    try {
                        String query = "SELECT * FROM membertable WHERE member_id = ? AND member_pw = ?";
                        PreparedStatement pstmt = conn.prepareStatement(query);
                        pstmt.setString(1, member_id);
                        pstmt.setString(2, member_pw);
                        ResultSet rs = pstmt.executeQuery();

                        if (rs.next()) {
                            JOptionPane.showMessageDialog(null, "로그인 성공!");
                            // 로그인이 성공하면 메인 메뉴로 이동하는 로직 추가 가능
                            new Menu(member_id);
                            setVisible(false); // 현재 창 닫기
                        } else {
                            JOptionPane.showMessageDialog(null, "로그인 실패! 아이디 또는 비밀번호를 확인하세요.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Start 창을 닫고 Login 창을 엽니다.
                setVisible(false); // 현재 창 닫기
                new SignUp(); // Login 창 열기
            }
        });

        // 패널에 컴포넌트 추가
        panel.add(userLabel);
        panel.add(userText);
        panel.add(passLabel);
        panel.add(passText);
        panel.add(loginButton);
        panel.add(signUpButton);

        add(loginLabel);

        // JFrame 설정
        getContentPane().setBackground(Color.decode("#E0FBFC"));
        setLayout(null);
        add(panel);
        setSize(1000, 700);  // 프레임 크기 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Login(); // 프로그램 시작
    }
}

class RoundedPanel extends JPanel {
    private Color backgroundColor;
    private int cornerRadius = 25; // 둥글기 정도 설정

    public RoundedPanel(int radius, Color bgColor) {
        super();
        cornerRadius = radius;
        backgroundColor = bgColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 배경 색상 그리기
        graphics.setColor(backgroundColor);
        graphics.fillRoundRect(0, 0, getWidth(), getHeight(), arcs.width, arcs.height); // 둥근 사각형 그리기
    }
}
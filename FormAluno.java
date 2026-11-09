import javax.swing.*;
import java.awt.*;

public class FormAluno extends JFrame {

    private final JTextField nome = new JTextField();
    private final JTextField email = new JTextField();
    private final JComboBox<String> curso = new JComboBox<>(new String[]{"Informática"});
    private final JRadioButton manha = new JRadioButton("Manhã");
    private final JRadioButton tarde = new JRadioButton("Tarde");
    private final JRadioButton noite = new JRadioButton("Noite");
    private final JButton cadastrar = new JButton("Cadastrar");
    private final JButton limpar = new JButton("Limpar");

    private boolean emailValido(String emailStr) {
        emailStr = emailStr.trim();
        return emailStr.contains("@") && emailStr.endsWith(".com");
    }

    public FormAluno() {
        super("Formulário de cadastro de alunos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("Cadastro de aluno"));
        add(new JLabel(""));

        add(new JLabel("Nome:"));
        add(nome);

        add(new JLabel("E-mail:"));
        add(email);

        add(new JLabel("Curso:"));
        add(curso);

        ButtonGroup turnos = new ButtonGroup();
        turnos.add(manha);
        turnos.add(tarde);
        turnos.add(noite);

        JPanel painelTurnos = new JPanel();
        painelTurnos.add(manha);
        painelTurnos.add(tarde);
        painelTurnos.add(noite);

        add(new JLabel("Turno:"));
        add(painelTurnos);

        add(cadastrar);
        add(limpar);

        cadastrar.addActionListener(e -> {
            String n = nome.getText().trim();
            String em = email.getText().trim();

            // 1) Verifica se algum campo está vazio
            if (n.isEmpty() || em.isEmpty() || turnos.getSelection() == null) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
                return;
            }

            // 2) Verifica se o e-mail é válido (separado do bloco acima)
            if (!emailValido(em)) {
                JOptionPane.showMessageDialog(this, "E-mail incorreto! Deve conter '@' e terminar com '.com'.");
                return;
            }

            String turno = manha.isSelected() ? "Manhã"
                    : (tarde.isSelected() ? "Tarde" : "Noite");

            JOptionPane.showMessageDialog(this, "Cadastro realizado!\nNome: " + n
                    + "\nE-mail: " + em + "\nCurso: " + curso.getSelectedItem()
                    + "\nTurno: " + turno);
        });

        limpar.addActionListener(e -> {
            nome.setText("");
            email.setText("");
            curso.setSelectedIndex(0);
            turnos.clearSelection();
        });

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormAluno().setVisible(true));
    }
}
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

public class FormAluno extends JFrame {

    private final JTextField nome = new JTextField();
    private final JTextField email = new JTextField();
    private JFormattedTextField cpf;
    private JFormattedTextField telefone;
    private final JComboBox<String> curso = new JComboBox<>(new String[]{"Informática", "Administração", "Enfermagem", "Engenharia" });
    private final JRadioButton manha = new JRadioButton("Manhã");
    private final JRadioButton tarde = new JRadioButton("Tarde");
    private final JRadioButton noite = new JRadioButton("Noite");
    private final JButton cadastrar = new JButton("Cadastrar");
    private final JButton limpar = new JButton("Limpar");

    // Método para validar e-mail
    private boolean emailValido(String emailStr) {
        emailStr = emailStr.trim();
        return emailStr.contains("@") && emailStr.endsWith(".com");
    }

    // Método para validar o CPF com o cálculo oficial dos dígitos verificadores
    private boolean cpfValido(String cpfStr) {
        String digitos = cpfStr.replaceAll("\\D", ""); // remove pontos, traço e espaços

        if (digitos.length() != 11) return false;

        // Rejeita CPFs com todos os dígitos iguais (ex: 111.111.111-11)
        if (digitos.matches("(\\d)\\1{10}")) return false;

        // Cálculo do 1º dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (digitos.charAt(i) - '0') * (10 - i);
        }
        int dv1 = 11 - (soma % 11);
        if (dv1 >= 10) dv1 = 0;

        // Cálculo do 2º dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (digitos.charAt(i) - '0') * (11 - i);
        }
        int dv2 = 11 - (soma % 11);
        if (dv2 >= 10) dv2 = 0;

        return dv1 == (digitos.charAt(9) - '0') && dv2 == (digitos.charAt(10) - '0');
    }

    public FormAluno() {
        super("Formulário de cadastro de alunos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // 8 linhas e 2 colunas para alinhar perfeitamente (Rótulo na esquerda, Campo na direita)
        setLayout(new GridLayout(8, 2, 10, 10));

        // Criação das máscaras para CPF e Telefone
        try {
            MaskFormatter mascaraCpf = new MaskFormatter("###.###.###-##");
            mascaraCpf.setPlaceholderCharacter('_');
            cpf = new JFormattedTextField(mascaraCpf);

            MaskFormatter mascaraTel = new MaskFormatter("(##) #########");
            mascaraTel.setPlaceholderCharacter('_');
            telefone = new JFormattedTextField(mascaraTel);
        } catch (ParseException e) {
            cpf = new JFormattedTextField();
            telefone = new JFormattedTextField();
        }

        // Linha 1: Título e espaço vazio
        add(new JLabel("Cadastro de aluno"));
        add(new JLabel(""));

        // Linha 2: Nome
        add(new JLabel("Nome:"));
        add(nome);

        // Linha 3: E-mail
        add(new JLabel("E-mail:"));
        add(email);

        // Linha 4: CPF
        add(new JLabel("CPF:"));
        add(cpf);

        // Linha 5: Telefone
        add(new JLabel("Telefone:"));
        add(telefone);

        // Linha 6: Curso
        add(new JLabel("Curso:"));
        add(curso);

        // Linha 7: Turno (Rótulo + Painel com os Rádios)
        ButtonGroup turnos = new ButtonGroup();
        turnos.add(manha);
        turnos.add(tarde);
        turnos.add(noite);

        JPanel painelTurnos = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        painelTurnos.add(manha);
        painelTurnos.add(tarde);
        painelTurnos.add(noite);

        add(new JLabel("Turno:"));
        add(painelTurnos);

        // Linha 8: Botões Cadastrar e Limpar
        add(cadastrar);
        add(limpar);

        cadastrar.addActionListener(e -> {
            String n = nome.getText().trim();
            String em = email.getText().trim();
            String c = cpf.getText().trim();
            String tel = telefone.getText().trim();

            if (n.isEmpty() || em.isEmpty() || c.contains("_") || tel.contains("_") || turnos.getSelection() == null) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos corretamente!");
                return;
            }

            if (!emailValido(em)) {
                JOptionPane.showMessageDialog(this, "E-mail incorreto! Deve conter '@' e terminar com '.com'.");
                return;
            }

            if (!cpfValido(c)) {
                JOptionPane.showMessageDialog(this, "CPF incorreto ou inválido!");
                return;
            }

            String turno = manha.isSelected() ? "Manhã"
                    : (tarde.isSelected() ? "Tarde" : "Noite");

            JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!\nNome: " + n
                    + "\nE-mail: " + em
                    + "\nCPF: " + c
                    + "\nTelefone: " + tel
                    + "\nCurso: " + curso.getSelectedItem()
                    + "\nTurno: " + turno);
        });

        limpar.addActionListener(e -> {
            nome.setText("");
            email.setText("");
            cpf.setValue(null);
            telefone.setValue(null);
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
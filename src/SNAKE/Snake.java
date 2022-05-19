package SNAKE;

// Importações necessárias para rodar o jogo
import javax.swing.JFrame;

public class Snake extends JFrame
{

    // Método construtor da classe
    public Snake ()
    {
        // Adiciona o JPanel do jogo
        add(new Grade());
        // Define a saida da aplicação
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
        // Define o tamanho da janela
        setSize(420, 440);
        // A localização
        setLocationRelativeTo(null);
        // O titulo da janela
        setTitle("Come-Come Game");

        // Impede o redimensionamento da janela
        setResizable(false);
        // Mostra a janela
        setVisible(true);
    }

    public static void main(String[] args) 
    {
        // Cria o novo JFrame
        new Snake();
    }

}

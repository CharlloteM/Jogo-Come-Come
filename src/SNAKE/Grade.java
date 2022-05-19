package SNAKE;

// Importações necessárias para rodar o jogo
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Grade extends JPanel implements ActionListener
{

    // Tamanho do JPanel em Largura x Altura
    private final int WIDTH_ = 400;
    private final int HEIGHT_ = 400;

    // Tamanho de cada ponto na tela
    private final int TAMANHO_PONTO = 20;
    // Tamanho total de pontos, multiplicando a largura e altura
    private final int TODOS_PONTOS = 1600;

    // Um valor aleatória para gerar posição
    private final int RAND_POSICAO = 20;
    // Um delay para o tempo de execução do jogo
    private final int DELAY = 140;

    // Definição do plano cartesiano (x,y) do jogo
    private int[] x = new int[TODOS_PONTOS];
    private int[] y = new int[TODOS_PONTOS];

    // Pontos da cobrinha
    private int pontos;
    // Posição (x,y) da comida
    private int comida_x;
    private int comida_y;

    // Contar pontuação
    private int PONTUAÇÃO = 0;
    // Mensagem da pontuação
    String SCORE = "PONTUAÇÃO: " + PONTUAÇÃO;
    // Fonte para escrever a pontuação, estilo da fonte
    Font SCORE_FONT = new Font("Consolas", Font.BOLD, 12);
    // Tamanho total da escrita na tela
    FontMetrics SCORE_METRICA = this.getFontMetrics(SCORE_FONT);

    // Definição dos movimentos
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    // Denifição do status do jogo
    private boolean estaJogando = false;
    private boolean inicio = true;
    private boolean pauseGame = false;
    private boolean startGato = false;

    // Tempo de execução do jogo
    private Timer tempo;

    // Imagens da cabeça e corpo da cobrinha, e comida
    private Image bola;
    private Image comida;
    private Image cabeça;
    private Image fundo;
    private Image fundoFinal;
    private Image fundoInicio;
    
    // skin do gato
    private Image cabecaGato;
    private Image corpoGato;
    private Image peixeGato;
    
    // skin da cobra
    private Image cabecaCobra;
    private Image corpoCobra;
    private Image macaCobra;
  
        
    // Método construtor da classe
    public Grade ()
    {
        // Cria uma instrução de teclado
        addKeyListener(new TAdapter());
       
        // Seta o plano de fundo como preto
        //setBackground(Color.black);
        
        // Cria um plano de fundo no inicio do jogo png com a imagem correspondente
        ImageIcon fundoInicio_ = new ImageIcon("fundoInicio.png");
        fundoInicio = fundoInicio_.getImage();
        
        // Cria um plano de fundo no jogo png com a imagem correspondente
        ImageIcon fundo_ = new ImageIcon("fundo.png");
        fundo = fundo_.getImage();      
        
        // Cria um plano de fundo no final do jogo png com a imagem correspondente
        ImageIcon fundoFinal_ = new ImageIcon("fundoFinal.png");
        fundoFinal = fundoFinal_.getImage();
               
        // Cria um icone do arquivo png e seta na imagem correspondente //cobrinha
        ImageIcon corpoCobra_ = new ImageIcon("corpoCobra.png");
        corpoCobra = corpoCobra_.getImage();

        // Cria um icone do arquivo png e seta na imagem correspondente //cobrinha
        ImageIcon macaCobra_ = new ImageIcon("macaCobra.png");
        macaCobra = macaCobra_.getImage();

        // Cria um icone do arquivo png e seta na imagem correspondente //cobrinha
        ImageIcon cabecaCobra_ = new ImageIcon("cabecaCobra.png");
        cabecaCobra = cabecaCobra_.getImage();
        
        // Cria um icone do arquivo png e seta na imagem correspondente //gato
        ImageIcon corpoGato_ = new ImageIcon("corpoGato.png");
        corpoGato = corpoGato_.getImage();

        // Cria um icone do arquivo png e seta na imagem correspondente //gato
        ImageIcon peixeGato_ = new ImageIcon("peixeGato.png");
        peixeGato = peixeGato_.getImage();

        // Cria um icone do arquivo png e seta na imagem correspondente //gato
        ImageIcon cabecaGato_ = new ImageIcon("cabecaGato.png");
        cabecaGato = cabecaGato_.getImage();

        // Define o foco para o JPanel
        setFocusable(true);
        // Define o tamanho da tela
        setSize(WIDTH_, HEIGHT_);

        //trocando valores dos nomes bola, cabeça e comida
        bola = corpoCobra;
        cabeça = cabecaCobra;
        comida = macaCobra;
        
        
        // Inicializa do jogo
        initJogo();
    }

    // Método para inicializar o jogo
    public void initJogo()
    {
        // Define quantidade de pontos iniciais
        pontos = 3;

        // Define a posição em (x,y) de cada ponto
        for (int i = 0; i < pontos; i++)
        {
            x[i] = 100 - i*21;
            y[i] = 100;
        }

        // Gera a primeira comida
        localComida();

        // Inicia o tempo de execução do jogo
        tempo = new Timer(DELAY, this);
        tempo.start();
    }
    
    // método para reiniciar o jogo
    public void reiniciarJogo() 
    {
        // Define quantidade de pontos iniciais
    	pontos = 3;
    	
        // Definição do plano cartesiano (x,y) do jogo
    	x = new int[TODOS_PONTOS];
       	y = new int[TODOS_PONTOS];
    	
        // Define a posição em (x,y) de cada ponto
        for (int i = 0; i < pontos; i++)
        {
            x[i] = 100 - i*21;
            y[i] = 100;
        }
        
        // Gera a primeira comida
        localComida();
    }

    // Método para desenhar elementos na tela do jogo
    @Override
    public void paint (Graphics g)
    {
        // Define o atribuito para a classe própria
        super.paint(g);
                      
        // Analisa se o jogo esta em andamento, se estiver desenha na tela,
        // se não estiver, o jogo é dado como o fim
        if (estaJogando)
        {           
            // Desenha o plano do jogo
            g.drawImage(fundo, 0, 0, this); 

            // Desenha a comida no plano (x,y) do jogo
            g.drawImage(comida, comida_x, comida_y, this);

            // Para cada ponto da cobrinha, desenha a cabeça e o corpo
            // em (x,y)
            for (int i = 0; i < pontos; i++)
            {
                if (i == 0)
                { g.drawImage(cabeça, x[i], y[i], this); }
                else
                { g.drawImage(bola, x[i], y[i], this); }
            }

            // Desenha a pontuação na tela
            desenharPontuacao(g);
            
            // Executa a sincronia de dados
            Toolkit.getDefaultToolkit().sync();

            // Pausa os gráficos
            g.dispose();
        }
        else if(inicio) {
            g.drawImage(fundoInicio, 0, 0, this); 
        } else 
        {
            // Executa o fim de jogo
            FimDeJogo(g);
        }
    }

    // Método para desenhar a pontuação na tela
    public void desenharPontuacao (Graphics g)
    {
        // Define a frase para escrever
        SCORE = "PONTUAÇÃO: " + PONTUAÇÃO;
        // Define o tamanho da fonte
        SCORE_METRICA = this.getFontMetrics(SCORE_FONT);

        // Define a cor da fonte
        g.setColor(Color.white);
        // Seta a fonte para o gráfico
        g.setFont(SCORE_FONT);
        // Desenha a fonte na tela
        g.drawString(SCORE, (WIDTH_ - SCORE_METRICA.stringWidth(SCORE)) - 10, HEIGHT_ - 10);
    }

    public void FimDeJogo (Graphics g)
    {
        // Desenha o plano do final do jogo
        g.drawImage(fundoFinal, 0, 0, this);
    	// Define a frase para escrever
        String msg = "Sua pontuação: " + PONTUAÇÃO;
        // Define o estilo da fonte
        Font pequena = new Font("Consolas", Font.BOLD, 14);
        // Define o tamanho da fonte
        FontMetrics metrica = this.getFontMetrics(pequena);

        // Define a cor da fonte
        g.setColor(Color.white);
        // Seta a fonte para o gráfico
        g.setFont(pequena);
        // Desenha a fonte na tela
        g.drawString(msg, (WIDTH_ - metrica.stringWidth(msg)) / 2 , 305);
    }

    // Método para checar se a cobrinha comeu a comida
    public void checarComida ()
    {
        // Se ele comer na mesma posição (x,y) então aumenta o corpo da cobrinha
        // aumenta a pontuação e gera uma nova comida
        if ((x[0] == comida_x) && (y[0] == comida_y))
        {
            pontos++;
            //PONTUAÇÃO++;
            if(cabeça.equals(cabecaCobra))
            {
            	PONTUAÇÃO += 20;
            	pontos += 3;
            	
            } else if(cabeça.equals(cabecaGato))
            {
            	PONTUAÇÃO += 30;
            	pontos += 6;
            }
           
            localComida();
        }
    }

    // Método para mover a cobrinha na tela
    public void mover ()
    {
        // Para cada ponto da cobrinha desenha em (x,y)
        for (int i = pontos; i > 0; i--)
        {
            x[i] = x[(i - 1)];
            y[i] = y[(i - 1)];
        }

        // Se for para esquerda decrementa em x
        if (left)
        {
            x[0] -= TAMANHO_PONTO;
        }

        // Se for para direita incrementa em x
        if (right)
        {
            x[0] += TAMANHO_PONTO;
        }

        // Se for para cima decrementa em y
        if (up)
        {
            y[0] -= TAMANHO_PONTO;
        }

        // Se for para baixo incrementa em y
        if (down)
        {
            y[0] += TAMANHO_PONTO;
        }
        
    }

    // Método para checar colisão entre a cobrinha e as bordas do jogo
    public void checarColisão ()
    {
        // Para cada ponto, verifica se este está em posição com outro ponto
        // se estiver ele avista que o jogador parou de jogar devido a colisão
        for (int i = pontos; i > 0; i--)
        {
            if ((i > 4) && (x[0] == x[i]) && (y[0] == y[i]))
            { estaJogando = false; }
            
        }

        // Verifica se a cabeça da cobrinha encostou em algum ponto (x,y)
        // nas bordas (width,height) da tela
        if (y[0] > HEIGHT_)
        { estaJogando = false; }

        if (y[0] < 0)
        { estaJogando = false; }

        if (x[0] > WIDTH_)
        { estaJogando = false; }

        if (x[0] < 0)
        { estaJogando = false; }
    }

    // Método que gera uma comida na tela
    public void localComida ()
    {
        // Define um valor aleatório e atribui a uma posição x na tela para a
        // comida
        int random = (int) (Math.random() * RAND_POSICAO);
        comida_x = (random * TAMANHO_PONTO);

        // Define um valor aleatório e atribui a uma posição y na tela para a
        // comida
        random = (int) (Math.random() * RAND_POSICAO);
        comida_y = (random * TAMANHO_PONTO);
    }

    // Método de ações durante a execução do jogo
    public void actionPerformed (ActionEvent e)
    {
        // Se estiver jogando então já realiza a checagem da comida, depois
        // verifica se existe colisão, só então depois, realiza o movimento
        // da cobrinha no jogo, por fim, redesenha os resultados
        if (estaJogando)
        {
            checarComida();
            checarColisão();
            mover();
        }

        repaint();
    }

    // Classe para analisar o teclado
    private class TAdapter extends KeyAdapter
    {

        // Método para verificar o que foi teclado
        @Override
        public void keyPressed (KeyEvent e)
        {
            // Obtém o código da tecla
            int key =  e.getKeyCode();

            // Verifica os movimentos e manipula as variáveis, para movimentar
            // corretamente sobre a tela
            if ((key == KeyEvent.VK_LEFT) && (!right))
            {
                left = true;
                up = false;
                down = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!left))
            {
                right = true;
                up = false;
                down = false;
            }

            if ((key == KeyEvent.VK_UP) && (!down))
            {
                up = true;
                left = false;
                right = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!up))
            {
                down = true;
                left = false;
                right = false;
            }
            //tela de inicio do jogo
            if ((key == KeyEvent.VK_SPACE))
            {
            	if (inicio) {
                inicio = false;
                estaJogando = true;
            	} else if(!inicio && !estaJogando) {
                    inicio = true;
                    estaJogando = false;
                    
                    reiniciarJogo();
            	}
            }
            
            // pausando jogo com a tecla ESC
            if ((key == KeyEvent.VK_ESCAPE) && (estaJogando))
            {
            	if(pauseGame == false) 
            	{
                    tempo.stop();
                    pauseGame = true;
            	}
            	else if(pauseGame == true) 
            	{
                    tempo.restart();
                    pauseGame = false;
            	}
            }
            
            //botão para mudar skin tecla G
            if ((key == KeyEvent.VK_G) && (estaJogando))
            {
                cabeça = cabecaGato;
                bola = corpoGato;
                comida = peixeGato;
            }
            //botão para mudar skin tecla C
            if ((key == KeyEvent.VK_C) && (estaJogando))
            {
                cabeça = cabecaCobra;
                bola = corpoCobra;
                comida = macaCobra;
            }
        }
    }
 
}


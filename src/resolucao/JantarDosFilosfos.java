package resolucao;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class JantarDosFilosfos {
	
	// Declaração da quantidade de filosofos e criação de vetor "Filosofo" e "Semaforo":
	private static final int NUM_FILOSOFOS = 5;
	private static Filosofo[] filosofos = new Filosofo[NUM_FILOSOFOS];
	private static Semaphore[] garfos = new Semaphore[NUM_FILOSOFOS];

	// Onde todo o "Bruto" do código ocorre:
	public static void main(String[] args) {
		for (int i = 0; i < NUM_FILOSOFOS; i++) {
			garfos[i] = new Semaphore(1); // Aqui é onde se dá inicio ao Semafóro.
		}
		for (int i = 0; i < NUM_FILOSOFOS; i++) {
			filosofos[i] = new Filosofo(i, garfos[i], garfos[(i + 1) % NUM_FILOSOFOS]); // Limitação da quantidade de filosofos fazendo com que
			new Thread(filosofos[i]).start();                                           // o código funcione corretamente.
		}
	}
}

class Filosofo implements Runnable {
	
	// Declaração de algumas variáveis:
	private final int[] cont = new int[5];
	private final int id;
	private final Semaphore garfoEsquerdo; // Obs: Semaforo que delimita
	private final Semaphore garfoDireito;  // os garfos.
	private final Random random = new Random();

	// Criação dos filosofos, importando informações de id e garfos.
	public Filosofo(int id, Semaphore garfoEsquerdo, Semaphore garfoDireito) {
		this.id = id;
		this.garfoEsquerdo = garfoEsquerdo;
		this.garfoDireito = garfoDireito;
	}

	// Aqui é onde a mágica acontece e todo o código se starta:
	@Override
	public void run() {
		try {
			while (true) {
				
				// Funções do código.
				
				pensar();
				pegarGarfos();
				comer();
				soltarGarfos();

			}
		} catch (InterruptedException e) { // Interrupção em caso de excessão:
			e.printStackTrace();
		}
	}

	// Private de função "pensar":
	private void pensar() throws InterruptedException {
		System.out.println("==========================================");
		System.out.println("Filósofo " + id + " está pensando...");
		System.out.println("==========================================");
		Thread.sleep(random.nextInt(1000)); // Esse cara aqui é importante, ele trará a aleatóriedade do código em questão!
	}
	
	// Private da função de "pegar garfos":
	private void pegarGarfos() throws InterruptedException {
		System.out.println("==========================================");
		System.out.println("Filósofo " + id + " está tentando pegar os garfos...");
		System.out.println("==========================================");
		garfoEsquerdo.acquire(); // Chamar método de garfo esquerdo.
		garfoDireito.acquire(); // Chamar método de garfo direito.
	}

	// Private da função "Comer":
	private void comer() throws InterruptedException {
		System.out.println("==========================================");
		System.out.println("Filósofo " + id + " está comendo...");
		cont[id]++; // "id" é o número do filosofo, esse cont[id]++ acrescenta a quantidade de vezes comida de cada filosófo.
		System.out.println("Filosofo " + id + " comeu " + cont[id] + " vezes.");
		System.out.println("==========================================");
		Thread.sleep(random.nextInt(1000)); // Lembre-se, esse carinha aqui trás a aleatóriedade do código.
	}
	
	// Private da função de "Soltar os garfos":
	private void soltarGarfos() {
		System.out.println("==========================================");
		System.out.println("Filósofo " + id + " soltou os garfos.");
		System.out.println("==========================================");
		garfoEsquerdo.release(); // Release de chamar método de gardo esquerdo.
		garfoDireito.release(); // Release de chamar método de gardo direito.
	}
}
package juego;

import java.awt.Color;
import java.awt.Image;

import javax.sound.sampled.Clip;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;

	// Variables y métodos propios de cada grupo
	// ...
	private Disparo disparos;
	private Islas[] isla;
	private Gnomos[] gnomosEnJuego;
	private JugadorPep pep;
	private TortugaAsesina[] tortugasEnJuego;
	private int tiempoParaSiguienteGnomo; // Contador de tiempo para el desfase
	private int gnomosRescatados;
	private int gnomosPerdidos;
	private int tortugasEliminadas;
	private int tiempo;
	private boolean enMenu;
	private boolean comienzoDelJuego;
	private boolean juegoEnPausa;
	private boolean ganoJuego;
	private boolean perdioJuego;
	private Image fondo;
	private Image gano;
	private Image perdio;
	private Image menu;
	private Image casa;
	private Clip sonidoMenu;
	private Clip sonidoFondo;
	private Clip sonidoPerdio;
	private Clip sonidoGano;

	Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Al rescate de los Gnomos- Grupo 6- p1", 800, 600);
		this.fondo = Herramientas.cargarImagen("Imagenes/fondo.png");
		this.gano = Herramientas.cargarImagen("Imagenes/winner.gif");
		this.perdio = Herramientas.cargarImagen("Imagenes/game-over.gif");
		this.menu = Herramientas.cargarImagen("Imagenes/menu.gif");
		this.casa = Herramientas.cargarImagen("Imagenes/casa.png");

		this.enMenu = true;
		this.comienzoDelJuego = false;
		this.juegoEnPausa = true;
		this.ganoJuego = false;
		this.perdioJuego = false;

		this.sonidoMenu = Herramientas.cargarSonido("Sonidos/menu.wav");
		this.sonidoFondo = Herramientas.cargarSonido("Sonidos/juego.wav");
		this.sonidoPerdio = Herramientas.cargarSonido("Sonidos/perdio.wav");
		this.sonidoGano = Herramientas.cargarSonido("Sonidos/gano.wav");

		// Inicializar lo que haga falta para el juego
		// ...
		pep = new JugadorPep(180, 455, 20, 60);
		gnomosEnJuego = new Gnomos[8];
		tortugasEnJuego = new TortugaAsesina[10];

		gnomosRescatados = 0;
		gnomosPerdidos = 0;
		tiempoParaSiguienteGnomo = 0;
		tortugasEliminadas = 0;
		tiempo = 0;

		isla = new Islas[15]; // número total de islas
		int[] rectanguloN = { 1, 2, 3, 4, 5 }; // Cantidad de islas por fila
		int[] xInicio = { 400, 300, 160, 90, 1 }; // Posiciones iniciales en x para cada fila
		int[] yFila = { 100, 200, 300, 400, 500 }; // Posiciones en y para cada fila
		int anchoRectangulo = 100;
		int distancia = 200;
		int k = 0; // posición en el arreglo de islas

		for (int i = 0; i < rectanguloN.length; i++) { // Recorremos las filas
			int x = xInicio[i];
			int y = yFila[i];
			int numRec = rectanguloN[i];

			for (int j = 0; j < numRec; j++) { // Recorremos las islas por fila
				this.isla[k] = new Islas(x, y, anchoRectangulo, 30);
				x += distancia; // Mover la siguiente isla a la derecha
				k++; // Incrementar el índice para la siguiente isla
			}
		}

		// Inicia el juego!
		this.entorno.iniciar();

	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y por lo
	 * tanto es el método más importante de esta clase. Aquí se debe actualizar el
	 * estado interno del juego para simular el paso del tiempo (ver el enunciado
	 * del TP para mayor detalle).
	 */
	public void tick() {
		// Procesamiento de un instante de tiempo
		if (enMenu && !comienzoDelJuego && juegoEnPausa) {// imagen menu del juego
			entorno.dibujarImagen(menu, 400, 300, 0);
			entorno.cambiarFont("arial", 18, Color.BLACK);
			entorno.escribirTexto("Bienvenidos al juego", 8, 20);
			entorno.escribirTexto("reglas del juego", 250, 20);
			entorno.escribirTexto("comandos", 475, 20);
			entorno.escribirTexto("lo dejo por si quieren salir ", 680, 20);// para que el tiempo sea en segundos divido
																			// por 1000

			sonidoMenu.start();
		}
		tiempo = entorno.tiempo() / 1000;
		
		if(tiempo>=60 && !ganoJuego) {
			perdioJuego=true;
		}
		if (perdioJuego) {
			entorno.dibujarImagen(perdio, entorno.ancho() / 2, entorno.alto() / 2, 0);
			sonidoFondo.stop();
			sonidoPerdio.start();

			return;

		}
		if (ganoJuego) {
			entorno.dibujarImagen(gano, entorno.ancho() / 2, entorno.alto() / 2, 0);
			sonidoFondo.stop();
			sonidoGano.start();

			return;
		}
		if (entorno.sePresiono(entorno.TECLA_ENTER)) {// comienza el juego
			enMenu = false;
			comienzoDelJuego = true;
			juegoEnPausa = false;
			sonidoMenu.stop();

		}
		if (comienzoDelJuego = true && !enMenu && !ganoJuego) {
			entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2, 0);
			sonidoFondo.start();
		}
		if (!juegoEnPausa) {
			entorno.dibujarImagen(casa, 390, 40, 0);
			entorno.cambiarFont("arial", 18, Color.BLACK);
			entorno.escribirTexto("Enemigos eliminados :" + tortugasEliminadas, 8, 20);
			entorno.escribirTexto("Gnomos perdidos :" + gnomosPerdidos, 475, 40);
			entorno.escribirTexto("Gnomos salvados :" + gnomosRescatados, 475, 20);
			entorno.escribirTexto("Tiempo: " + tiempo, 700, 20);// para que el tiempo sea en segundos divido por 1000

			
			
			// Dibujar las islas
			for (Islas i : isla) {
				if (i != null) {
					i.dibujarIsla(entorno);
				}
			}
			// Dibujo y movimiento de Pep
			if (pep != null) {
				if (!pep.getSaltando() && pep.puedoSaltar(isla)) { // moverse si no está saltando
					if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
						pep.caminar(entorno, entorno.TECLA_IZQUIERDA);
					} else if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
						pep.caminar(entorno, entorno.TECLA_DERECHA);
					}
				}
				// Salto de pep
				if (entorno.sePresiono(entorno.TECLA_ARRIBA) && pep.puedoSaltar(isla)) {
					pep.iniciarSalto(); // Iniciar el salto
					Herramientas.play("Sonidos/salto.wav");
				}
				pep.saltar();

				// Verifico colisiones con las islas para frenar o crear la caída
				boolean estaSobreUnaIsla = false;
				for (Islas i : isla) {
					if (i != null && pep.colisionIsla(i)) {
						estaSobreUnaIsla = true;
						break;// si esta sigo el flujo del juego
					}
				}
				if (!estaSobreUnaIsla) {
					pep.caer(); // si no colisiona con las islas puede caer

				}
				pep.DibujarPep(entorno);

				if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO) && disparos == null && estaSobreUnaIsla == true) {
					Herramientas.play("Sonidos/disparo.wav");
					// Disparar si no hay disparo activo

					if (pep.tieneDireccion()) {
						char direccionPep = pep.getDireccionPep();
						disparos = new Disparo(pep.getX(), pep.getY() + 20, direccionPep);
						disparos.setDireccion(direccionPep);// Obtener dirección de Pep
					} else {
						char direccionPorDefecto = entorno.TECLA_DERECHA; // sino no tiene una direccion.Por defecto
																			// sale por la derecha.
						disparos = new Disparo(pep.getX(), pep.getY() + 20, direccionPorDefecto);
						disparos.setDireccion(direccionPorDefecto);
					}
				}

				if (pep.getY() > 300) {
					// Variable para contar los gnomos rescatados

					// Recorremos el arreglo de gnomos
					for (int i = 0; i < gnomosEnJuego.length; i++) {

						if (gnomosEnJuego[i] != null) { // Verificamos que el gnomo aún exista (no sea null)
							// Verificamos la colisión con Pep
							if (pep.colisionaConGnomos(gnomosEnJuego[i])) {
								gnomosRescatados++;
								if (gnomosRescatados == 10) {
									ganoJuego = true;
								}
								// Si colisiona, el gnomo se rescata y se elimina (lo hacemos null)
								gnomosEnJuego[i] = null;
								Herramientas.play("Sonidos/gnomosalvado.wav");
								// Incrementamos el contador de gnomos rescatados

							} else {
								// Si no hay colisión, seguimos dibujando y moviendo al gnomo
								gnomosEnJuego[i].dibujarGnomos(entorno);
								gnomosEnJuego[i].movimientoGnomos(isla);
							}
						}
					}

				}
				// Verificar colisión con todas las tortugas
				for (int i = 0; i < tortugasEnJuego.length; i++) {
					if (pep != null) {
						if (pep.getY() > 580 || tortugasEnJuego[i] != null
								&& pep.colisionaConTortuga(tortugasEnJuego[i]) && tiempo < 60) {
							pep = null;
							perdioJuego = true;
							// muere el pep manejar que termine el juego
						}
					}
				}
			}
			// Actualizo el disparo
			if (disparos != null) {
				disparos.dibujarDisparo(entorno); // Dibujar el disparo
				disparos.disparar(disparos.getDireccion(), entorno); // Mover el disparo en la ultima direccion de pep.

				// Eliminar el disparo si sale de la pantalla o colisiona con tortuga
				if (disparos.fueraDePantalla()) {
					disparos = null; // El disparo desaparece
				} else if (disparos.colisionaConTortuga(tortugasEnJuego)) {
					disparos = null;
					tortugasEliminadas++;
					Herramientas.play("Sonidos/tortugamuerte.wav");

				}
			}

			// Dibujar y mover las tortugas
			int tortugasEnPantalla = 0;

			// Contamos cuántas tortugas no son null (es decir, cuántas están activas en
			// pantalla)
			for (int i = 0; i < this.tortugasEnJuego.length; i++) {
				if (tortugasEnJuego[i] != null) {
					tortugasEnPantalla++;
				}
			}

			// Si hay menos de 7 tortugas, creamos nuevas en las posiciones que están null
			if (tortugasEnPantalla < 7) {
				for (int i = 0; i < this.tortugasEnJuego.length; i++) {
					if (tortugasEnJuego[i] == null) {
						tortugasEnJuego[i] = new TortugaAsesina(32, 35, 2, tortugasEnJuego, i); // Creamos nueva tortuga
						tortugasEnPantalla++; // Incrementamos el conteo de tortugas en pantalla
						if (tortugasEnPantalla == 7) { // Cuando alcanzamos 7 tortugas, dejamos de crear más
							break;
						}
					}
				}
			}

			// Dibujar y mover solo las tortugas que están en pantalla (no null)
			for (int i = 0; i < this.tortugasEnJuego.length; i++) {
				if (tortugasEnJuego[i] != null) {
					tortugasEnJuego[i].DibujarTotuga(entorno); // Dibujamos la tortuga
					tortugasEnJuego[i].movimiento(isla); // Movemos la tortuga
				}
			}

			// dibujar y mover los Gnomos
			tiempoParaSiguienteGnomo++;
			if (tiempoParaSiguienteGnomo % 80 == 0) {
				// gnomos activos en el juego
				int gnomosActivos = 0;
				for (int i = 0; i < gnomosEnJuego.length; i++) {
					if (gnomosEnJuego[i] != null) {
						gnomosActivos++;
					}
				}

				// para que siempre tenga 6 gnomos
				if (gnomosActivos < 6) {
					for (int i = 0; i < gnomosEnJuego.length; i++) {
						if (gnomosEnJuego[i] == null) {
							// Crear un nuevo gnomo con un desfase en su posición x
							double desfaseX = i * 3; // Usa el índice para desfase de posición
							gnomosEnJuego[i] = new Gnomos(400 + desfaseX, 70, 20, 25);
							break; // crea un gnomo por ciclo
						}
					}
				}
			}

			for (int i = 0; i < this.gnomosEnJuego.length; i++) {
				if (gnomosEnJuego[i] != null) {
					gnomosEnJuego[i].dibujarGnomos(entorno);
					gnomosEnJuego[i].movimientoGnomos(isla);

					// Verificar colisiones
					for (Islas is : isla) {
						if (gnomosEnJuego[i] != null && gnomosEnJuego[i].colisionaConIsla(is)) {
							// Si colisiona con una isla, actualiza su movimiento
							gnomosEnJuego[i].movimientoGnomos(isla);
						} else if (gnomosEnJuego[i] != null) {
							// Si no colisiona con ninguna isla, cae
							gnomosEnJuego[i].caer(is);
						}
					}

					// Verificar si colisiona con tortugas o sale de pantalla
					if (gnomosEnJuego[i].colisionConTortuga(tortugasEnJuego) || gnomosEnJuego[i].salioDePantalla()) {
						gnomosPerdidos++;
						if (gnomosPerdidos == 25) {
							perdioJuego = true;
						}
						// Elimina el gnomo y crear uno nuevo
						gnomosEnJuego[i] = null;
						Herramientas.play("Sonidos/gnomomuerte.wav");
						// busco null para crear un nuevo gnomo
						for (int j = 0; j < gnomosEnJuego.length; j++) {
							if (gnomosEnJuego[j] == null) {
								double desfaseX = j * 3; // Usa el índice para desfase de posición
								gnomosEnJuego[j] = new Gnomos(400 + desfaseX, 70, 20, 25);
								break; // crea un gnomo por ciclo
							}
						}
					}
				}
			}
		}

	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}

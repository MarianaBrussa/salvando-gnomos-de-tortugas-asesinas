package juego;

import java.awt.Color;
import java.awt.Image;
import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;

public class Gnomos {
	private Image imagen;
	private double x;
	private double y;
	private double alto;
	private double ancho;
	private double velocidadX; // Velocidad horizontal
	private double velocidadY; // Velocidad vertical (caída)
	private boolean cayendo; // Estado de caída
	private boolean haSalidoDePrimeraIsla; // Control de salida desde la primera isla
	private Random random;

	// Constructor
	public Gnomos(double x, double y, double ancho, double alto) {
		this.x = x; // Posición inicial en x
		this.y = y; // Posición inicial en y
		this.alto = alto;
		this.ancho = ancho;
		this.velocidadX = 0; // Inicialmente, el gnomo no se mueve horizontalmente
		this.velocidadY = 0.5; // Velocidad de caída inicial
		this.cayendo = true; // El gnomo comienza cayendo
		this.haSalidoDePrimeraIsla = false; // Inicialmente no ha salido de la primera isla
		this.random = new Random();
		this.imagen = Herramientas.cargarImagen("Imagenes/gnomo.png").getScaledInstance(30, 35, Image.SCALE_SMOOTH);
	}

	// Método para dibujar los gnomos en la pantalla
	public void dibujarGnomos(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, new Color(0, 0, 0, 0));
		entorno.dibujarImagen(imagen, this.x, this.y, 0);
	}

	// Movimiento de los gnomos controlando colisiones con las islas
	public void movimientoGnomos(Islas[] islas) {
		if (cayendo) {
			// Si el gnomo está cayendo, se mueve hacia abajo
			this.y += velocidadY;

			// Verificar colisión con cada isla
			for (Islas isla : islas) {
				if (colisionaConIsla(isla)) {
					cayendo = false;
					velocidadY = 0; // Detiene la caída
					velocidadX = (random.nextBoolean()) ? 0.1 : -0.1; // Movimiento horizontal aleatorio

					break; // Detener al colisionar con una isla
				}
			}
		}
		if (!cayendo) {
			// Movimiento horizontal si no está cayendo
			this.x += velocidadX;

			if (!haSalidoDePrimeraIsla) {
				// Verificar si el gnomo ha salido de la primera isla
				Islas primeraIsla = islas[0];
				if (this.x <= primeraIsla.getLimiteIzq() - 10 || this.x >= primeraIsla.getLimiteDer() + 10) {
					haSalidoDePrimeraIsla = true; // El gnomo ha salido de la primera isla
				}
			}

			// después de salir de la primera isla
			if (haSalidoDePrimeraIsla) {
				for (Islas isla : islas) {
					if (colisionaConIsla(isla)) {
						// Si llega al borde de cualquier isla, comienza a caer
						if (this.x < isla.getLimiteIzq() - 10 || // le doy un margen para que el gnomo caiga bien al
																	// borde de la isla
								this.x > isla.getLimiteDer() + 10) {
							// si el gnomo ha salido de los límites de la isla
							cayendo = true; // Activar el estado de caída
							velocidadY = 0.5;
							break; // No seguir verificando otras islas
						}
					}
				}
			}
		}

	}

	public boolean colisionaConIsla(Islas isla) {
		// Verifica si el gnomo está justo en el borde superior de la isla.
		return (this.y + this.ancho / 2 == isla.getLimiteSup()) && (this.x + this.alto / 2 >= isla.getLimiteIzq())
				&& (this.x - this.alto / 2 <= isla.getLimiteDer());
	}

	public void caer(Islas isla) {
		if (cayendo && colisionaConIsla(isla)) {
			this.y += velocidadY; // Movimiento hacia abajo
			System.out.println("colisionnnnn");
		}
	}

	public boolean colisionConTortuga(TortugaAsesina[] tortugas) {
		for (TortugaAsesina tortuga : tortugas) {
			if (tortuga != null) { // Verifico que no haya tortugas nulas en el array
				// Verificar si gnomo y la tortuga colisionan
				if (this.x < tortuga.getLimiteDer() && this.x + this.ancho > tortuga.getLimiteIzq()
						&& this.y < tortuga.getLimiteInf() && this.y + this.alto > tortuga.getLimiteSup()) {
					return true; // Hay colisión
				}
			}
		}
		return false; // No hay colisión
	}

	public boolean salioDePantalla() {
		double gnomoY = getY();

		if (gnomoY > 600) {
			return true; // El gnomo ha salio de la pantalla
		}
		return false; // El gnomo está dentro de la pantalla
	}

	// Getters y setters
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Image getImagen() {
		return imagen;
	}

	public void setImagen(Image imagen) {
		this.imagen = imagen;
	}

	public double getAlto() {
		return alto;
	}

	public void setAlto(double alto) {
		this.alto = alto;
	}

	public double getAncho() {
		return ancho;
	}

	public void setAncho(double ancho) {
		this.ancho = ancho;
	}

	public void setCayendo(boolean cayendo) {
		this.cayendo = cayendo;
	}
}

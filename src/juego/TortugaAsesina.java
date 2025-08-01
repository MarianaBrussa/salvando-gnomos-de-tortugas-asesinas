package juego;

import java.awt.Color;
import java.awt.Image;
import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;

public class TortugaAsesina {
	private Image imagen;
	private double x;
	private double y;
	private double alto;
	private double ancho;
	private double velocidad;
	private double limiteIzq;
	private double limiteDer;
	private double limiteSup;
	private double limiteInf;

	public TortugaAsesina(double ancho, double alto, double velocidad, TortugaAsesina[] tortugasExistentes,
			int tortugasCreadas) {
		this.alto = alto;
		this.ancho = ancho;
		Random tortugasRandom = new Random();
		boolean posicionValida = false;
		double distanciaMinima = 80; // Distancia mínima entre tortugas
		this.velocidad = 0.3;
		// Generar coordenadas aleatorias hasta que no estén cerca de otra tortuga y no
		// caigan en la isla
		while (!posicionValida) {
			this.x = tortugasRandom.nextInt(800) + 1; // Genera coordenadas aleatorias en X
			this.y = tortugasRandom.nextInt(100) + 1; // Genera coordenadas aleatorias en Y
			posicionValida = true;

			// para que no caigan en la primer isla
			if (this.x >= 300 && this.x <= 500) {
				posicionValida = false;
				continue;
			}

			// Verificar que no esté muy cerca de las tortugas existentes
			for (int i = 0; i < tortugasCreadas; i++) {
				TortugaAsesina otraTortuga = tortugasExistentes[i];
				double distancia = Math
						.sqrt(Math.pow(this.x - otraTortuga.getX(), 2) + Math.pow(this.y - otraTortuga.getY(), 2));
				if (distancia < distanciaMinima) {
					posicionValida = false; // Si la distancia es menor que la mínima, buscar nueva posición
					break;
				}
			}
			this.imagen = Herramientas.cargarImagen("Imagenes/tortuga.png").getScaledInstance(40, 40,
					Image.SCALE_SMOOTH);

		}

		this.velocidad = tortugasRandom.nextDouble() * 0.3 + 0.3;

	}

	public void DibujarTotuga(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0.0, new Color(0, 0, 0, 0));
		entorno.dibujarImagen(imagen, x, y, 0.0);
	}

	public void movimiento(Islas[] isla) {
		// Si la tortuga no colisiona con ninguna isla, sigue moviéndose hacia abajo
		if (!colisionTortugaIsla(isla)) {
			this.y += this.velocidad; // Mover hacia abajo
			actualizarLimites();
			// Verificar si la tortuga llega al borde inferior del entorno
			if (this.y > 580) { // el límite inferior del entorno es 580
				this.y = 580; // Mantenerla dentro del límite
			}
		} else {
			// Si la tortuga colisiona con una isla, moverse sobre ella de izquierda a
			// derecha
			moverSobreIsla(isla);
		}
	}

	public boolean colisionTortugaIsla(Islas[] isla) {
		// Verificar si los bordes de la tortuga y la isla se superponen
		for (Islas is : isla) {
			if ((is.getLimiteDer() > this.limiteIzq) && (is.getLimiteIzq() < this.limiteDer)
					&& (is.getLimiteInf() > this.limiteSup) && (is.getLimiteSup() < this.limiteInf)) {
				return true; // Hay colisión
			}
		}
		return false; // No hay colisión
	}

	public void actualizarLimites() {
		this.limiteIzq = this.x - (this.ancho / 2);
		this.limiteDer = this.x + (this.ancho / 2);
		this.limiteSup = this.y - (this.alto / 2);
		this.limiteInf = this.y + (this.alto / 2);
	}

	public void moverSobreIsla(Islas[] isla) {
		for (Islas is : isla) {
			// Verificar si la tortuga está sobre la isla
			if (this.limiteDer > is.getLimiteIzq() && this.limiteIzq < is.getLimiteDer()
					&& (Math.abs(this.limiteInf - is.getLimiteSup()) < 5)) { // La tortuga está sobre la isla
				// Mover la tortuga de izquierda a derecha
				this.x += this.velocidad;

				// Actualizar los límites de la tortuga
				actualizarLimites();

				// Si la tortuga llega al borde derecho de la isla, invertir la dirección
				if (this.limiteDer > is.getLimiteDer()) {
					this.x = is.getLimiteDer() - (this.ancho / 2); // Ajustar posición al borde
					this.velocidad = -this.velocidad; // Invertir la dirección (ahora se mueve a la izquierda)
				}

				// Si la tortuga llega al borde izquierdo de la isla, invertir la dirección
				if (this.limiteIzq < is.getLimiteIzq()) {
					this.x = is.getLimiteIzq() + (this.ancho / 2); // Ajustar posición al borde
					this.velocidad = -this.velocidad; // Invertir la dirección (ahora se mueve a la derecha)
				}

				break; // Romper el bucle ya que la tortuga está moviéndose sobre una isla
			}
		}
	}

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

	public Image getImagenTotuga() {
		return imagen;
	}

	public void setImagenTortuga(Image imagen) {
		this.imagen = imagen;
	}

	public double getAltoTortuga() {
		return alto;
	}

	public void setAltoTortuga(double alto) {
		this.alto = alto;
	}

	public double getAnchoTortuga() {
		return ancho;
	}

	public void setAnchoTortuga(double ancho) {
		this.ancho = ancho;
	}

	public double getLimiteIzq() {
		return limiteIzq;
	}

	public void setLimiteIzq(double limiteIzq) {
		this.limiteIzq = limiteIzq;
	}

	public double getLimiteDer() {
		return limiteDer;
	}

	public void setLimiteDer(double limiteDer) {
		this.limiteDer = limiteDer;
	}

	public double getLimiteSup() {
		return limiteSup;
	}

	public void setLimiteSup(double limiteSup) {
		this.limiteSup = limiteSup;
	}

	public double getLimiteInf() {
		return limiteInf;
	}

	public void setLimiteInf(double limiteInf) {
		this.limiteInf = limiteInf;
	}

}

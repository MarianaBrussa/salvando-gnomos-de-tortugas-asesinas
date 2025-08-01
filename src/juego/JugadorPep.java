package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class JugadorPep {
	private Image[] imagen;
	private double x;
	private double y;
	private double alto;
	private double ancho;
	private double velocidadY;
	private boolean saltando;
	private double saltoY = 0.5;
	private char direccionPep;
	private int direImagen;

	public JugadorPep(double x, double y, double ancho, double alto) {
		this.imagen = new Image[2];
		this.x = x;
		this.y = y;
		this.alto = 60;
		this.ancho = 20.0;
		this.velocidadY = 0;
		this.saltando = false;
		this.direccionPep = '0';
		imagen[0] = Herramientas.cargarImagen("Imagenes/pepDer.png").getScaledInstance(25, 60, Image.SCALE_SMOOTH);
		imagen[1] = Herramientas.cargarImagen("Imagenes/pepIzq.png").getScaledInstance(25, 60, Image.SCALE_SMOOTH);

		// le sume un int al alto y ancho para que la imagen sea mas grande
		this.direImagen = 0; // Imagen inicial
	}

	public void DibujarPep(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, new Color(0, 0, 0, 0));
		entorno.dibujarImagen(imagen[direImagen], this.x, this.y, 0);
	}

	public void caminar(Entorno entorno, char teclaPresionada) {

		if (teclaPresionada == entorno.TECLA_DERECHA) {
			x++;
			direccionPep = entorno.TECLA_DERECHA;
			direImagen = 0;
		} else if (teclaPresionada == entorno.TECLA_IZQUIERDA) {
			x--;
			direccionPep = entorno.TECLA_IZQUIERDA;
			direImagen = 1;

		}
	}

	public boolean tieneDireccion() {
		return this.direccionPep != '0';
	}

	public void iniciarSalto() {
		if (!saltando) {
			this.saltando = true;
			this.velocidadY = -10; // Velocidad inicial hacia arriba

		}
	}

	public void saltar() {
		if (saltando) {
			y += velocidadY; // Actualiza la posición vertical según la velocidad actual
			velocidadY += saltoY; // caida con gravedad

			// Detener el salto cuando empieza a caer
			if (velocidadY > 0) {
				saltando = false;
			}
		}
	}

	public void caer() {
		if (!saltando) {
			y += 3;

			// Si no está saltando, caer de manera gradual
		}
	}

	public boolean puedoSaltar(Islas[] isla) {
		if (y - alto / 2 <= 0) {
			return false;
		}
		for (Islas i : isla) {
			if (colisionIsla(i)) {
				return true;
			}
		}
		return false;
	}

	public boolean colisionIsla(Islas i) {
		return (this.y + (this.alto / 2) >= i.getY() - (i.getAlto() / 2) && this.y <= i.getY() - (i.getAlto() / 2))
				&& this.x + (this.ancho / 2) > i.getX() - (i.getAncho() / 2)
				&& this.x - (this.ancho / 2) < i.getX() + (i.getAncho() / 2);
	}

	public boolean colisionaConTortuga(TortugaAsesina t) {
		return (this.x + (this.ancho / 2) > t.getLimiteIzq() && this.x - (this.ancho / 2) < t.getLimiteDer()
				&& this.y + (this.alto / 2) > t.getLimiteSup() && this.y - (this.alto / 2) < t.getLimiteInf());
	}

	public boolean colisionaConGnomos(Gnomos g) {
		return (this.y + (this.alto / 2) >= g.getY() - (g.getAlto() / 2) && this.y <= g.getY() - (g.getAlto() / 2))
				&& this.x + (this.ancho / 2) > g.getX() - (g.getAncho() / 2)
				&& this.x - (this.ancho / 2) < g.getX() + (g.getAncho() / 2);
	}

	public boolean getSaltando() {
		return saltando;
	}

	public void setSaltando(boolean saltando) {
		this.saltando = saltando;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setPosicion(double y) {
		this.y = y;
	}

	public char getDireccionPep() {
		return direccionPep;
	}

	public void setDireccionPep(char direccionPep) {
		this.direccionPep = direccionPep;
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

}

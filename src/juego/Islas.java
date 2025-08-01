package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Islas {
	private Image imagen;
	private double x;
	private double y;
	private double ancho;
	private double alto;
	private double limiteIzq;
	private double limiteDer;
	private double limiteSup;
	private double limiteInf;

	public Islas(double x, double y, double ancho, double alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.limiteIzq = x - (ancho / 2); // Calcula el límite izquierdo
		this.limiteDer = x + (ancho / 2); // Calcula el límite derecho
		this.limiteSup = y - (alto / 2); // Calcula el límite superior
		this.limiteInf = y + (alto / 2); // Calcula el límite inferior
		this.imagen = Herramientas.cargarImagen("Imagenes/isla.png").getScaledInstance(95, 30, Image.SCALE_SMOOTH);
	}

	public void dibujarIsla(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, new Color(0, 0, 0, 0));

		entorno.dibujarImagen(imagen, x, y -10, 0);
	}

	public Image getImagen() {
		return imagen;
	}

	public void setImagen(Image imagen) {
		this.imagen = imagen;
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

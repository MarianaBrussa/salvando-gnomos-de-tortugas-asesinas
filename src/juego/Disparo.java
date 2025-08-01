package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Disparo {
	private Image imagen;
	private double x;
	private double y;
	private double alto;
	private double ancho;
	private double velocidad;
	private boolean estado;
	private char direccion;

	public Disparo(double x, double y, double direccionPep) {
		this.x = x;
		this.y = y;
		this.alto = 20;
		this.ancho = 20;
		this.velocidad = 0.5;
		this.estado = false;
		this.direccion = 0;
		this.imagen = Herramientas.cargarImagen("Imagenes/disparo.png").getScaledInstance(45, 25, Image.SCALE_SMOOTH);
	}

	public void disparar(char direccionPep, Entorno e) {
		if (direccionPep == e.TECLA_DERECHA) {
			this.x += this.velocidad * Math.cos(direccionPep) + 4;
		} else if (direccionPep == e.TECLA_IZQUIERDA) {
			this.x -= this.velocidad * Math.cos(direccionPep) + 4;

		}
	}

	public void dibujarDisparo(Entorno e) {
		e.dibujarRectangulo(x, y, ancho, alto, 0, new Color(0, 0, 0, 0));
		e.dibujarImagen(imagen, x, y, 0);
	}

	public boolean fueraDePantalla() {
		int anchoPantalla = 800;
		int altoPantalla = 600;

		// verifico si el disparo está fuera de la pantalla
		if (this.x < 0 || this.x > anchoPantalla || this.y < 0 || this.y > altoPantalla) {
			return true;
		}
		return false;
	}

	public boolean colisionaConTortuga(TortugaAsesina[] tortugas) {
		// Recorre todas las tortugas en el array
		for (int i = 0; i < tortugas.length; i++) {
			TortugaAsesina tortuga = tortugas[i];
			if (tortuga != null) {
				if (this.x >= tortuga.getX() - tortuga.getAnchoTortuga() / 2
						&& this.x <= tortuga.getX() + tortuga.getAnchoTortuga() / 2
						&& this.y >= tortuga.getY() - tortuga.getAltoTortuga() / 2
						&& this.y <= tortuga.getY() + tortuga.getAltoTortuga() / 2) {
					tortugas[i] = null; // Elimina la tortuga asignando null
					return true;
				}
			}
		}
		return false;

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

	public double getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public char getDireccion() {
		return direccion;
	}

	public void setDireccion(char direccion) {
		this.direccion = direccion;
	}

}

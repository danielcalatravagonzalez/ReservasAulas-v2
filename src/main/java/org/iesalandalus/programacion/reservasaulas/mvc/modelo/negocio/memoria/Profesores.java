package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;

public class Profesores implements IProfesores {
	// Atributos
	private List<Profesor> coleccionProfesores;

	// Constructor por defecto
	public Profesores() {
		coleccionProfesores = new ArrayList<>();
	}

	// Constructor copia, valida null, si no es null coge el setter
	public Profesores(IProfesores copiaProfesores) {
		if (copiaProfesores == null) {
			throw new NullPointerException("ERROR: No se pueden copiar profesores nulos.");
		} else {
			setProfesores(copiaProfesores);
		}
	}
	
	// Método setProfesores, valida null, si no es null obtiene arraylist por getProfesores
	// a coleccionProfesores
	private void setProfesores(IProfesores profesores) {
		if (profesores == null) {
			throw new NullPointerException("ERROR: No se puede copiar un profesor nulo.");
		} else {
			this.coleccionProfesores = profesores.getProfesores();
		}
	}
	
	// Método copiaProfundaProfesores
	private List<Profesor> copiaProfundaProfesores(List<Profesor> listaProfesores) {
		List<Profesor> copiaProfunda = new ArrayList<>();
		Iterator<Profesor> iterador = listaProfesores.iterator();
		while (iterador.hasNext()) {
			copiaProfunda.add(new Profesor(iterador.next()));
		}
		return copiaProfunda;
	}

	// Método List<Profesor> getProfesores(), coge una copia del método copiaProfunda para evitar aliasing
	@Override
	public List<Profesor> getProfesores() {
		List<Profesor> profesoresOrdenados = copiaProfundaProfesores(coleccionProfesores);
		profesoresOrdenados.sort(Comparator.comparing(Profesor::getCorreo));
		return profesoresOrdenados;
	}

	// Método getNumProfesores, obtiene tamaño de la coleccion
	@Override
	public int getNumProfesores() {
		return coleccionProfesores.size();

	}

	// Método insertar, valida null, si no es null comprueba si en coleccionProfesores
	// no está metido el profesor y si no está lo añadimos
	@Override
	public void insertar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede insertar un profesor nulo.");
		} else if (!coleccionProfesores.contains(profesor)) {
			coleccionProfesores.add(new Profesor(profesor));
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un profesor con ese correo.");
		}
	}

	// Método buscar, validamos null, si no es null utilizamos index of para ver si encuentra ese profesor,
	// si no existe saldrá -1 y se devuelve null, si no añadimos ese profesor
	@Override
	public Profesor buscar(Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede buscar un profesor nulo.");
		}
		Profesor profesorEncontrado = null;
		int indice = coleccionProfesores.indexOf(profesor);
		if (indice == -1) {
			profesorEncontrado = null;
		} else {
			profesorEncontrado = new Profesor(coleccionProfesores.get(indice));
		}
		return profesorEncontrado;
	}

	// Método borrar, validamos null, si no es null comprueba si la colección tiene el profesor
	// y si está dentro lo borra
	@Override
	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede borrar un profesor nulo.");
		} else if (!coleccionProfesores.contains(profesor)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún profesor con ese correo.");
		} else {
			coleccionProfesores.remove(profesor);
		}
	}

	// Metodo representar: guarda arrayList en toString pasando por iterador
	@Override
	public List<String> representar() {
		List<String> representacion = new ArrayList<String>();
		Iterator<Profesor> iterador = coleccionProfesores.iterator();
		while (iterador.hasNext()) {
			representacion.add(iterador.next().toString());
		}
		return representacion;
	}
}

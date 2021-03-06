package timeline.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import timeline.model.Empresa;
import timeline.persistence.DaoFactory;
import timeline.persistence.EmpresaDao;
import timeline.persistence.PersistenceException;

public class EmpresaDaoTests {

	EmpresaDao dao = DaoFactory.getEmpresaDao(); //devuelve un dao

	Empresa aerolinea = new Empresa(2,"Aerolinea xxx");
	Empresa supermercado = new Empresa(5,"Super Pirulo");

	@Before
	public void setUp() throws PersistenceException {
		// se borran todos los empresas para iniciar con la base vacia
		for (Empresa cadaEmpresa : dao.findAll()) { //aca trae la lista completa 
			dao.delete(cadaEmpresa); //va borrando a cada persona
		}

		dao.insert(aerolinea);
		dao.insert(supermercado);
	}

	@After
	public void tearDown() throws PersistenceException {
		// se borran todos los empresas creados en forma global
		dao.delete(aerolinea);
		dao.delete(supermercado);

	}

	@Test
	public void testQueSePuedeBuscarUnEmpresa() throws PersistenceException {

		Empresa empresaEncontrado = dao.findById(aerolinea.getId_Empresa());

		assertNotNull("el empresa con id 2 debe existir", empresaEncontrado);
		assertEquals("el empresa 2 tiene nombre: Aerolinea xxx", "Aerolinea xxx", empresaEncontrado.getRazon_Social());

	}

	@Test
	public void testQueSePuedeInsertarUnEmpresa() throws PersistenceException {

		Empresa libreria = new Empresa (1,"libreria pixel");
		assertEquals("antes de insertar hay 2 empresas", 2, dao.findAll().size());

		dao.insert(libreria);
		assertEquals("luego de insertar hay 3 empresas", 3, dao.findAll().size());
		assertNotNull("que existe un empresa con ese id", dao.findById(libreria.getId_Empresa()));

	}
	
	@Test
	public void testQueSePuedeBorrarUnEmpresa() throws PersistenceException {

		Empresa empresaEncontrado = dao.findById(aerolinea.getId_Empresa());
		dao.delete(empresaEncontrado);

		empresaEncontrado = dao.findById(1);
		assertNull("el empresa con ese id no deberia existir", empresaEncontrado);

	}
	
	@Test
	public void testQueSePuedeActualizarUnEmpresa() throws PersistenceException {

		Empresa empresaEncontrado = dao.findById(aerolinea.getId_Empresa());
		assertEquals("la persona con id 1 se llama Aerolinea xxx", "Aerolinea xxx", empresaEncontrado.getRazon_Social());
		empresaEncontrado.setRazon_Social("Aerolinea Lan");
		dao.update(empresaEncontrado);
		assertEquals("el empresa ahora es Aerolinea Lan", "Aerolinea Lan", empresaEncontrado.getRazon_Social());

	}

	@Test
	public void testQueSePuedenBuscarTodosLosEmpresas() throws PersistenceException {

		List<Empresa> todoslosEmpresas = dao.findAll();
		assertEquals("se espera que haya dos empresas en la base", 2, todoslosEmpresas.size());

	}

}

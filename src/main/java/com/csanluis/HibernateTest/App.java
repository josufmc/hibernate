package com.csanluis.HibernateTest;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.csanluis.HibernateTest.model.City;
import com.csanluis.HibernateTest.model.Country;
import com.csanluis.HibernateTest.model.CountryLanguage;

/**
 * Aplicación de prueba para las cachés L1 y L2 de Hibernate
 */
public class App {
	
	/**
	 * Nota: Para probar la aplicación, debe estar cargada la base 
	 * de datos world en localhost de MySQL que se ha utilizado en clase
	 */
	public static void main(String[] args) {
		
		/* Descomentar un método u otro para las pruebas. 
		 * Cada método tiene internamente comentarios sobre 
		 * las pruebas y qué ocurre en cada test.
		 * */
		testCacheL1();
		//testCacheL2();
	}
	
	/**
	 * Prueba de caché L1
	 */
	public static void testCacheL1(){
		Configuration cfg = new Configuration()
				.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
				.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/world")
				.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
				.setProperty("hibernate.cache.use_second_level_cache", "false")
				.setProperty("hibernate.connection.username", "root").setProperty("hibernate.connection.password", "")
				.setProperty("hibernate.show_sql", "true")
				.setProperty("hibernate.current_session_context_class", "thread")
				.addAnnotatedClass(Country.class)
				.addAnnotatedClass(City.class)
				.addAnnotatedClass(CountryLanguage.class)
				;

		SessionFactory sf = cfg.buildSessionFactory();
		// Obtenemos una sesión
		Session s = sf.openSession();
		// Obtenemos otra sesión
		Session s2 = sf.openSession();

		// Abrimos las sesiones
		s.beginTransaction();
		s2.beginTransaction();
		
		// Obtenemos el país 4 veces, podemos ver que hibernate 
		// sólo lanza la consulta una vez en el grupo de las sessión s. 
		Country country1 = (Country)s.load(Country.class, "NLD");
		Country country2 = (Country)s.load(Country.class, "NLD");
		Country country3 = (Country)s.load(Country.class, "NLD");
		// Si cambiamos de sesión, vuelve a realizar la consulta. 
		Country country4 = (Country)s2.load(Country.class, "NLD");
		
		// Terminamos las transacciones
		s.getTransaction().commit();
		s2.getTransaction().commit();
		
		// Imprimimos los paises
		System.out.println(country1.getName());
		System.out.println(country2.getName());
		System.out.println(country3.getName());
		System.out.println(country4.getName());
		
		System.out.println("OK!");
	}
	
	/**
	 * Prueba de caché L2
	 */
	public static void testCacheL2(){
		
		Configuration cfg = new Configuration()
				.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
				.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/world")
				.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
				// Metemos la EHCache para caché de nivel 2
				.setProperty("hibernate.cache.use_second_level_cache", "true")
				.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.EhCacheProvider")
				.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.EhCacheRegionFactory")
				//.setProperty("hibernate.cache.provider_class", "net.sf.ehcache.hibernate.EhCacheProvider")
				//.setProperty("hibernate.cache.region.factory_class", "net.sf.ehcache.hibernate.EhCacheRegionFactory")
				.setProperty("hibernate.connection.username", "root").setProperty("hibernate.connection.password", "")
				.setProperty("hibernate.show_sql", "true")
				.setProperty("hibernate.current_session_context_class", "thread")
				.addAnnotatedClass(Country.class)
				.addAnnotatedClass(City.class)
				.addAnnotatedClass(CountryLanguage.class)
				;

		SessionFactory sf = cfg.buildSessionFactory();
		// Obtenemos una sesión
		Session s = sf.openSession();
		// Obtenemos otra sesión
		Session s2 = sf.openSession();

		// Abrimos las sesiones
		s.beginTransaction();
		s2.beginTransaction();
		
		// Obtenemos el país 4 veces, podemos ver que hibernate 
		// sólo lanza la consulta la primera vez. 
		Country country1 = (Country)s.load(Country.class, "NLD");
		Country country2 = (Country)s.load(Country.class, "NLD");
		Country country3 = (Country)s.load(Country.class, "NLD");
		
		// Si cambiamos de sesión, tampoco hace la consulta 
		// ya que hemos marcado la entidad como cacheable 
		// (Está en la caché de L2). 
		Country country4 = (Country)s2.load(Country.class, "NLD");
		
		// Terminamos las transacciones
		s.getTransaction().commit();
		s2.getTransaction().commit();
		
		// Imprimimos los paises
		System.out.println(country1.getName());
		System.out.println(country2.getName());
		System.out.println(country3.getName());
		System.out.println(country4.getName());
		
		System.out.println("OK!");
	}
	
	/**
	 * Mostramos los paises normalmente
	 */
	public static void showAllCountries(){
		Configuration cfg = new Configuration()
				.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
				.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/world")
				.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
				.setProperty("hibernate.cache.use_second_level_cache", "false")
				.setProperty("hibernate.connection.username", "root").setProperty("hibernate.connection.password", "")
				//.setProperty("hibernate.show_sql", "true")
				.setProperty("hibernate.current_session_context_class", "thread")
				.addAnnotatedClass(Country.class)
				.addAnnotatedClass(City.class)
				.addAnnotatedClass(CountryLanguage.class)
				;

		SessionFactory sf = cfg.buildSessionFactory();
		Session s = sf.getCurrentSession();

		// Obtenemos los paises
		s.beginTransaction();	
		Criteria c = s.createCriteria(Country.class);
		//c.setFetchMode("cities", FetchMode.SELECT);
		c.setFetchMode("cities", FetchMode.JOIN);	// Lo ponemos así porque mejor que saque más datos que varias consultas
		c.setFetchMode("capital", FetchMode.JOIN);
		@SuppressWarnings("unchecked")
		List<Country> countries = c.list();
		for(Country country: countries){
			City capital = country.getCapital();
			Set<CountryLanguage> languages = country.getCountryLanguages();
			System.out.println(country.getName() + " -> " + (capital == null ? "" : capital.getName()) + " total: " + languages.size() + " languages");
		}
		s.getTransaction().commit();
		System.out.println("OK!");
	}
}

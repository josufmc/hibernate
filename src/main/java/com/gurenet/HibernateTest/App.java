package com.gurenet.HibernateTest;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.gurenet.HibernateTest.model.Country;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		Configuration cfg = new Configuration()
				.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
				.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/world")
				.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
				.setProperty("hibernate.connection.username", "root").setProperty("hibernate.connection.password", "")
				.setProperty("hibernate.show_sql", "true")
				.setProperty("hibernate.current_session_context_class", "thread").addAnnotatedClass(Country.class);

		SessionFactory sf = cfg.buildSessionFactory();
		Session s = sf.getCurrentSession();

		// Obtenemos los paises
		s.beginTransaction();	
		Criteria c = s.createCriteria(Country.class);
		c.setFetchMode("cities", FetchMode.SELECT);
		List<Country> countries = c.list();
		s.getTransaction().commit();
		
		//c.setFetchMode("alumnos", FetchMode.JOIN);
		
	}
}
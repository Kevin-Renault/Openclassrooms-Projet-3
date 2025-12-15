package com.openclassrooms.projet3.excercice1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale de l'application Rental - API de gestion de locations.
 * Cette application Spring Boot fournit une API REST pour gérer les locations
 * immobilières, les utilisateurs et les messages entre propriétaires et
 * locataires potentiels.
 * 
 * @author Kévin Renaults
 */
@SpringBootApplication
public class RentalApplication {

	/**
	 * Point d'entrée de l'application Spring Boot.
	 * 
	 * @param args Arguments de ligne de commande
	 */
	public static void main(String[] args) {
		SpringApplication.run(RentalApplication.class, args);
	}

}

package presentacion; /** @file Main.java
 @brief Código de la clase Main
 */

import clases.*;
import clases.inout;
import controladores.*;

import java.util.*;


/**
 * @brief Sistema de recomendaciones a un usuario basado en valoraciones previamente hechas a otros items
 *
 * @author Carla Campàs Gené
 * @author Pau Vallespí Monclús
 * @author Andres Eduardo Bercowsky Rama
 * @author Beatriz Gomes da Costa
 *
 * @date November 2021
 */


public class Main {
	/*
		------------------ PROJECTE DE PROGRAMACIÓ ------------------
		GRUP: 2.1

		PROFESSOR:
		  · Borja Vallès

		MEMBRES:
		  · Andrés Eduardo Bercowsky Rama
		  · Carla Campàs Gené
		  · Beatriz Gomes da Costa
		  · Pau Vallespí Monclús
		-------------------------------------------------------------
	*/

	public static void main(String[] args) throws Exception {
		//CtrlPersistencia.borrarCarpeta("DATA/2378");
		CtrlPresentacion.iniPresentacion();
	}
}

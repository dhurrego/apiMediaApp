package com.deividurrego.service;

import com.deividurrego.model.Archivo;

public interface IArchivoService {

	int guardar(Archivo archivo);
	byte[] leerArchivo(Integer idArchivo);
	
}

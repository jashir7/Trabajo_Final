export const recursos = {
  usuarios: {
    titulo: "Usuarios",
    endpoint: "usuarios",
    campos: [
      { nombre: "nombres", etiqueta: "Nombres", maxLength: 100 },
      { nombre: "apellidos", etiqueta: "Apellidos", maxLength: 100 },
      { nombre: "correo", etiqueta: "Correo", tipo: "email", maxLength: 180 },
    ],
  },
  aprendices: {
    titulo: "Aprendices",
    endpoint: "aprendices",
    campos: [
      { nombre: "nombres", etiqueta: "Nombres", maxLength: 100 },
      { nombre: "apellidos", etiqueta: "Apellidos", maxLength: 100 },
      { nombre: "tipoDocumento", etiqueta: "Tipo de documento", maxLength: 30 },
      { nombre: "numeroDocumento", etiqueta: "Número de documento", maxLength: 40 },
      { nombre: "correo", etiqueta: "Correo", tipo: "email", maxLength: 180 },
      {
        nombre: "fichaId",
        etiqueta: "Ficha",
        tipo: "select",
        requerido: false,
        endpointOpciones: "fichas",
        etiquetaOpcion: (ficha) => `${ficha.codigo} - ${ficha.nombre}`,
      },
    ],
  },
  instructores: {
    titulo: "Instructores",
    endpoint: "instructores",
    campos: [
      { nombre: "nombres", etiqueta: "Nombres", maxLength: 100 },
      { nombre: "apellidos", etiqueta: "Apellidos", maxLength: 100 },
      { nombre: "correo", etiqueta: "Correo", tipo: "email", maxLength: 180 },
      { nombre: "especialidad", etiqueta: "Especialidad", maxLength: 120 },
    ],
  },
  fichas: {
    titulo: "Fichas",
    endpoint: "fichas",
    campos: [
      { nombre: "codigo", etiqueta: "Código", maxLength: 40 },
      { nombre: "nombre", etiqueta: "Nombre", maxLength: 120 },
      { nombre: "programa", etiqueta: "Programa", maxLength: 150 },
      { nombre: "jornada", etiqueta: "Jornada", maxLength: 40 },
    ],
  },
  competencias: {
    titulo: "Competencias",
    endpoint: "competencias",
    campos: [
      { nombre: "codigo", etiqueta: "Código", maxLength: 40 },
      { nombre: "nombre", etiqueta: "Nombre", maxLength: 150 },
      {
        nombre: "descripcion",
        etiqueta: "Descripción",
        tipo: "textarea",
        maxLength: 1000,
      },
    ],
  },
  evaluaciones: {
    titulo: "Evaluaciones",
    endpoint: "evaluaciones",
    campos: [
      {
        nombre: "aprendizId",
        etiqueta: "Aprendiz",
        tipo: "select",
        endpointOpciones: "aprendices",
        etiquetaOpcion: (aprendiz) =>
          `${aprendiz.nombres} ${aprendiz.apellidos} (ID ${aprendiz.id})`,
      },
      {
        nombre: "competenciaId",
        etiqueta: "Competencia",
        tipo: "select",
        endpointOpciones: "competencias",
        etiquetaOpcion: (competencia) =>
          `${competencia.codigo} - ${competencia.nombre}`,
      },
      {
        nombre: "calificacion",
        etiqueta: "Calificación",
        tipo: "number",
        minimo: 0,
        maximo: 5,
        paso: 0.01,
      },
      { nombre: "fecha", etiqueta: "Fecha", tipo: "date" },
    ],
  },
};

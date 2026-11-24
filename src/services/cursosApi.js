import {
  eliminarRecurso,
  guardarRecurso,
  listarRecurso,
} from "./api";

export function listarCursos() {
  return listarRecurso("cursos");
}

export function guardarCurso(curso, id) {
  return guardarRecurso("cursos", curso, id);
}

export function eliminarCurso(id) {
  return eliminarRecurso("cursos", id);
}

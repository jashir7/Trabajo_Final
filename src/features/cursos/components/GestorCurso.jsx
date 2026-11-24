import { useState } from "react";
import {
  eliminarCurso as eliminarCursoApi,
  guardarCurso,
} from "../../../services/cursosApi";
import "./GestorCurso.css";

function GestorCurso({ cursos, setCursos }) {
  const [nombre, setNombre] = useState("");
  const [descripcion, setDescripcion] = useState("");
  const [url, setUrl] = useState("");
  const [id, setId] = useState(null);
  const [mensaje, setMensaje] = useState("");
  const [indiceCurso, setIndiceCurso] = useState(0);
  const [guardando, setGuardando] = useState(false);
  const [eliminandoId, setEliminandoId] = useState(null);

  function limpiarFormulario() {
    setNombre("");
    setDescripcion("");
    setUrl("");
    setId(null);
  }

  async function manejarEnvio(evento) {
    evento.preventDefault();

    if (!nombre.trim() || !descripcion.trim() || !url.trim()) {
      setMensaje("Completa todos los campos.");
      return;
    }

    const cursoActualizado = {
      nombre: nombre.trim(),
      descripcion: descripcion.trim(),
      url: url.trim(),
    };

    setGuardando(true);
    setMensaje("");

    try {
      const cursoGuardado = await guardarCurso(cursoActualizado, id);

      if (id !== null) {
        setCursos((actuales) =>
          actuales.map((curso) => (curso.id === id ? cursoGuardado : curso)),
        );
        setMensaje("Curso actualizado correctamente.");
      } else {
        setCursos((actuales) => [...actuales, cursoGuardado]);
        setIndiceCurso(0);
        setMensaje("Curso agregado correctamente.");
      }

      limpiarFormulario();
    } catch (error) {
      setMensaje(
        error instanceof Error ? error.message : "No se pudo guardar el curso.",
      );
    } finally {
      setGuardando(false);
    }
  }

  function editarCurso(curso) {
    setNombre(curso.nombre);
    setDescripcion(curso.descripcion);
    setUrl(curso.url || "");
    setId(curso.id);
    setMensaje("");
  }

  async function eliminarCurso(idEliminar) {
    if (
      !window.confirm("¿Estás seguro de que deseas eliminar este curso?")
    ) {
      return;
    }

    setEliminandoId(idEliminar);
    setMensaje("");

    try {
      await eliminarCursoApi(idEliminar);
      const cursosRestantes = cursos.filter(
        (curso) => curso.id !== idEliminar,
      );
      setCursos(cursosRestantes);
      setIndiceCurso((actual) =>
        Math.min(actual, Math.max(cursosRestantes.length - 1, 0)),
      );

      if (id === idEliminar) limpiarFormulario();
      setMensaje("Curso eliminado correctamente.");
    } catch (error) {
      setMensaje(
        error instanceof Error ? error.message : "No se pudo eliminar el curso.",
      );
    } finally {
      setEliminandoId(null);
    }
  }

  function cancelarEdicion() {
    limpiarFormulario();
    setMensaje("");
  }

  function siguienteCurso() {
    setIndiceCurso((actual) => (actual + 1) % cursos.length);
  }

  const cursoActual = cursos[indiceCurso];

  return (
    <div>
      <form onSubmit={manejarEnvio}>
        <div>
          <label htmlFor="nombre-curso">Nombre del curso:</label>
          <input
            id="nombre-curso"
            type="text"
            value={nombre}
            onChange={(evento) => setNombre(evento.target.value)}
            placeholder="Ej. Inteligencia Artificial"
            required
          />
        </div>

        <div>
          <label htmlFor="descripcion-curso">Descripción:</label>
          <input
            id="descripcion-curso"
            type="text"
            value={descripcion}
            onChange={(evento) => setDescripcion(evento.target.value)}
            placeholder="Describe el curso"
            required
          />
        </div>

        <div>
          <label htmlFor="url-curso">Imagen del curso (URL):</label>
          <input
            id="url-curso"
            type="url"
            value={url}
            onChange={(evento) => setUrl(evento.target.value)}
            placeholder="https://..."
            required
          />
        </div>

        <div>
          <button type="submit" disabled={guardando || eliminandoId !== null}>
            {guardando
              ? "Guardando..."
              : id !== null
                ? "Guardar cambios"
                : "Agregar curso"}
          </button>
          {id !== null && (
            <button type="button" onClick={cancelarEdicion}>
              Cancelar edición
            </button>
          )}
        </div>
      </form>

      {mensaje && (
        <p role="status" aria-live="polite">
          {mensaje}
        </p>
      )}

      <h3>Cursos registrados: {cursos.length}</h3>

      {cursos.length === 0 ? (
        <p>No hay cursos registrados.</p>
      ) : (
        <div className="url">
          <article>
            {cursoActual?.url && (
              <img src={cursoActual.url} alt={cursoActual.nombre} />
            )}
            <h3>{cursoActual?.nombre}</h3>
            <p>{cursoActual?.descripcion}</p>
            {cursoActual && (
              <>
                <button type="button" onClick={() => editarCurso(cursoActual)}>
                  Editar
                </button>
                <button
                  type="button"
                  disabled={guardando || eliminandoId !== null}
                  onClick={() => eliminarCurso(cursoActual.id)}
                >
                  {eliminandoId === cursoActual.id
                    ? "Eliminando..."
                    : "Eliminar"}
                </button>
              </>
            )}
          </article>
        </div>
      )}
      {cursos.length > 1 && (
        <div>
          <p>
            {indiceCurso + 1} de {cursos.length}
          </p>
          <button type="button" onClick={siguienteCurso}>
            Siguiente
          </button>
        </div>
      )}
    </div>
  );
}

export default GestorCurso;

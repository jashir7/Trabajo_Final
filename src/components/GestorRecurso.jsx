import { useEffect, useState } from "react";
import {
  eliminarRecurso,
  guardarRecurso,
  listarRecurso,
} from "../services/api";
import "./GestorRecurso.css";

function crearFormularioVacio(campos) {
  return Object.fromEntries(campos.map((campo) => [campo.nombre, ""]));
}

function mostrarError(error) {
  return error instanceof Error ? error.message : "Ocurrió un error inesperado.";
}

function GestorRecurso({ recurso }) {
  const [elementos, setElementos] = useState([]);
  const [opciones, setOpciones] = useState({});
  const [formulario, setFormulario] = useState(() =>
    crearFormularioVacio(recurso.campos),
  );
  const [editandoId, setEditandoId] = useState(null);
  const [cargando, setCargando] = useState(true);
  const [guardando, setGuardando] = useState(false);
  const [eliminandoId, setEliminandoId] = useState(null);
  const [error, setError] = useState("");
  const [mensaje, setMensaje] = useState("");

  useEffect(() => {
    let activo = true;
    const endpointsOpciones = [
      ...new Set(
        recurso.campos
          .map((campo) => campo.endpointOpciones)
          .filter(Boolean),
      ),
    ];

    Promise.all([
      listarRecurso(recurso.endpoint),
      ...endpointsOpciones.map((endpoint) => listarRecurso(endpoint)),
    ])
      .then(([datos, ...listasOpciones]) => {
        if (!activo) return;

        setElementos(datos);
        setOpciones(
          Object.fromEntries(
            endpointsOpciones.map((endpoint, indice) => [
              endpoint,
              listasOpciones[indice],
            ]),
          ),
        );
      })
      .catch((loadError) => {
        if (activo) setError(mostrarError(loadError));
      })
      .finally(() => {
        if (activo) setCargando(false);
      });

    return () => {
      activo = false;
    };
  }, [recurso]);

  function cambiarCampo(evento) {
    const { name, value } = evento.target;
    setFormulario((actual) => ({ ...actual, [name]: value }));
  }

  function empezarEdicion(elemento) {
    setFormulario(
      Object.fromEntries(
        recurso.campos.map((campo) => [
          campo.nombre,
          elemento[campo.nombre] ?? "",
        ]),
      ),
    );
    setEditandoId(elemento.id);
    setMensaje("");
    setError("");
  }

  function limpiarFormulario() {
    setFormulario(crearFormularioVacio(recurso.campos));
    setEditandoId(null);
  }

  async function manejarEnvio(evento) {
    evento.preventDefault();
    setGuardando(true);
    setError("");
    setMensaje("");

    const datos = Object.fromEntries(
      recurso.campos.map((campo) => {
        const valor = formulario[campo.nombre];
        if (campo.tipo === "number" || campo.endpointOpciones) {
          return [campo.nombre, valor === "" ? null : Number(valor)];
        }
        return [campo.nombre, valor.trim()];
      }),
    );

    try {
      const elementoGuardado = await guardarRecurso(
        recurso.endpoint,
        datos,
        editandoId,
      );

      if (editandoId === null) {
        setElementos((actuales) => [...actuales, elementoGuardado]);
        setMensaje(`${recurso.titulo}: registro creado correctamente.`);
      } else {
        setElementos((actuales) =>
          actuales.map((elemento) =>
            elemento.id === editandoId ? elementoGuardado : elemento,
          ),
        );
        setMensaje(`${recurso.titulo}: registro actualizado correctamente.`);
      }

      limpiarFormulario();
    } catch (saveError) {
      setError(mostrarError(saveError));
    } finally {
      setGuardando(false);
    }
  }

  async function borrarElemento(id) {
    if (!window.confirm("¿Estás seguro de que deseas eliminar este registro?")) {
      return;
    }

    setEliminandoId(id);
    setError("");
    setMensaje("");

    try {
      await eliminarRecurso(recurso.endpoint, id);
      setElementos((actuales) =>
        actuales.filter((elemento) => elemento.id !== id),
      );
      if (editandoId === id) limpiarFormulario();
      setMensaje(`${recurso.titulo}: registro eliminado correctamente.`);
    } catch (deleteError) {
      setError(mostrarError(deleteError));
    } finally {
      setEliminandoId(null);
    }
  }

  function valorVisible(campo, elemento) {
    const valor = elemento[campo.nombre];
    if (valor === null || valor === undefined || valor === "") {
      return "—";
    }

    if (campo.endpointOpciones) {
      const opcion = opciones[campo.endpointOpciones]?.find(
        (item) => item.id === valor,
      );
      return opcion ? campo.etiquetaOpcion(opcion) : `ID ${valor}`;
    }

    return valor;
  }

  function renderizarCampo(campo) {
    const commonProps = {
      id: `${recurso.endpoint}-${campo.nombre}`,
      name: campo.nombre,
      value: formulario[campo.nombre],
      onChange: cambiarCampo,
      required: campo.requerido !== false,
      maxLength: campo.maxLength,
      min: campo.minimo,
      max: campo.maximo,
      step: campo.paso,
    };

    if (campo.tipo === "textarea") {
      return <textarea {...commonProps} rows="3" />;
    }

    if (campo.tipo === "select") {
      return (
        <select {...commonProps}>
          <option value="">
            {campo.requerido === false ? "Sin asignar" : "Selecciona una opción"}
          </option>
          {(opciones[campo.endpointOpciones] || []).map((opcion) => (
            <option key={opcion.id} value={opcion.id}>
              {campo.etiquetaOpcion(opcion)}
            </option>
          ))}
        </select>
      );
    }

    return <input {...commonProps} type={campo.tipo || "text"} />;
  }

  return (
    <section className="gestor-recurso">
      <h3>{recurso.titulo}</h3>

      {error && <p role="alert">{error}</p>}
      {mensaje && <p role="status">{mensaje}</p>}
      {cargando ? (
        <p role="status">Cargando {recurso.titulo.toLowerCase()}...</p>
      ) : (
        <>
          <form className="recurso-formulario" onSubmit={manejarEnvio}>
            {recurso.campos.map((campo) => (
              <div className="recurso-campo" key={campo.nombre}>
                <label htmlFor={`${recurso.endpoint}-${campo.nombre}`}>
                  {campo.etiqueta}:
                </label>
                {renderizarCampo(campo)}
              </div>
            ))}
            <div className="recurso-acciones">
              <button type="submit" disabled={guardando || eliminandoId !== null}>
                {guardando
                  ? "Guardando..."
                  : editandoId === null
                    ? "Registrar"
                    : "Guardar cambios"}
              </button>
              {editandoId !== null && (
                <button type="button" onClick={limpiarFormulario}>
                  Cancelar edición
                </button>
              )}
            </div>
          </form>

          <h4>Registros: {elementos.length}</h4>
          {elementos.length === 0 ? (
            <p>No hay registros.</p>
          ) : (
            <div className="recurso-lista">
              {elementos.map((elemento) => (
                <article className="recurso-tarjeta" key={elemento.id}>
                  <dl>
                    <div>
                      <dt>ID</dt>
                      <dd>{elemento.id}</dd>
                    </div>
                    {recurso.campos.map((campo) => (
                      <div key={campo.nombre}>
                        <dt>{campo.etiqueta}</dt>
                        <dd>{valorVisible(campo, elemento)}</dd>
                      </div>
                    ))}
                  </dl>
                  <div className="recurso-acciones">
                    <button
                      type="button"
                      disabled={guardando || eliminandoId !== null}
                      onClick={() => empezarEdicion(elemento)}
                    >
                      Editar
                    </button>
                    <button
                      type="button"
                      disabled={guardando || eliminandoId !== null}
                      onClick={() => borrarElemento(elemento.id)}
                    >
                      {eliminandoId === elemento.id
                        ? "Eliminando..."
                        : "Eliminar"}
                    </button>
                  </div>
                </article>
              ))}
            </div>
          )}
        </>
      )}
    </section>
  );
}

export default GestorRecurso;

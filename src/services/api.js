const API_BASE_URL =
  import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api";

async function solicitar(path, options) {
  let response;

  try {
    response = await fetch(`${API_BASE_URL}${path}`, options);
  } catch {
    throw new Error(
      "No se pudo conectar con el backend. Comprueba que Spring Boot esté ejecutándose.",
    );
  }

  if (response.status === 204) {
    return null;
  }

  const data = await response.json();

  if (!response.ok) {
    const detalles = data.details
      ? Object.values(data.details).join(" ")
      : "";
    throw new Error([data.message, detalles].filter(Boolean).join(": "));
  }

  return data;
}

export function listarRecurso(recurso) {
  return solicitar(`/${recurso}`);
}

export function guardarRecurso(recurso, datos, id = null) {
  const actualizando = id !== null;

  return solicitar(actualizando ? `/${recurso}/${id}` : `/${recurso}`, {
    method: actualizando ? "PUT" : "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(datos),
  });
}

export function eliminarRecurso(recurso, id) {
  return solicitar(`/${recurso}/${id}`, {
    method: "DELETE",
  });
}

export function obtenerResumen() {
  return solicitar("/dashboard/resumen");
}

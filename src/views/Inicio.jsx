import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { obtenerResumen } from "../services/api";

const metricas = [
  ["usuarios", "Usuarios"],
  ["aprendices", "Aprendices"],
  ["instructores", "Instructores"],
  ["fichas", "Fichas"],
  ["competencias", "Competencias"],
  ["evaluaciones", "Evaluaciones"],
  ["cursos", "Cursos"],
];

function Inicio() {
  const [resumen, setResumen] = useState(null);
  const [cargandoResumen, setCargandoResumen] = useState(true);
  const [errorResumen, setErrorResumen] = useState("");

  useEffect(() => {
    let activo = true;

    obtenerResumen()
      .then((datos) => {
        if (activo) setResumen(datos);
      })
      .catch((error) => {
        if (activo) setErrorResumen(error.message);
      })
      .finally(() => {
        if (activo) setCargandoResumen(false);
      });

    return () => {
      activo = false;
    };
  }, []);

  return (
    <>
      <section className="inicio" id="inicio">
        <h2>Bienvenidos a nuestro portal de formación</h2>
        <p>Encuentra cursos y fortalece tus habilidades tecnológicas.</p>
        <p>
          <Link to="/cursos">Explorar cursos</Link>
          {" | "}
          <Link to="/registro">Registrarme como aprendiz</Link>
        </p>
      </section>

      <section aria-labelledby="resumen-title">
        <h2 id="resumen-title">Resumen del sistema</h2>
        {cargandoResumen ? (
          <p role="status">Cargando resumen...</p>
        ) : errorResumen ? (
          <p role="alert">{errorResumen}</p>
        ) : (
          <div className="dashboard-resumen">
            {metricas.map(([clave, etiqueta]) => (
              <article key={clave}>
                <h3>{etiqueta}</h3>
                <p>{resumen[clave]}</p>
              </article>
            ))}
          </div>
        )}
      </section>

      <section id="nosotros">
        <h2>Sobre nosotros</h2>
        <p>
          Somos un centro de formación comprometido con el desarrollo de
          nuestros estudiantes y la excelencia educativa.
        </p>
      </section>

      <section id="contacto">
        <h2>Contacto</h2>
        <p>
          Puedes conocer nuestros cursos y registrarte como aprendiz desde
          este portal.
        </p>
      </section>

    </>
  );
}

export default Inicio;
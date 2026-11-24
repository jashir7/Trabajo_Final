import { NavLink, Outlet } from "react-router-dom";
import "./Administracion.css";

const enlaces = [
  ["usuarios", "Usuarios"],
  ["aprendices", "Aprendices"],
  ["instructores", "Instructores"],
  ["fichas", "Fichas"],
  ["competencias", "Competencias"],
  ["evaluaciones", "Evaluaciones"],
];

function Administracion() {
  return (
    <section className="administracion">
      <h2>Administración</h2>
      <nav className="administracion-enlaces" aria-label="Módulos de administración">
        {enlaces.map(([ruta, etiqueta]) => (
          <NavLink key={ruta} to={ruta}>
            {etiqueta}
          </NavLink>
        ))}
      </nav>
      <Outlet />
    </section>
  );
}

export default Administracion;

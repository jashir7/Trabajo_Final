import { NavLink } from 'react-router-dom';

export default function AcademicoPage() {
  return (
    <div>
      <h1>Indice de Contenidos</h1>
      <ul>
        <li><NavLink to="/" end>Inicio</NavLink></li>
        <li><NavLink to="/administracion/fichas">Fichas</NavLink></li>
        <li><NavLink to="/administracion/evaluaciones">Evaluaciones</NavLink></li>
        <li><NavLink to="/administracion/competencias">Competencias</NavLink></li>
        <li><NavLink to="/administracion/instructores">Instructores</NavLink></li>
        <li><NavLink to="/administracion/usuarios">Usuarios</NavLink></li>
        <li><NavLink to="/administracion/aprendices">Aprendices</NavLink></li>
      </ul>
    </div>
  );
}

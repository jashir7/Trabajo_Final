import { useEffect, useState } from 'react';
import './App.css';
import Header from './components/Header';
import Footer from './components/Footer';
import Navbar from './components/Navbar';
import { Navigate, Route, Routes } from 'react-router-dom';
import Inicio from './views/Inicio';
import Cursos from './views/Curso';
import Registro from './views/Registro';
import AcercaDe from './views/AcercaDe';
import Administracion from './views/Administracion';
import GestorRecurso from './components/GestorRecurso';
import { recursos } from './config/recursos';
import { listarCursos } from './services/cursosApi';

function App() {
  const [cursos, setCursos] = useState([]);
  const [cargandoCursos, setCargandoCursos] = useState(true);
  const [errorCargaCursos, setErrorCargaCursos] = useState('');

  useEffect(() => {
    let activo = true;

    listarCursos()
      .then((cursosCargados) => {
        if (activo) {
          setCursos(cursosCargados);
          setErrorCargaCursos('');
        }
      })
      .catch((error) => {
        if (activo) {
          setErrorCargaCursos(error.message);
        }
      })
      .finally(() => {
        if (activo) {
          setCargandoCursos(false);
        }
      });

    return () => {
      activo = false;
    };
  }, []);

  return (
    <div>
      <Header />
      <Navbar />
      <main>
        <Routes>
          <Route path="/" element={<Inicio />} />
          <Route
            path="/cursos"
            element={
              <Cursos
                cursos={cursos}
                setCursos={setCursos}
                cargando={cargandoCursos}
                errorCarga={errorCargaCursos}
              />
            }
          />
          <Route path="/registro" element={<Registro />} />
          <Route path="/acerca-de" element={<AcercaDe />} />
          <Route path="/administracion" element={<Administracion />}>
            <Route
              index
              element={<Navigate to="usuarios" replace />}
            />
            <Route
              path="usuarios"
              element={
                <GestorRecurso
                  key={recursos.usuarios.endpoint}
                  recurso={recursos.usuarios}
                />
              }
            />
            <Route
              path="aprendices"
              element={
                <GestorRecurso
                  key={recursos.aprendices.endpoint}
                  recurso={recursos.aprendices}
                />
              }
            />
            <Route
              path="instructores"
              element={
                <GestorRecurso
                  key={recursos.instructores.endpoint}
                  recurso={recursos.instructores}
                />
              }
            />
            <Route
              path="fichas"
              element={
                <GestorRecurso
                  key={recursos.fichas.endpoint}
                  recurso={recursos.fichas}
                />
              }
            />
            <Route
              path="competencias"
              element={
                <GestorRecurso
                  key={recursos.competencias.endpoint}
                  recurso={recursos.competencias}
                />
              }
            />
            <Route
              path="evaluaciones"
              element={
                <GestorRecurso
                  key={recursos.evaluaciones.endpoint}
                  recurso={recursos.evaluaciones}
                />
              }
            />
          </Route>
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </main>
      <Footer />
    </div>
  );
}

export default App;
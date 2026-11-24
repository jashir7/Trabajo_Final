import { useEffect, useState } from "react";
import { Navigate, Route, Routes } from "react-router-dom";
import "./styles/App.css";
import Header from "./components/layout/Header";
import Footer from "./components/layout/Footer";
import Navbar from "./components/layout/Navbar";
import AcademicoPage from "./features/academico/AcademicoPage";
import CompetenciasPage from "./features/competencias/CompetenciasPage";
import CursosPage from "./features/cursos/CursosPage";
import EvaluacionesPage from "./features/evaluaciones/EvaluacionesPage";
import FichasPage from "./features/fichas/FichasPage";
import InstructoresPage from "./features/instructores/InstructoresPage";
import RegistroPage from "./features/registro/RegistroPage";
import UsuariosPage from "./features/usuarios/UsuariosPage";
import AcercaDePage from "./pages/AcercaDePage";
import Administracion from "./pages/Administracion";
import InicioPage from "./pages/InicioPage";
import GestorRecurso from "./components/GestorRecurso";
import { recursos } from "./config/recursos";
import { listarCursos } from "./services/cursosApi";

function App() {
  const [cursos, setCursos] = useState([]);
  const [cargandoCursos, setCargandoCursos] = useState(true);
  const [errorCargaCursos, setErrorCargaCursos] = useState("");

  useEffect(() => {
    let activo = true;

    listarCursos()
      .then((cursosCargados) => {
        if (activo) setCursos(cursosCargados);
      })
      .catch((error) => {
        if (activo) setErrorCargaCursos(error.message);
      })
      .finally(() => {
        if (activo) setCargandoCursos(false);
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
          <Route path="/" element={<InicioPage />} />
          <Route
            path="/cursos"
            element={
              <CursosPage
                cursos={cursos}
                setCursos={setCursos}
                cargando={cargandoCursos}
                errorCarga={errorCargaCursos}
              />
            }
          />
          <Route path="/registro" element={<RegistroPage />} />
          <Route path="/acerca-de" element={<AcercaDePage />} />
          <Route path="/academico" element={<AcademicoPage />} />
          <Route path="/administracion" element={<Administracion />}>
            <Route index element={<Navigate to="usuarios" replace />} />
            <Route
              path="usuarios"
              element={<UsuariosPage />}
            />
            <Route
              path="aprendices"
              element={<GestorRecurso recurso={recursos.aprendices} />}
            />
            <Route
              path="instructores"
              element={<InstructoresPage />}
            />
            <Route path="fichas" element={<FichasPage />} />
            <Route
              path="competencias"
              element={<CompetenciasPage />}
            />
            <Route
              path="evaluaciones"
              element={<EvaluacionesPage />}
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

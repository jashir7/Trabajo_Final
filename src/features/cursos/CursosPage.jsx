import GestorCurso from "./components/GestorCurso";

function CursosPage({ cursos, setCursos, cargando, errorCarga }) {
  return (
    <section id="cursos">
      <h2>Nuestros cursos</h2>
      <p>
        Explora los cursos disponibles y descubre opciones para desarrollar tus
        habilidades.
      </p>
      {errorCarga && <p role="alert">{errorCarga}</p>}
      {cargando ? (
        <p role="status">Cargando cursos...</p>
      ) : (
        <GestorCurso cursos={cursos} setCursos={setCursos} />
      )}
    </section>
  );
}

export default CursosPage;

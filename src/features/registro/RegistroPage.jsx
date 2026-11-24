import GestorRecurso from "../../components/GestorRecurso";
import { recursos } from "../../config/recursos";

function RegistroPage() {
  return (
    <section id="registro">
      <h2>Registro de aprendiz</h2>
      <p>Registra y consulta aprendices en el sistema.</p>
      <GestorRecurso recurso={recursos.aprendices} />
    </section>
  );
}

export default RegistroPage;

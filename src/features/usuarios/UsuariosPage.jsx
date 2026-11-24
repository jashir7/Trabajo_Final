import GestorRecurso from "../../components/GestorRecurso";
import { recursos } from "../../config/recursos";

function UsuariosPage() {
  return <GestorRecurso recurso={recursos.usuarios} />;
}

export default UsuariosPage;

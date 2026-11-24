import GestorRecurso from "../../components/GestorRecurso";
import { recursos } from "../../config/recursos";

function InstructoresPage() {
  return <GestorRecurso recurso={recursos.instructores} />;
}

export default InstructoresPage;

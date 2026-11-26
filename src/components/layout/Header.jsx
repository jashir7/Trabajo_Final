import "./Header.css";

function Header() {
  return (
    <header className="Header">
      <img src="/ceafi-logo.svg" alt="Logo de CEAFI" className="Header-logo" />
      <div className="Header-brand">
        <p className="Header-eyebrow">CENTRO EDUCATIVO DE ALTO NIVEL Y FORMACIÓN INTEGRAL</p>
        <h1 className="Title">CEAFI</h1>
        <p className="Par">Formamos talento, construimos futuro.</p>
      </div>
    </header>
  );
}

export default Header;

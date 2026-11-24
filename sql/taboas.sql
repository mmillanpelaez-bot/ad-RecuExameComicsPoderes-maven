DROP TABLE IF EXISTS Comic;
DROP TABLE IF EXISTS PoderUnico;
DROP TABLE IF EXISTS Autor;
DROP TABLE IF EXISTS Protagonista;

CREATE TABLE Autor (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    apelidos VARCHAR(100) NOT NULL,
    nacionalidade VARCHAR(50)
);

CREATE TABLE Protagonista (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE PoderUnico (
    id SERIAL PRIMARY KEY,
    nome_poder VARCHAR(100) NOT NULL,
    id_protagonista INTEGER NOT NULL,
    CONSTRAINT fk_poder_protagonista
        FOREIGN KEY (id_protagonista) REFERENCES Protagonista(id)
);

CREATE TABLE Comic (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    ano INTEGER,
    id_autor INTEGER NOT NULL,
    id_protagonista INTEGER NOT NULL,
    CONSTRAINT fk_comic_autor
        FOREIGN KEY (id_autor) REFERENCES Autor(id),
    CONSTRAINT fk_comic_protagonista
        FOREIGN KEY (id_protagonista) REFERENCES Protagonista(id)
);

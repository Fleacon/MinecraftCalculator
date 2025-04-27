CREATE TABLE MobTyp (
    mobTypID INTEGER PRIMARY KEY,
    typ TEXT NOT NULL
);

CREATE TABLE Mob (
    mobID INTEGER PRIMARY KEY,
    bezeichnung TEXT NOT NULL,
    hp INTEGER NOT NULL,
    textur TEXT,
    mobTypID INTEGER,
    FOREIGN KEY (mobTypID) REFERENCES MobTyp(mobTypID)
);

CREATE TABLE Effekt (
    effektID INTEGER PRIMARY KEY,
    bezeichnung TEXT NOT NULL
);

CREATE TABLE Verzauberung (
    verzauberungID INTEGER PRIMARY KEY,
    bezeichnung TEXT NOT NULL
);

CREATE TABLE Waffentyp (
    waffentypID INTEGER PRIMARY KEY,
    typ TEXT NOT NULL
);

CREATE TABLE Rüstungstyp (
    rüstungstypID INTEGER PRIMARY KEY,
    typ TEXT NOT NULL
);

CREATE TABLE Material (
    materialID INTEGER PRIMARY KEY,
    bezeichnung TEXT NOT NULL
);

CREATE TABLE Waffe (
    waffeID INTEGER PRIMARY KEY,
    schaden INTEGER NOT NULL,
    textur TEXT,
    materialID INTEGER,
    waffentypID INTEGER,
    FOREIGN KEY (materialID) REFERENCES Material(materialID),
    FOREIGN KEY (waffentypID) REFERENCES Waffentyp(waffentypID)
);

CREATE TABLE Rüstungsteil (
    rüstungsID INTEGER PRIMARY KEY,
    rüstungsPunkte INTEGER NOT NULL,
    textur TEXT,
    materialID INTEGER,
    rüstungstypID INTEGER,
    FOREIGN KEY (materialID) REFERENCES Material(materialID),
    FOREIGN KEY (rüstungstypID) REFERENCES Rüstungstyp(rüstungstypID)
);
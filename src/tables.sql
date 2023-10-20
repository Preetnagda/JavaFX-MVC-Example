CREATE TABLE Post(
    id INTEGER PRIMARY KEY,
    content TEXT,
    author TEXT,
    likes INTEGER,
    shares INTEGER,
    creationDate DATETIME
);

CREATE TABLE User(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT UNIQUE,
    firstname TEXT,
    lastname TEXT,
    password TEXT,
    is_vip INTEGER DEFAULT 0
);
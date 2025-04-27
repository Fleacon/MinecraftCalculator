import sqlite3
import os

DB_FILE = "../minecraftDB.db"

def connect_to_db(db_file):
    """Connect to SQLite database."""
    try:
        conn = sqlite3.connect(db_file)
        print(f"Connected to database: {db_file}")
        return conn
    except sqlite3.Error as e:
        print(f"SQLite error: {e}")
        return None

# MobTyp(mobTypID, typ)
def insert_mob_types(conn):
    """Insert data into MobTyp table."""
    types = [
        ("Untot"), # 1
        ("Füßler") # 2
    ]
    
    cursor = conn.cursor()
    cursor.executemany("INSERT INTO MobTyp (typ) VALUES (?)", types)
    conn.commit()
    print(f"Inserted {cursor.rowcount} mob types")

# Mob(mobID, bezeichnung, hp, textur, mobTyp#)
def insert_mobs(conn):
    """Insert data into Mob table. Textures are initially NULL."""
    mobs = [
        ("Zombie", 20, None, 1), # 1
        ("Skelett", 20, None, 1) # 2
    ]
    
    cursor = conn.cursor()
    cursor.executemany("INSERT INTO Mob (bezeichnung, hp, textur, mobTypID) VALUES (?, ?, ?, ?)", mobs)
    conn.commit()
    print(f"Inserted {cursor.rowcount} mobs")

# Material(materialID, bezeichnung)
def insert_materials(conn):
    """Insert data into Material table."""
    materials = [
        ("Holz"), # 1
        ("Stein"), # 2
        ("Eisen"), # 3
        ("Gold"), # 4
        ("Diamant"), # 5
        ("Netherit") # 6
    ]
    
    cursor = conn.cursor()
    cursor.executemany("INSERT INTO Material (bezeichnung) VALUES (?)", materials)
    conn.commit()
    print(f"Inserted {cursor.rowcount} materials")

# Waffentyp(waffentypID, typ)
def insert_weapon_types(conn):
    """Insert data into Waffentyp table."""
    types = [
        ("Schwert"), # 1
        ("Axt"), # 2
    ]
    
    cursor = conn.cursor()
    cursor.executemany("INSERT INTO Waffentyp (typ) VALUES (?)", types)
    conn.commit()
    print(f"Inserted {cursor.rowcount} weapon types")

# Waffe(waffeID, schaden, textur, materialID#, waffentypID#)
def insert_weapons(conn):
    """Insert data into Waffe table. Textures are initially NULL."""
    weapons = [
        (4, None, 1, 1),  # 1 - Holz Schwert
        (5, None, 2, 1),  # 2 - Stein Schwert
        (6, None, 3, 1),  # 3 - Eisen Schwert
        (7, None, 5, 1),  # 4 - Diamant Schwert
        (8, None, 6, 1)   # 5 - Netherite Schwert
    ]
    
    cursor = conn.cursor()
    cursor.executemany("INSERT INTO Waffe (schaden, textur, materialID, waffentypID) VALUES (?, ?, ?, ?)", weapons)
    conn.commit()
    print(f"Inserted {cursor.rowcount} weapons")

# Rüstungsteil(rüstungsID, rüstungsPunkte, textur, materialID#, rüstungstypID#)
def insert_armor_types(conn):
    """Insert data into Rüstungstyp table."""
    types = [
        ("Helm"), 
        ("Brustplatte"),
        ("Holz"),
        ("Schuhe")
    ]
    
    cursor = conn.cursor()
    cursor.executemany("INSERT INTO Rüstungstyp (typ) VALUES (?)", types)
    conn.commit()
    print(f"Inserted {cursor.rowcount} armor types")

# Rüstungsteil(rüstungsID, rüstungsPunkte, textur, materialID#, rüstungstypID#)
def insert_armor(conn):
    """Insert data into Rüstungsteil table. Textures are initially NULL."""
    armor = [
        (1, None, 3, 1),  # Eisen
        (5, None, 3, 2),  # Iron chestplate
        (4, None, 3, 3),  # Iron leggings
        (1, None, 3, 4),  # Iron boots
        (3, None, 5, 1)   # Diamond helmet
    ]
    
    cursor = conn.cursor()
    cursor.executemany("INSERT INTO Rüstungsteil (rüstungsPunkte, textur, materialID, rüstungstypID) VALUES (?, ?, ?, ?)", armor)
    conn.commit()
    print(f"Inserted {cursor.rowcount} armor pieces")

# Effekt(effektID, bezeichnung)
def insert_effects(conn):
    """Insert data into Effekt table."""
    effects = [
        ("Stärke"),
        ("Schwäche"),
        ("Resistenz"),
        ("Feuerresistenz")
    ]
    
    cursor = conn.cursor()
    cursor.executemany("INSERT INTO Effekt (bezeichnung) VALUES (?)", effects)
    conn.commit()
    print(f"Inserted {cursor.rowcount} effects")


# Verzauberung(vezauberungID, bezeichnung)
def insert_enchantments(conn):
    """Insert data into Verzauberung table."""
    enchantments = [
        ("Schärfe"),
        ("Stärke"),
        ("Verbrennung"),
        ("Bann"),
        ("Nemesis der Gliederfüßler"),
        ("Schutz"),
        ("Feuerschutz"),
        ("Schusssicher")
    ]
    
    cursor = conn.cursor()
    cursor.executemany("INSERT INTO Verzauberung (bezeichnung) VALUES (?)", enchantments)
    conn.commit()
    print(f"Inserted {cursor.rowcount} enchantments")

def main():
    conn = connect_to_db(DB_FILE)
    if conn:
        try:
            # Insert data into all tables
            insert_mob_types(conn)
            insert_mobs(conn)
            insert_materials(conn)
            insert_weapon_types(conn)
            insert_weapons(conn)
            insert_armor_types(conn)
            insert_armor(conn)
            insert_effects(conn)
            insert_enchantments(conn)
            
            print("Data insertion completed successfully!")
        except sqlite3.Error as e:
            print(f"Error inserting data: {e}")
        finally:
            conn.close()
            print("Database connection closed.")

if __name__ == '__main__':
    main()
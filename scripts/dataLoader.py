import sqlite3
import os

DB_FILE = "../minecraftDB.sqlite"

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
        ("Untot",), # 1
        ("Füßler",) # 2
    ]
    
    cursor = conn.cursor()
    cursor.executemany("INSERT INTO MobTyp (typ) VALUES (?)", types)
    conn.commit()
    print(f"Inserted {cursor.rowcount} mob types")

# model.Mob(mobID, bezeichnung, hp, textur, mobTyp#)
def insert_mobs(conn):
    """Insert data into Mob table. Textures are initially NULL."""
    mobs = [
        ("Zombie", 20, 2, None, 1), # 1
        ("Skelett", 20, 0, None, 1) # 2
    ]
    
    cursor = conn.cursor()
    cursor.executemany("INSERT INTO Mob (bezeichnung, hp, basisRüstungsPunkte, textur, mobTypID) VALUES (?, ?, ?, ?, ?)", mobs)
    conn.commit()
    print(f"Inserted {cursor.rowcount} mobs")

# Material(materialID, bezeichnung)
def insert_materials(conn):
    """Insert data into Material table."""
    materials = [
        ("Holz",), # 1
        ("Stein",), # 2
        ("Eisen",), # 3
        ("Gold",), # 4
        ("Diamant",), # 5
        ("Netherit",), # 6
        ("Leder",), # 7
        ("Ketten",) # 8
    ]
    
    cursor = conn.cursor()
    cursor.executemany("INSERT INTO Material (bezeichnung) VALUES (?)", materials)
    conn.commit()
    print(f"Inserted {cursor.rowcount} materials")

# Waffentyp(waffentypID, typ)
def insert_weapon_types(conn):
    """Insert data into Waffentyp table."""
    types = [
        ("Schwert",), # 1
        ("Axt",) # 2
    ]
    
    cursor = conn.cursor()
    cursor.executemany("INSERT INTO Waffentyp (typ) VALUES (?)", types)
    conn.commit()
    print(f"Inserted {cursor.rowcount} weapon types")

# Waffe(waffeID, bezeichnung, schaden, textur, materialID#, waffentypID#)
def insert_weapons(conn):
    """Insert data into Waffe table."""
    weapons = [
        # Schwerter
        ("Holz Schwert", 4, r"res\weapons\swords\wooden_sword.png", 1, 1),  # 1
        ("Stein Schwert", 5, r"res\weapons\swords\stone_sword.png", 2, 1),  # 2
        ("Eisen Schwert", 6, r"res\weapons\swords\iron_sword.png", 3, 1),  # 3
        ("Gold Schhwert", 4, r"res\weapons\swords\golden_sword.png", 4, 1),  # 4 
        ("Diamant Schwert", 7, r"res\weapons\swords\diamond_sword.png", 5, 1),  # 5
        ("Netherite Schwert", 8, r"res\weapons\swords\netherite_sword.png", 6, 1),  # 6
        # Äxte
        ("Holz Axt", 7, r"res\weapons\axes\wooden_axe.png", 1, 2),  # 7 
        ("Stein Axt", 9, r"res\weapons\axes\stone_axe.png", 2, 2),  # 8
        ("Eisen Axt", 9, r"res\weapons\axes\iron_axe.png", 3, 2),  # 9
        ("Gold Axt", 7, r"res\weapons\axes\golden_axe.png", 4, 2),  # 10
        ("Diamant Axt", 9, r"res\weapons\axes\diamond_axe.png", 5, 2),  # 11
        ("Netherite Axt", 10, r"res\weapons\axes\netherite_axe.png", 6, 2)# 12
    ]
    
    cursor = conn.cursor()
    cursor.executemany("INSERT INTO Waffe (bezeichnung, schaden, textur, materialID, waffentypID) VALUES (?, ?, ?, ?, ?)", weapons)
    conn.commit()
    print(f"Inserted {cursor.rowcount} weapons")

# Rüstungsteil(rüstungsID, rüstungsPunkte, textur, materialID#, rüstungstypID#)
def insert_armor_types(conn):
    """Insert data into Rüstungstyp table."""
    types = [
        ("Helm",), 
        ("Brustplatte",),
        ("Hose",),
        ("Schuhe",)
    ]
    
    cursor = conn.cursor()
    cursor.executemany("INSERT INTO Rüstungstyp (typ) VALUES (?)", types)
    conn.commit()
    print(f"Inserted {cursor.rowcount} armor types")

# Rüstungsteil(rüstungsID, bezeichnung, rüstungsPunkte, härte, textur, materialID#, rüstungstypID#)
def insert_armor(conn):
    """Insert data into Rüstungsteil table. Textures are initially NULL."""
    armor = [
        # Helme
        ("Schildkrötenhelm", 2, 0, r"res\armor\helmets\turtle_helmet.png", None, 1), # 1
        ("Leder Helm", 1, 0, r"res\armor\helmets\leather_helmet.png", 7, 1), # 2
        ("Ketten Helm", 2, 0, r"res\armor\helmets\chainmail_helmet.png", 8, 1), # 3
        ("Eisen Helm", 2, 0, r"res\armor\helmets\iron_helmet.png", 3, 1), # 4
        ("Gold Helm", 2, 0, r"res\armor\helmets\golden_helmet.png", 4, 1), # 5
        ("Diamant Helm", 3, 2, r"res\armor\helmets\diamond_helmet.png", 5, 1), # 6
        ("Netherite Helm", 3, 3, r"res\armor\helmets\netherite_helmet.png", 6, 1), # 7
        # Brustplatten
        ("Leder Brustplatte", 3, 0, r"res\armor\chestplates\leather_chestplate.png", 7, 2), # 8
        ("Ketten Brustplatte", 5, 0, r"res\armor\chestplates\chainmail_chestplate.png", 8, 2), # 9
        ("Eisen Brustplatte", 6, 0, r"res\armor\chestplates\iron_chestplate.png", 3, 2), # 10
        ("Gold Brustplatte", 5, 0, r"res\armor\chestplates\golden_chestplate.png", 4, 2), # 11
        ("Diamant Brustplatte", 8, 2, r"res\armor\chestplates\diamond_chestplate.png", 5, 2), # 12
        ("Netherite Brustplatte", 8, 3, r"res\armor\chestplates\netherite_chestplate.png", 6, 2), # 13
        # Hosen
        ("Leder Hose", 2, 0, r"res\armor\leggings\leather_leggings.png", 7, 3), # 14
        ("Ketten Hose", 4, 0, r"res\armor\leggings\chainmail_leggings.png", 8, 3), # 15
        ("Eisen Hose", 5, 0, r"res\armor\leggings\iron_leggings.png", 3, 3), # 16
        ("Gold Hose", 3, 0, r"res\armor\leggings\golden_leggings.png", 4, 3), # 17
        ("Diamant Hose", 6, 2, r"res\armor\leggings\diamond_leggings.png", 5, 3), # 18
        ("Netherite Hose", 6, 3, r"res\armor\leggings\netherite_leggings.png", 6, 3), # 19
        # Schuhe
        ("Leder Schuhe", 2, 0, r"res\armor\boots\leather_boots.png", 7, 4), # 20
        ("Ketten Schuhe", 4, 0, r"res\armor\boots\chainmail_boots.png", 8, 4), # 21
        ("Eisen Schuhe", 5, 0, r"res\armor\boots\iron_boots.png", 3, 4), # 22
        ("Gold Schuhe", 3, 0, r"res\armor\boots\golden_boots.png", 4, 4), # 23
        ("Diamant Schuhe", 6, 2, r"res\armor\boots\diamond_boots.png", 5, 4), # 24
        ("Netherite Schuhe", 6, 3, r"res\armor\boots\netherite_boots.png", 6, 4)  # 25
    ]
    
    cursor = conn.cursor()
    cursor.executemany("INSERT INTO Rüstungsteil (bezeichnung, rüstungsPunkte, härte, textur, materialID, rüstungstypID) VALUES (?, ?, ?, ?, ?, ?)", armor)
    conn.commit()
    print(f"Inserted {cursor.rowcount} armor pieces")

# Effekt(effektID, bezeichnung)
def insert_effects(conn):
    """Insert data into Effekt table."""
    effects = [
        ("Stärke",),  
        ("Schwäche",),
        ("Resistenz",),
        ("Feuerresistenz",)
    ]
    
    cursor = conn.cursor()
    cursor.executemany("INSERT INTO Effekt (bezeichnung) VALUES (?)", effects)
    conn.commit()
    print(f"Inserted {cursor.rowcount} effects")


# Verzauberung(vezauberungID, bezeichnung)
def insert_enchantments(conn):
    """Insert data into Verzauberung table."""
    enchantments = [
        ("Schärfe",),  
        ("Stärke",),
        ("Verbrennung",),
        ("Bann",),
        ("Nemesis der Gliederfüßler",),
        ("Schutz",),
        ("Feuerschutz",),
        ("Schusssicher",)
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
            print("database connection closed.")

if __name__ == '__main__':
    main()
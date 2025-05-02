import sqlite3
import os

DB_FILE = "../minecraftDB.sqlite"
SQL_SETUP_FILE = "setup.sql"

def execute_sql_setup_script(db_file, sql_file):
    """Creates a new SQLite database and executes SQL script."""
    if not os.path.exists(sql_file):
        print(f"SQL file '{sql_file}' not found!")
        return

    conn = None
    try:
        conn = sqlite3.connect(db_file)
        print(f"Connected to database: {db_file}")
        
        with open(sql_file, 'r', encoding='utf-8') as f:
            sql_script = f.read()
        
        conn.executescript(sql_script)
        print(f"Successfully executed SQL script: {sql_file}")
    except sqlite3.Error as e:
        print(f"SQLite error: {e}")
    finally:
        if conn:
            conn.close()
            print(f"Closed connection to database.")

def main():
    execute_sql_setup_script(DB_FILE, SQL_SETUP_FILE)

if __name__ == '__main__':
    main()
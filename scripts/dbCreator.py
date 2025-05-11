import subprocess

# Run the tableBuilder.py script
subprocess.run(["python", "tableBuilder.py"], check=True)

# Run the dataLoader.py script
subprocess.run(["python", "dataLoader.py"], check=True)

print("database creation and data loading completed successfully.")

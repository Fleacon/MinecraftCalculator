import os
from PIL import Image

# Konfiguration
source_folder = r"..\app\src\main\resources\res\weapons"
target_size = 128  # Zielgröße (quadratisch)
image_extensions = (".jpg", ".jpeg", ".png", ".bmp", ".gif")

def upscale_nearest(image_path, target_size):
    with Image.open(image_path) as img:
        width, height = img.size

        # Nur quadratische Bilder verarbeiten
        if width != height:
            print(f"Übersprungen (nicht quadratisch): {image_path}")
            return

        # Nur hochskalieren, nicht verkleinern
        if width >= target_size:
            print(f"Übersprungen (bereits groß genug): {image_path}")
            return

        resized = img.resize((target_size, target_size), Image.NEAREST)
        resized.save(image_path)
        print(f"Hochskaliert mit NEAREST: {image_path}")

def process_images(folder, target_size):
    for root, _, files in os.walk(folder):
        for file in files:
            if file.lower().endswith(image_extensions):
                img_path = os.path.join(root, file)
                upscale_nearest(img_path, target_size)

process_images(source_folder, target_size)

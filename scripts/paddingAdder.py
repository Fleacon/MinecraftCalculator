import os
from PIL import Image

def add_transparent_border_to_image(image_path, border_size):
    img = Image.open(image_path).convert("RGBA")
    width, height = img.size

    if width != height:
        print(f"Skipping non-square image: {image_path}")
        return

    new_size = width + 2 * border_size
    new_img = Image.new("RGBA", (new_size, new_size), (0, 0, 0, 0))
    new_img.paste(img, (border_size, border_size))

    new_img.save(image_path)
    print(f"Processed: {image_path}")

def process_folder(folder_path, border_size):
    for root, _, files in os.walk(folder_path):
        for file in files:
            if file.lower().endswith((".png", ".jpg", ".jpeg")):
                full_path = os.path.join(root, file)
                try:
                    add_transparent_border_to_image(full_path, border_size)
                except Exception as e:
                    print(f"Failed to process {full_path}: {e}")

# Example usage
if __name__ == "__main__":
    folder_path = r"..\app\src\main\resources\res\armor"  # Replace with your folder
    border_size = 8  # Adjust the border size
    process_folder(folder_path, border_size)

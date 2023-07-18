import os

root_directory = "/home/showkat/train-ticket"

gitignore_content = """\
# Ignore .idea directory
.idea/

# Ignore specific files
test/java/test1.iml
test/test.iml
"""

gitignore_path = os.path.join(root_directory, ".gitignore")

with open(gitignore_path, "w") as file:
    file.write(gitignore_content)

print(f".gitignore file created successfully at {gitignore_path}.")


import os
import shutil


# update below path for the microservice path and the destination path


microservices_parent_path = "source_path"
monolith_parent_path = "dest_path"

# update below list as your requirement


microservices = [
    "ts-admin-basic-info-service",
    "ts-admin-order-service",
    "ts-admin-route-service",
    "ts-admin-travel-service",
    "ts-admin-user-service",
    "ts-assurance-service",
    "ts-auth-service",
    "ts-basic-service",
    "ts-cancel-service",
    "ts-common",
    "ts-config-service",
    "ts-consign-price-service",
    "ts-consign-service",
    "ts-contacts-service",
    "ts-delivery-service",
    "ts-execute-service",
    "ts-food-delivery-service",
    "ts-food-service",
    "ts-gateway-service",
    "ts-inside-payment-service",
    "ts-notification-service",
    "ts-order-other-service",
    "ts-order-service",
    "ts-payment-service",
    "ts-price-service",
    "ts-preserve-other-service",
    "ts-preserve-service",
    "ts-rebook-service",
    "ts-route-plan-service",
    "ts-route-service",
    "ts-seat-service",
    "ts-security-service",
    "ts-station-food-service",
    "ts-station-service",
    "ts-train-food-service",
    "ts-train-service",
    "ts-travel-plan-service",
    "ts-travel-service",
    "ts-travel2-service",
    "ts-user-service",
    "ts-verification-code-service",
    "ts-wait-order-service",
]



# Package mapping
package_mapping = {
    "config": "config",
    "constant": "constant",
    "controller": "controller",
    "dto": "dto",
    "entity": "entity",
    "exception": "exception",
    "init": "init",
    "mq": "mq",
    "repository": "repository",
    "service": "service",
    "impl": "service/impl",
    "util": "util"
}

# Iterate over the list of microservices
for microservice in microservices:
    # Get the list of folders immediately following "main/java"
    microservice_path = os.path.join(microservices_parent_path, microservice, "src", "main", "java")
    subfolders = [folder for folder in os.listdir(microservice_path) if os.path.isdir(os.path.join(microservice_path, folder))]
    
    if len(subfolders) != 1:
        print(f"Error: Expected one subfolder immediately following 'main/java' in microservice {microservice}. Skipping.")
        continue
    
    package_name = subfolders[0]
    
    for source_package, target_package in package_mapping.items():
        microservice_package_path = os.path.join(microservices_parent_path, microservice, "src", "main", "java", package_name, source_package)
        monolith_package_path = os.path.join(monolith_parent_path, "src", "main", "java", target_package)
        
        # Check if the microservice package path exists and it is a directory
        if os.path.exists(microservice_package_path) and os.path.isdir(microservice_package_path):
            # Create the target package path in the monolith folder if it doesn't exist
            os.makedirs(monolith_package_path, exist_ok=True)
            
            # Copy the contents of the microservice package to the monolith package
            for root, dirs, files in os.walk(microservice_package_path):
                for file in files:
                    source_file_path = os.path.join(root, file)
                    target_file_path = os.path.join(monolith_package_path, file)
                    shutil.copy(source_file_path, target_file_path)
                    print(f"File copied from {source_file_path} to {target_file_path}")

print("Copying completed.")

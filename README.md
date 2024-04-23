# Train-Ticket: Monolith to Microservices Conversion Benchmark


[//]: # (# <img src="./image/logo.png">)

## Description
This repository contains the code for a monolithic application extracted from the Train-Ticket 
microservices architecture. The purpose of creating this monolith is to serve as a benchmark 
for research purposes in software engineering, specifically for the conversion of monoliths to 
microservices.

## Background
The software industry has seen an increased interest in migrating from monolithic architectures 
to microservices due to the many benefits it offers, such as improved scalability, maintainability, 
and deployment flexibility. However, breaking down a monolith into microservices is a complex task that 
requires careful analysis and planning.

## Objective
The main objective of this project is to propose a novel method for converting a monolith into 
microservices. We are exploring the use of a VAE (Variational Autoencoder) based Fuzzy C means clustering
algorithm to automatically extract microservices from the Train-Ticket monolith. By applying this proposed
method, we aim to suggest a minimum number of microservices that can be extracted from the monolith while 
preserving functionality and ensuring proper service boundaries.

## Quick Start

#### 1. Clone the Repository
```bash
git clone https://github.com/showkat2203/train-ticket-monolith.git 
cd trainticket/
```

#### 2. Run the Docker Compose command to set up the necessary infrastructure for the application:

```bash
docker-compose up --build -d
```

If want to run using traces use:

```sh

```


## Proposed Conversion Method
The proposed method involves the following steps:

#### 1. Data Collection: 
Gather information about the Train-Ticket monolith, its functionalities, and interactions between components.

#### 2. Preprocessing: 
Prepare the data for clustering by removing noise, handling missing values, and performing feature engineering.

#### 3. Feature Extraction: 
Apply the VAE (Variational Autoencoder) to extract meaningful features from the monolithic codebase.

#### 4. Clustering: 
Utilize the Fuzzy C means algorithm to group similar functionalities into microservices.

#### 5. Validation: 
Validate the generated microservices using various metrics like cohesion, coupling, and functional correctness.

#### 6. Benchmarking: 
Compare the performance of the extracted microservices against the original monolith and other existing microservice architectures.

## Contribution Guidelines
We welcome contributions from the community to improve and expand this benchmark project. If you want to contribute, please follow the guidelines outlined in CONTRIBUTING.md.

#### License
This project is licensed under the MIT License.

#### Acknowledgments
We would like to express our gratitude to the creators and maintainers of the Train-Ticket microservices architecture for providing the foundation for this benchmark project.

Thank you for your interest in our Train-Ticket Monolith to Microservices Conversion Benchmark project. If you have any questions, concerns, or suggestions, please feel free to reach out to us or open an issue in this repository.

### Happy benchmarking and microservices conversion!

### CloudHub Lab, Baylor University
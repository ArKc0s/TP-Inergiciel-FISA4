-- Table pour stocker les informations sur les personnes
CREATE TABLE Patient (
                         patient_id VARCHAR(100) PRIMARY KEY,
                         birth_name VARCHAR(50),
                         legal_name VARCHAR(50),
                         first_name VARCHAR(50),
                         prefix VARCHAR(10),
                         birth_date DATE
);

-- Table pour stocker les adresses des personnes
CREATE TABLE Address (
                         address_id SERIAL PRIMARY KEY,
                         address_index INTEGER NOT NULL,
                         street VARCHAR(100),
                         other_street VARCHAR(100),
                         city VARCHAR(50),
                         state VARCHAR(50),
                         postal_code VARCHAR(20),
                         country VARCHAR(50),
                         address_type VARCHAR(50),
                         patient_id VARCHAR(100) REFERENCES Patient(patient_id)
);

-- Table pour stocker les séjours des patients
CREATE TABLE Stay (
                      num_sej VARCHAR(100) PRIMARY KEY,
                      start_date DATE,
                      end_date DATE,
                      patient_id VARCHAR(100) REFERENCES Patient(patient_id)
);

-- Table pour stocker les mouvements des patients
CREATE TABLE Movement (
                          movement_id SERIAL PRIMARY KEY,
                          service VARCHAR(100),
                          room VARCHAR(100),
                          bed VARCHAR(100),
                          num_sej VARCHAR(100) REFERENCES Stay(num_sej),
                          patient_id VARCHAR(100) REFERENCES Patient(patient_id)
);




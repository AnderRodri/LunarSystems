# 🌕 Lunar Systems — Sistema de Controle de Missões Lunares

Projeto desenvolvido em Java para simular o controle de missões espaciais
com destino à Lua. O sistema permite cadastrar missões, astronautas, naves,
registrar retornos e armazenar todos os dados utilizando duas formas distintas de
persistência:

- **Serialização binária (ObjectOutputStream)**
- **NitriteDB** (banco de dados NoSQL embarcado)

---

## 🚀 Objetivo

O propósito deste trabalho é demonstrar:

- Modelagem orientada a objetos
- Encapsulamento, herança e polimorfismo
- Camadas de aplicação (model, repository, service, view)
- Persistência de dados em Java
- Uso do NitriteDB como ObjectRepository
- Validações e regras de negócio em um cenário realista

---

## 🧩 Funcionalidades do Sistema

- Cadastro completo de missões espaciais
- Cadastro de astronautas
- Cadastro de naves (tripulada e cargueira)
- Registro de retorno da missão (data + resultado científico)
- Busca de astronautas por nome
- Busca de missões por astronauta
- Listagem geral de missões
- Listagem de missões com resultado registrado
- Persistência simultânea:
  - `missions.bin` (Serialização)
  - `lunarsystems.db` (NitriteDB)

---

## 🛰️ Entidades Modeladas

### **Missão**
- Código único  
- Nome  
- Data de lançamento  
- Data de retorno  
- Destino  
- Objetivo  
- Resultado científico  
- Nave utilizada  
- Lista de astronautas

### **Astronauta**
- Nome  
- Idade  
- Especialidade  
- Horas de voo  
> *Validação: idade mínima de 21 anos*

### **Nave (abstrata)**
- ID  
- Modelo  
- Capacidade de tripulantes

### **NaveTripulada**  
### **NaveCargueira**
- Capacidade de carga (kg)

---

## 🧠 Regras de Negócio

1. **Código da missão deve ser único**
2. **Astronautas devem ter 21+ anos**
3. **Número de astronautas ≤ capacidade da nave**
4. **Toda missão é salva em BIN + NitriteDB**
5. **É possível registrar resultados científicos pós-retorno**

---

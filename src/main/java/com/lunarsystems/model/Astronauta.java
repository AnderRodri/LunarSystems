package com.lunarsystems.model;


import java.io.Serializable;


public class Astronauta implements Serializable {
private String nome;
private int idade;
private String especialidade;
private int horasVoo;


public Astronauta(String nome, int idade, String especialidade, int horasVoo) {
this.nome = nome;
this.idade = idade;
this.especialidade = especialidade;
this.horasVoo = horasVoo;
}


public String getNome() { return nome; }
public void setNome(String nome) { this.nome = nome; }
public int getIdade() { return idade; }
public void setIdade(int idade) { this.idade = idade; }
public String getEspecialidade() { return especialidade; }
public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
public int getHorasVoo() { return horasVoo; }
public void setHorasVoo(int horasVoo) { this.horasVoo = horasVoo; }


@Override
public String toString() {
return nome + " (" + idade + " anos, " + especialidade + ", " + horasVoo + "h)";
}
}
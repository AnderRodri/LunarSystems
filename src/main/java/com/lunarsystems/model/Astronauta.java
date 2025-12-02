package com.lunarsystems.model;

import java.io.Serializable;
import org.dizitart.no2.objects.Id;

public class Astronauta implements Serializable {
    @Id
    private String nome;
    private int idade;
    private String especialidade;
    private int horasVoo;

    public Astronauta() {
    }

    public Astronauta(String nome, int idade, String especialidade, int horasVoo) {
        this.nome = nome;

        setIdade(idade);
        this.especialidade = especialidade;
        this.horasVoo = horasVoo;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getIdade() { return idade; }


    public void setIdade(int idade) {
        if (idade < 21) {
            throw new IllegalArgumentException("A idade mínima para um astronauta é 21 anos.");
        }
        this.idade = idade;
    }


    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
    public int getHorasVoo() { return horasVoo; }
    public void setHorasVoo(int horasVoo) { this.horasVoo = horasVoo; }

    @Override
    public String toString() {
        return nome + " (" + idade + " anos, \n" + especialidade + ", \n" + horasVoo + "h)\n";
    }
}
